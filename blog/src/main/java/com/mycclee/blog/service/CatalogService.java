package com.mycclee.blog.service;

import com.mycclee.blog.beans.Catalog;

import java.util.List;

public interface CatalogService {

    List<Catalog> getCatalogList();

    void insertCatalog(Catalog catalog);

    void updateCatalog(Long id,String name,Long uid);

    void deleteCatalog(Long id);

    Catalog getCatalogById(Long id);

    List<Catalog> getOwnerCatalog(Long uid);
}
