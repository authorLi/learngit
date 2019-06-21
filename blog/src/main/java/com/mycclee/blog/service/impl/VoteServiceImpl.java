package com.mycclee.blog.service.impl;

import com.mycclee.blog.beans.Vote;
import com.mycclee.blog.beans.VoteExample;
import com.mycclee.blog.mapper.VoteMapper;
import com.mycclee.blog.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteMapper voteMapper;

    @Override
    public void insertVote(Long uid,Long blogid) {
        Vote vote = new Vote();
        vote.setUid(uid);
        vote.setBlogid(blogid);
        vote.setCreatetime(new Date());
        voteMapper.insert(vote);
    }

    @Override
    public void removeVote(Long id) {
        voteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Vote> selectVoteByUid(Long uid) {
        VoteExample example = new VoteExample();
        VoteExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        List<Vote> voteList =  voteMapper.selectByExample(example);
        return voteList;
    }
}
