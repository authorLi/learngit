package com.mycclee.blog.service;

import com.mycclee.blog.beans.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getBlogAllComments(Long blogid);

    void comment(String content,Long uid,Long blogid);

    void deleteComment(Long id);

    Comment getCommentOwner(Long id);

    List<Comment> getAllComments();
}
