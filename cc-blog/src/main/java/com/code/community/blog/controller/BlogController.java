package com.code.community.blog.controller;

import com.code.community.blog.model.form.BlogForm;
import com.code.community.blog.model.po.Blog;
import com.code.community.blog.service.BlogService;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.auth.Auth;
import com.code.community.common.model.PageInfo;
import com.code.community.common.validator.group.Insert;
import com.code.community.common.validator.group.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@Slf4j
@Api("博客相关接口")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @ApiOperation("获取markdown渲染后的博客")
    @GetMapping("/{id}")
    public Result getBlog(@PathVariable String id) {
        log.info("getBlog,id{}",id);
        return Result.success(blogService.getBlogById(id));
    }

    @ApiOperation("编辑博客调用,获取原始博客")
    @GetMapping("/md/{id}")
    public Result getMdBlog(@PathVariable String id){
        log.info("getMdBlog:{}",id);
        return Result.success(blogService.getMdBlogById(id));
    }

    @Auth
    @ApiOperation("发表博客")
    @PostMapping
    public Result postBlog(@RequestBody @Validated({Insert.class}) BlogForm blogForm) {
        log.info("postBlog,userName:{},title:{},content:{}",
                blogForm.getAuthor(), blogForm.getTitle(), blogForm.getContent());
        Blog blog = blogForm.toPo(Blog.class);
        return Result.success(blogService.postBlog(blog));
    }

    @Auth
    @ApiOperation("更新博客")
    @PutMapping
    public Result updateBlog(@RequestBody @Validated({Update.class}) BlogForm blogForm){
        log.info("postBlog,blogId{},userName:{},title:{},content:{},author:{}",
                blogForm.getId(), blogForm.getAuthor(), blogForm.getTitle(), blogForm.getContent(),blogForm.getAuthor());
        Blog blog = blogForm.toPo(Blog.class);
        return Result.success(blogService.updateBlog(blog));
    }

    @Auth
    @ApiOperation("删除博客")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id){
        log.info("deleteBlog,id:{}",id);
        blogService.deleteBlog(id);
        return Result.success();
    }

    @ApiOperation("根据创建时间批量获取博客")
    @GetMapping
    public Result page(@Validated PageInfo pageInfo) {
        log.info("getPage,currentPage:{},pageInfo:{}",pageInfo);
        return Result.success(blogService.getBlogPageByCreatedTime(pageInfo));
    }

    @ApiOperation("根据点赞数量随机获取批量博客")
    @GetMapping("/hot")
    public Result randomGetHotBlog(@Validated PageInfo pageInfo) {
        log.info("getBlogBySupportAndRandom,pageInfo:{}", pageInfo);
        return Result.success(blogService.getBlogsBySupportAndRandom(pageInfo));
    }

    @ApiOperation("获取对应标签的博客")
    @GetMapping("/tag/{tag}")
    public Result getBlogPageByTag(@PathVariable("tag")String tag,
                                   @Validated PageInfo pageInfo){
        log.info("getBlogByTag,tag:{},pageInfo:{}",tag,pageInfo);
        return Result.success(blogService.getBlogPageByTag(tag,pageInfo));
    }

    @ApiOperation("获取用户发表博客记录")
    @GetMapping("/author/{author}")
    public Result getBlogPageByAuthor(@PathVariable("author")String author,
                                      @Validated PageInfo pageInfo){
        log.info("getBlogByAuthor,author:{},pageInfo:{}",author,pageInfo);
        return Result.success(blogService.getBlogPageByAuthor(author,pageInfo));
    }

    @ApiOperation("获取博客标签")
    @GetMapping("/get-tag")
    public Result getTag(){
        log.info("getTag:{}");
        return Result.success(blogService.getTag());
    }


}
