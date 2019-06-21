package com.mycclee.blog.mapper;

import com.github.pagehelper.PageInfo;
import com.mycclee.blog.beans.Blog;
import com.mycclee.blog.beans.BlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogMapper {
    long countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> selectAllBlog();

    List<Blog> selectAllBlogByHot();

    List<Blog> selectBlogsOrderByTime();

    List<Blog> selectBlogsOrderByCommentsAndLikes();

    List<Blog> selectBlogsByOwner(Long uid);

    List<Blog> getBlogsByCid(Blog blog);

    List<Blog> getBlogsByTags(Blog blog);

    List<Blog> listBlogsByHot(Long uid);

    List<Blog> listBlogsByNew(Long uid);


}