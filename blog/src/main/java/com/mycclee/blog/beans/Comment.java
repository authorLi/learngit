package com.mycclee.blog.beans;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private Long id;

    private String content;

    private Long uid;

    private Long blogid;

    private Date createtime;

    public Comment() {
    }

    public Comment(String content, Long uid, Long blogid) {
        this.content = content;
        this.uid = uid;
        this.blogid = blogid;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getBlogid() {
        return blogid;
    }

    public void setBlogid(Long blogid) {
        this.blogid = blogid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", uid=" + uid +
                ", blogid=" + blogid +
                ", createtime=" + createtime +
                '}';
    }
}