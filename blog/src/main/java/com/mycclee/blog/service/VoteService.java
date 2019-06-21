package com.mycclee.blog.service;

import com.mycclee.blog.beans.Vote;

import java.util.List;

public interface VoteService {

    void insertVote(Long uid,Long blogid);

    void removeVote(Long id);

    List<Vote> selectVoteByUid(Long uid);
}
