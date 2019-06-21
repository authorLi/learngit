package com.mycclee.blog.service.impl;

import com.mycclee.blog.beans.Comment;
import com.mycclee.blog.beans.CommentExample;
import com.mycclee.blog.mapper.CommentMapper;
import com.mycclee.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getBlogAllComments(Long blogid) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andBlogidEqualTo(blogid);
        List<Comment> commentList = commentMapper.selectByExample(example);
        return commentList;
    }

    @Override
    public void comment(String content,Long uid,Long blogid) {
        Comment comment = new Comment(content,uid,blogid);
        comment.setCreatetime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Comment getCommentOwner(Long id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        return comment;
    }

    @Override
    public List<Comment> getAllComments() {
        CommentExample example = new CommentExample();
        List<Comment> commentList = commentMapper.selectByExample(example);
        return commentList;
    }
}
