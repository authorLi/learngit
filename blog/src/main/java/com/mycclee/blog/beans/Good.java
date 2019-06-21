package com.mycclee.blog.beans;

public class Good {
    private Blog blog;
    private User user;

    public Good() {
    }

    public Good(Blog blog, User user) {
        this.blog = blog;
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Good{" +
                "blog=" + blog +
                ", user=" + user +
                '}';
    }
}
