package com.mycclee.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mycclee.blog.beans.Blog;
import com.mycclee.blog.beans.BlogExample;
import com.mycclee.blog.beans.Good;
import com.mycclee.blog.beans.User;
import com.mycclee.blog.mapper.BlogMapper;
import com.mycclee.blog.mapper.UserMapper;
import com.mycclee.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Long saveBlog(Blog blog) {
        Long id = new Long(blogMapper.insert(blog));
        return id;
    }

    @Override
    public void removeBlog(Long id) {
        blogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        blogMapper.updateByPrimaryKeySelective(blog);
        return blog;
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        return blog;
    }

    @Override
    public PageInfo<Blog> getBlogByOwner(Integer pageNum, Integer pageSize, Long uid) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        blogMapper.selectBlogsByOwner(uid);
        return new PageInfo<Blog>(page);
    }

    @Override
    public PageInfo<Blog> listOwnerBlogsByHot(Integer pageNum,Integer pageSize,Long uid) {
        Page page = PageHelper.startPage(pageNum,pageSize);
        blogMapper.listBlogsByHot(uid);
        return new PageInfo<Blog>(page);
    }

    @Override
    public PageInfo<Blog> listOwnerBlogsByNew(Integer pageNum,Integer pageSize,Long uid) {
        Page page = PageHelper.startPage(pageNum,pageSize);
        blogMapper.listBlogsByNew(uid);
        return new PageInfo<Blog>(page);
    }

    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        System.out.println(blog);
        Long reading = blog.getReading();
        System.out.println(reading);
        blog.setReading(++reading);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

//    @Override
//    public PageInfo<Good> getAllBlogs(Integer pageNum, Integer pageSize) {
//        Page page = PageHelper.startPage(pageNum, pageSize);
//        BlogExample example = new BlogExample();
//        example.setOrderByClause("createTime desc");
//        List<Blog> list = blogMapper.selectByExample(example);
//        PageInfo<Good> pageInfo = new PageInfo<Good>(getGoodsByBlogList(list));
//        pageInfo.setTotal(page.getTotal());
//        pageInfo.setIsFirstPage(page.getPageNum() == 1 ? true : false);
//        pageInfo.setIsLastPage(page.getPageNum() == page.getPages() ? true : false);
//        pageInfo.setPages(page.getPages());
//        pageInfo.setHasPreviousPage(pageInfo.isIsFirstPage() == true ? false : true);
//        pageInfo.setHasNextPage(pageInfo.isIsLastPage() == true ? false : true);
//        return pageInfo;
//    }

    @Override
    public void changeCommentsPlus(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        Long comments = blog.getComments();
        blog.setComments(++comments);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public void changeCommentsMinus(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        Long comments = blog.getComments();
        blog.setComments(--comments);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public void changeVotesPlus(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        Long likes = blog.getLikes();
        blog.setLikes(++likes);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public void changeVotesMinus(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        Long likes = blog.getLikes();
        blog.setLikes(--likes);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public PageInfo<Blog> getBlogsByCid(Integer pageNum, Integer pageSize, Blog blog) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        blogMapper.getBlogsByCid(blog);
        return new PageInfo<Blog>(page);
    }

    @Override
    public PageInfo<Blog> getBlogsByTags(Integer pageNum,Integer pageSize,Blog blog) {
        blog.setTag("%" + blog.getTag() + "%");

        Page page = PageHelper.startPage(pageNum, pageSize);
        blogMapper.getBlogsByTags(blog);
        return new PageInfo<Blog>(page);
    }

    @Override
    public List<Blog> getBlogsOrderByTime() {
        return blogMapper.selectBlogsOrderByTime();
    }

    @Override
    public List<Blog> getBlogsOrderByCommentsAndLikes() {
        return blogMapper.selectBlogsOrderByCommentsAndLikes();
    }

    @Override
    public List<Blog> getBlogListByOwner(Long uid) {
        BlogExample example = new BlogExample();
        example.setOrderByClause("(comments + likes) desc");
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        return blogMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Blog> getIndexBlog(Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        blogMapper.selectAllBlog();
        return new PageInfo<Blog>(page);
    }

    @Override
    public PageInfo<Blog> selectAllBlogByHot(Integer pageNum,Integer pageSize) {
        Page page = PageHelper.startPage(pageNum,pageSize);
        blogMapper.selectAllBlogByHot();
        return new PageInfo<Blog>(page);
    }

}
