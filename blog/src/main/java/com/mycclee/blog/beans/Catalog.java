package com.mycclee.blog.beans;

import java.io.Serializable;

public class Catalog implements Serializable {
    private Long id;

    private String name;

    private Long uid;

    private static final long serialVersionUID = 1L;

    public Catalog() {
    }

    public Catalog(Long id, String name, Long uid) {
        this.id = id;
        this.name = name;
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uid=" + uid +
                '}';
    }
}