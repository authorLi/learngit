package com.mycclee.blog.service;

import com.github.pagehelper.PageInfo;
import com.mycclee.blog.beans.Blog;
import com.mycclee.blog.beans.Good;
import com.mycclee.blog.beans.User;

import java.util.List;

public interface BlogService {
    Long saveBlog(Blog blog);
    void removeBlog(Long id);
    Blog updateBlog(Blog blog);
    Blog getBlogById(Long id);
    PageInfo<Blog> getBlogByOwner(Integer pageNum,Integer pageSize,Long uid);
    //最新
    PageInfo<Blog> listOwnerBlogsByHot(Integer pageNum,Integer pageSize,Long uid);
    //最热
    PageInfo<Blog> listOwnerBlogsByNew(Integer pageNum,Integer pageSize,Long uid);
    void readingIncrease(Long id);
    void changeCommentsPlus(Long blogId);
    void changeCommentsMinus(Long blogId);
    void changeVotesPlus(Long blogId);
    void changeVotesMinus(Long blogId);
    PageInfo<Blog> getBlogsByCid(Integer pageNum,Integer pageSize,Blog blog);
    PageInfo<Blog> getBlogsByTags(Integer pageNum,Integer pageSize,Blog blog);
    List<Blog> getBlogsOrderByTime();
    List<Blog> getBlogsOrderByCommentsAndLikes();
    List<Blog> getBlogListByOwner(Long uid);
    PageInfo<Blog> getIndexBlog(Integer pageNum,Integer pageSize);
    PageInfo<Blog> selectAllBlogByHot(Integer pageNum,Integer pageSize);
}
