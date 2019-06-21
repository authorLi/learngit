package com.mycclee.blog.beans;

public class UComment {
    private User user;
    private Comment comment;

    public UComment() {
    }

    public UComment(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "UComment{" +
                "user=" + user +
                ", comment=" + comment +
                '}';
    }
}
