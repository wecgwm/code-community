package com.code.community.comment.controller;

import com.code.community.comment.base.model.vo.Result;
import com.code.community.comment.model.form.CommentForm;
import com.code.community.comment.model.po.Comment;
import com.code.community.comment.service.CommentService;
import com.code.community.common.auth.Auth;
import com.code.community.common.model.PageInfo;
import com.code.community.common.validator.group.Insert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Slf4j
@Api("评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Auth
    @ApiOperation("发表评论")
    @PostMapping
    public Result addComment(@RequestBody @Validated({Insert.class}) CommentForm commentForm) {
        log.info("addComment,commentForm:{}",commentForm);
        Comment comment = commentForm.toPo(Comment.class);
        commentService.submitComment(comment);
        return Result.success();
    }

    @ApiOperation("获取博客下评论")
    @GetMapping("/receiveId/{receiveId}")
    public Result getCommentPage(@PathVariable("receiveId") String receiveId,
                                 @Validated PageInfo pageInfo) {
        log.info("getCommentPage,receiveId:{}", receiveId);
        return Result.success(commentService.getCommentPage(receiveId,pageInfo));
    }

    @ApiOperation("获取用户的评论记录")
    @GetMapping("/userId/{userId}")
    public Result getCommentPageByUserId(@PathVariable("userId") String userId ,
                                         @Validated PageInfo pageInfo)  {
        log.info("getCommentPageByUserId,userId:{}", userId);
        return Result.success(commentService.getCommentPageByUserId(userId,pageInfo));
    }

    @Auth
    @ApiOperation("删除评论")
    @DeleteMapping
    public Result deleteComment(@RequestBody CommentForm commentForm) {
        log.info("deleteComment,id:{}", commentForm);
        Comment comment = commentForm.toPo(Comment.class);
        commentService.deleteComment(comment);
        return Result.success();
    }

}
