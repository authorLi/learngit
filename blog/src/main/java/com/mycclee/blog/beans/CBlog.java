package com.mycclee.blog.beans;

import java.util.List;

public class CBlog {
    private Blog blog;
    private List<Comment> commentList;

    public CBlog() {
    }

    public CBlog(Blog blog, List<Comment> commentList) {
        this.blog = blog;
        this.commentList = commentList;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "CBlog{" +
                "blog=" + blog +
                ", commentList=" + commentList +
                '}';
    }
}
