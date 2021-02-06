package com.code.community.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.code.community.comment.model.po.Comment;
import com.code.community.comment.model.vo.CommentVo;
import com.code.community.common.model.PageInfo;

public interface CommentService {
    /**
     * 添加评论
     * @param comment
     */
    void submitComment(Comment comment);

    /**
     * 根据博客id批量获取评论
     * @param receiveId 博客id
     * @param pageInfo 分页信息
     * @return
     */
    IPage<CommentVo> getCommentPage(String receiveId, PageInfo pageInfo);

    /**
     * 根据userId批量获取评论
     * @param userId 用户id
     * @param pageInfo 分页信息
     * @return
     */
    IPage<CommentVo> getCommentPageByUserId(String userId,PageInfo pageInfo);

    /**
     * 删除评论
     * @param comment
     */
    void deleteComment(Comment comment);
}
