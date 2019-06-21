package com.mycclee.blog.controller;

import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.service.VoteService;
import com.mycclee.blog.vo.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Response> createVote(@RequestParam("blogId") Long blogid) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Long uid = userService.getUserByUsername((String) subject.getPrincipal()).getId();
            blogService.changeVotesPlus(blogid);
            voteService.insertVote(uid,blogid);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteVote(@PathVariable("id") Long id, @RequestParam("blogId") Long blogid) {
        try {
            blogService.changeVotesMinus(blogid);
            voteService.removeVote(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true,"取消点赞成功",null));
    }

}
