package com.code.community.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.blog.dao.BlogMapper;
import com.code.community.blog.model.po.Blog;
import com.code.community.blog.model.vo.BlogVo;
import com.code.community.blog.service.BlogService;
import com.code.community.comment.base.constant.FiledConstant;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.constant.RedisConstant;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.CRUDExceptionType;
import com.code.community.common.auth.UserContextHolder;
import com.code.community.common.exception.AuthException;
import com.code.community.common.factory.VoFactory;
import com.code.community.common.model.PageInfo;
import com.code.community.common.service.RedisService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"blog"})
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    private static final String SUPPORT_COUNT = "support_count";

    @Autowired
    RedisService redisService;

    @Override
    public BlogVo getBlogById(String id) {
        Blog blog = (Blog) redisService.get(RedisConstant.BLOG_PREFIX + id);
        if (Objects.isNull(blog)) {
            blog = this.getById(id);
            if (Objects.isNull(blog)) {
                throw new CRUDException(CRUDExceptionType.NOT_FOUND, "博客未找到，id:" + id);
            }
            renderToHTML(blog);
            redisService.set(RedisConstant.BLOG_PREFIX + id, blog, RedisConstant.EXPIRE_TIME);
        }
        return VoFactory.createVo(BlogVo.class, blog);
    }

    @Cacheable(key = "#author", condition = "#pageInfo.currentPage == 1")
    @Override
    public IPage<BlogVo> getBlogPageByAuthor(String author, PageInfo pageInfo) {
        Page<Blog> blogPage = pageInfo.getPage();
        QueryWrapper<Blog> queryWrapper = getQueryWrapper();
        queryWrapper.eq(FiledConstant.AUTHOR, author);
        queryWrapper.orderByDesc(FiledConstant.CREATED_TIME);
        this.page(blogPage, queryWrapper);
        return getBlogVoPage(blogPage);
    }

    @Override
    public String updateBlog(Blog blog) {
        //防止水平越权
        Blog oldBlog = (Blog) redisService.get(RedisConstant.BLOG_PREFIX + blog.getId());
        if (Objects.isNull(oldBlog)) {
            oldBlog = this.getById(blog.getId());
        }
        if (Objects.isNull(oldBlog)) {
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "没有找到对应博客，blog:" + blog);
        }
        String userName = UserContextHolder.getUser().getUserName();
        if (!oldBlog.getAuthor().equals(userName)) {
            throw new AuthException();
        }
        boolean update = this.updateById(blog);
        if (!update) {
            throw new CRUDException(CRUDExceptionType.UPDATE_FAILED, "博客更新失败，blog:" + blog);
        }
        renderToHTML(blog);
        redisService.set(RedisConstant.BLOG_PREFIX + blog.getId(), blog, RedisConstant.EXPIRE_TIME);
        redisService.del(RedisConstant.BLOG_PREFIX + userName);
        redisService.del(RedisConstant.BLOG_PREFIX + FiledConstant.CREATED_TIME);
        return blog.getId();
    }

    @CacheEvict(key = "#id")
    @Override
    public void deleteBlog(String id) {
        Blog blog = (Blog) redisService.get(RedisConstant.BLOG_PREFIX + id);
        if (blog == null) {
            blog = this.getById(id);
        }
        if (Objects.isNull(blog)) {
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "没有找到对应博客，blog:" + blog);
        }
        String userName = UserContextHolder.getUser().getUserName();
        if (!blog.getAuthor().equals(userName)) {
            throw new AuthException();
        }
        boolean remove = this.removeById(id);
        if (!remove) {
            throw new CRUDException(CRUDExceptionType.DELETE_FAILED, "博客记录删除失败，id" + id);
        }
        redisService.del(RedisConstant.BLOG_PREFIX + id);
        redisService.del(RedisConstant.BLOG_PREFIX + userName);
        redisService.del(RedisConstant.BLOG_PREFIX + FiledConstant.CREATED_TIME);
    }

    @Override
    public String postBlog(Blog blog) {
        String author = UserContextHolder.getUser().getUserName();
        blog.setAuthor(author);
        boolean save = this.save(blog);
        if (!save) {
            throw new CRUDException(CRUDExceptionType.INSERT_FAILED, "博客记录插入失败，blog" + blog);
        }
        redisService.set(RedisConstant.BLOG_PREFIX + blog.getId(), blog, RedisConstant.EXPIRE_TIME);
        redisService.del(RedisConstant.BLOG_PREFIX + author);
        redisService.del(RedisConstant.BLOG_PREFIX + FiledConstant.CREATED_TIME);
        redisService.del(RedisConstant.BLOG_PREFIX + blog.getTag());
        return blog.getId();
    }

    @Cacheable(key = "'created_time'", condition = "#pageInfo.currentPage == 1")
    @Override
    public IPage<BlogVo> getBlogPageByCreatedTime(PageInfo pageInfo) {
        IPage<Blog> page = pageInfo.getPage();
        QueryWrapper<Blog> queryWrapper = getQueryWrapper();
        queryWrapper.eq(FiledConstant.STATE, FiledConstant.STATE_EFFECTIVE);
        queryWrapper.orderByDesc(FiledConstant.CREATED_TIME);
        this.page(page, queryWrapper);
        return getBlogVoPage(page);
    }

    @Cacheable(key = "#tag", condition = "#pageInfo.currentPage == 1")
    @Override
    public IPage<BlogVo> getBlogPageByTag(String tag, PageInfo pageInfo) {
        Page<Blog> blogPage = pageInfo.getPage();
        QueryWrapper<Blog> queryWrapper = getQueryWrapper();
        queryWrapper.eq(FiledConstant.TAG, tag);
        this.page(blogPage, queryWrapper);
        return getBlogVoPage(blogPage);
    }

    //TODO 从缓存中取，反向转换成原格式
    @Override
    public BlogVo getMdBlogById(String id) {
        Blog blog = this.getById(id);
        if (Objects.isNull(blog)) {
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "博客未找到，id:" + id);
        }
        return VoFactory.createVo(BlogVo.class, blog);
    }

    @Override
    public IPage<BlogVo> getBlogsBySupportAndRandom(PageInfo pageInfo) {
        Integer realPageSize = pageInfo.getPageSize();
        IPage<Blog> blogPage = pageInfo.getPage();
        blogPage.setSize(realPageSize * NumberConstant.TWO);
        QueryWrapper<Blog> queryWrapper = getQueryWrapper();
        queryWrapper.orderByDesc(SUPPORT_COUNT);
        this.page(blogPage, queryWrapper);
        IPage<BlogVo> blogVoPage = getBlogVoPage(blogPage);
        List<BlogVo> records = blogVoPage.getRecords();
        if (records.size() > realPageSize) {
            Collections.shuffle(records);
            blogVoPage.setRecords(records.subList(NumberConstant.ZERO, realPageSize));
            blogVoPage.setSize(realPageSize);
        }
        return blogVoPage;
    }

    @Override
    public Set<String> getTag() {
        QueryWrapper<Blog> queryWrapper = getQueryWrapper();
        queryWrapper.select(FiledConstant.TAG);
        Set<String> collect = this.listObjs(queryWrapper).
                stream().map(o -> (String) o).collect(Collectors.toSet());
        return collect;
    }

    /**
     * 将MyBatis查询结果Page<Blog>转换成Page<BlogVo>
     *
     * @param blogPage
     * @return
     */
    private IPage<BlogVo> getBlogVoPage(IPage<Blog> blogPage) {
        IPage<BlogVo> blogVoPage = new Page<>(blogPage.getCurrent(), blogPage.getSize(), blogPage.getTotal());
        blogVoPage.setPages(blogPage.getPages());
        List<Blog> records = blogPage.getRecords();
        if (!records.isEmpty()) {
            List<BlogVo> collect = records.stream()
                    .map(blog -> VoFactory.createVo(BlogVo.class, blog))
                    .collect(Collectors.toList());
            blogVoPage.setRecords(collect);
        }
        return blogVoPage;
    }

    private void renderToHTML(Blog blog) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(blog.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        blog.setContent(renderer.render(document));
    }

    private QueryWrapper<Blog> getQueryWrapper() {
        return new QueryWrapper<>();
    }
}
