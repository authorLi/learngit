package com.mycclee.blog.mapper;

import com.mycclee.blog.beans.Catalog;
import com.mycclee.blog.beans.CatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogMapper {
    long countByExample(CatalogExample example);

    int deleteByExample(CatalogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Catalog record);

    int insertSelective(Catalog record);

    List<Catalog> selectByExample(CatalogExample example);

    Catalog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Catalog record, @Param("example") CatalogExample example);

    int updateByExample(@Param("record") Catalog record, @Param("example") CatalogExample example);

    int updateByPrimaryKeySelective(Catalog record);

    int updateByPrimaryKey(Catalog record);
}