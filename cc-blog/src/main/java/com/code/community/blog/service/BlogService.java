package com.code.community.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.code.community.blog.model.po.Blog;
import com.code.community.blog.model.vo.BlogVo;
import com.code.community.common.model.PageInfo;

import java.util.Set;

public interface BlogService {
    /**
     * 根据创建时间获取博客
     * @param pageInfo 分页信息
     * @return
     */
    IPage<BlogVo> getBlogPageByCreatedTime(PageInfo pageInfo);

    /**
     * 获取markdown渲染后的博客，浏览用
     * @param id 博客id
     * @return
     */
    BlogVo getBlogById(String id);

    /**
     * 获取原始博客,编辑博客用
     * @param id 博客id
     * @return
     */
    BlogVo getMdBlogById(String id);

    /**
     * 发表博客
     * @param blog
     * @return
     */
    String postBlog(Blog blog);

    /**
     * 更新博客
     * @param blog
     * @return
     */
    String updateBlog(Blog blog);

    /**
     * 根据点赞数随机获取批量博客
     * @param pageInfo 分页信息
     * @return
     */
    IPage<BlogVo> getBlogsBySupportAndRandom(PageInfo pageInfo);

    /**
     * 获取对应标签的博客
     * @param tag 标签
     * @param pageInfo 分页信息
     * @return
     */
    IPage<BlogVo> getBlogPageByTag(String tag, PageInfo pageInfo);

    /**
     * 获取用户发表博客记录
     * @param author 作者/用户
     * @param pageInfo 分页信息
     * @return
     */
    IPage<BlogVo> getBlogPageByAuthor(String author,PageInfo pageInfo);

    /**
     * 删除博客
     * @param id 博客id
     */
    void deleteBlog(String id);

    /**
     * 获取博客标签
     * @return
     */
    Set<String> getTag();
}
