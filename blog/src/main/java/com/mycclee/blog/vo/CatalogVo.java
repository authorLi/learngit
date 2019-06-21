package com.mycclee.blog.vo;

import com.mycclee.blog.beans.Catalog;

import java.io.Serializable;

public class CatalogVo implements Serializable {
    private String username;
    private Catalog catalog;

    public CatalogVo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return "CatalogVo{" +
                "username='" + username + '\'' +
                ", catalog=" + catalog +
                '}';
    }
}
