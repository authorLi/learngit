package com.mycclee.blog.service.impl;

import com.mycclee.blog.beans.Catalog;
import com.mycclee.blog.beans.CatalogExample;
import com.mycclee.blog.mapper.CatalogMapper;
import com.mycclee.blog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogMapper catalogMapper;

    @Override
    public List<Catalog> getCatalogList() {
        List<Catalog> catalogList = catalogMapper.selectByExample(new CatalogExample());
        return catalogList;
    }

    @Override
    public void insertCatalog(Catalog catalog) {
        if (catalog.getId() != null){
            catalogMapper.updateByPrimaryKey(catalog);
        }else{
            catalogMapper.insert(catalog);
        }
    }

    @Override
    public void updateCatalog(Long id,String name,Long uid) {
        Catalog catalog = new Catalog(id,name,uid);
        catalogMapper.updateByPrimaryKey(catalog);
    }

    @Override
    public void deleteCatalog(Long id) {
        catalogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        Catalog catalog = catalogMapper.selectByPrimaryKey(id);
        return catalog;
    }

    @Override
    public List<Catalog> getOwnerCatalog(Long uid) {
        CatalogExample example = new CatalogExample();
        CatalogExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        List<Catalog> catalogList = catalogMapper.selectByExample(example);
        return catalogList;
    }
}
