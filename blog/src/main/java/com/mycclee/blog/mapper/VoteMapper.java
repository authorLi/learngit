package com.mycclee.blog.mapper;

import com.mycclee.blog.beans.Vote;
import com.mycclee.blog.beans.VoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteMapper {
    long countByExample(VoteExample example);

    int deleteByExample(VoteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Vote record);

    int insertSelective(Vote record);

    List<Vote> selectByExample(VoteExample example);

    Vote selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByExample(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);
}