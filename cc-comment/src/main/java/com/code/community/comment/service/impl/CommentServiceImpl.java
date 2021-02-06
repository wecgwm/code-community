package com.code.community.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.comment.base.constant.FiledConstant;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.constant.RedisConstant;
import com.code.community.comment.base.exception.BaseException;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.CRUDExceptionType;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.comment.dao.CommentMapper;
import com.code.community.comment.model.po.Comment;
import com.code.community.comment.model.vo.CommentVo;
import com.code.community.comment.provider.UserProvider;
import com.code.community.comment.service.CommentService;
import com.code.community.common.auth.UserContextHolder;
import com.code.community.common.exception.AuthException;
import com.code.community.common.factory.VoFactory;
import com.code.community.common.model.PageInfo;
import com.code.community.common.service.RedisService;
import com.code.community.user.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "comment")
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private RedisService redisService;


    @Caching(
            evict = {
                    @CacheEvict(key = "#comment.receiveId")
            }
    )
    @Override
    public void submitComment(Comment comment) {
        comment.setUserId(UserContextHolder.getUser().getId());
        boolean save = this.save(comment);
        if (save) {
            redisService.set(RedisConstant.COMMENT_PREFIX + comment.getId() ,comment, RedisConstant.EXPIRE_TIME);
            redisService.del(RedisConstant.COMMENT_PREFIX + comment.getUserId());
            return;
        }
        throw new CRUDException(CRUDExceptionType.INSERT_FAILED, "评论插入失败,comment:" + comment);
    }

    @Cacheable(key = "#receiveId", condition = "#pageInfo.currentPage == 1")
    @Override
    public IPage<CommentVo> getCommentPage(String receiveId, PageInfo pageInfo) {
        IPage<Comment> commentPage = pageInfo.getPage();
        QueryWrapper<Comment> queryWrapper = getQueryWrapper();
        queryWrapper.eq(FiledConstant.STATE, FiledConstant.STATE_EFFECTIVE);
        queryWrapper.eq(FiledConstant.RECEIVE_ID, receiveId);
        queryWrapper.orderByDesc(FiledConstant.CREATED_TIME);
        this.page(commentPage, queryWrapper);
        if (commentPage.getTotal() == 0L) {
            return null;
        }
        List<Comment> comments = commentPage.getRecords();
        List<String> idList = new ArrayList(NumberConstant.TEN);
        comments.stream().forEach(comment -> idList.add(comment.getUserId()));
        //获取评论用户信息 设置用户名以及头像
        Result<List<UserVo>> result = userProvider.batchGetUsersById(idList);
        if (Objects.nonNull(result) && result.isSuccess()) {
            List<UserVo> userVos = result.getData();
            Map<String, UserVo> userVoMap = userVos.stream()
                    .collect(Collectors.toMap(UserVo::getId, userVo -> userVo, (v1, v2) -> v1));
            List<CommentVo> commentVos = comments.stream()
                    .map(comment -> VoFactory.createVo(CommentVo.class, comment))
                    .collect(Collectors.toList());
            commentVos.forEach(commentVo -> {
                if (userVoMap.get(commentVo.getUserId()) != null) {
                    commentVo.setUserVo(userVoMap.get(commentVo.getUserId()));
                }
            });
            IPage<CommentVo> commentVoPage = new Page<>(commentPage.getCurrent(),
                    commentPage.getSize(), commentPage.getTotal());
            commentVoPage.setRecords(commentVos);
            return commentVoPage;
        } else {
            throw new BaseException("请求用户头像失败");
        }
    }

    @Cacheable(key = "#userId", condition = "#pageInfo.currentPage == 1")
    @Override
    public IPage<CommentVo> getCommentPageByUserId(String userId, PageInfo pageInfo) {
        IPage<Comment> commentPage = pageInfo.getPage();
        QueryWrapper<Comment> queryWrapper = getQueryWrapper();
        queryWrapper.eq(FiledConstant.STATE, FiledConstant.STATE_EFFECTIVE);
        queryWrapper.eq(FiledConstant.USER_ID, userId);
        queryWrapper.orderByDesc(FiledConstant.CREATED_TIME);
        this.page(commentPage, queryWrapper);
        List<CommentVo> commentVos = commentPage.getRecords().stream()
                .map(comment -> VoFactory.createVo(CommentVo.class, comment))
                .collect(Collectors.toList());
        IPage<CommentVo> commentVoPage = new Page<>(commentPage.getCurrent(), commentPage.getSize(), commentPage.getTotal());
        commentVoPage.setRecords(commentVos);
        return commentVoPage;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#comment.receiveId"),
                    @CacheEvict(key = "#comment.userId")
            }
    )
    @Override
    public void deleteComment(Comment comment) {
        //防止水平越权
        Comment c = (Comment)redisService.get(RedisConstant.COMMENT_PREFIX + comment.getId());
        if(Objects.isNull(c)){
            c = this.getById(comment.getId());
        }
        if(Objects.isNull(c)){
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "没有找到对应评论");
        }
        String id = UserContextHolder.getUser().getId();
        if(!comment.getUserId().equals(id)){
            throw new AuthException();
        }
        boolean remove = this.removeById(comment.getId());
        if (!remove) {
            throw new CRUDException(CRUDExceptionType.DELETE_FAILED, "删除评论失败:" + comment);
        }

    }

    private QueryWrapper<Comment> getQueryWrapper() {
        return new QueryWrapper<>();
    }

}
