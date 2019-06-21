package com.mycclee.blog.beans;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private String htmlcontent;

    private Long uid;

    private Date createtime;

    private Long reading;

    private Long comments;

    private Long likes;

    private Long cid;

    private String tag;

    private User user;

    private Catalog catalog;

    private static final long serialVersionUID = 1L;

    public Blog() {
    }

    public Blog(Long cid,Long uid){
        this.cid = cid;
        this.uid = uid;
    }

    public Blog(String tag,Long uid){
        this.tag = tag;
        this.uid = uid;
    }

    public Blog(Long uid,String title){
        this.uid = uid;
        this.title = title;
    }

    public Blog(Long id, String title, String summary, String content, String htmlcontent, Long uid, Date createtime, Long reading, Long comments, Long likes, Long cid, String tag ,User user,Catalog catalog) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.htmlcontent = htmlcontent;
        this.uid = uid;
        this.createtime = createtime;
        this.reading = reading;
        this.comments = comments;
        this.likes = likes;
        this.cid = cid;
        this.tag = tag;
        this.user = user;
        this.catalog = catalog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getHtmlcontent() {
        return htmlcontent;
    }

    public void setHtmlcontent(String htmlcontent) {
        this.htmlcontent = htmlcontent == null ? null : htmlcontent.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getReading() {
        return reading;
    }

    public void setReading(Long reading) {
        this.reading = reading;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", htmlcontent='" + htmlcontent + '\'' +
                ", uid=" + uid +
                ", createtime=" + createtime +
                ", reading=" + reading +
                ", comments=" + comments +
                ", likes=" + likes +
                ", cid=" + cid +
                ", tag='" + tag + '\'' +
                ", user=" + user +
                ", catalog=" + catalog +
                '}';
    }
}