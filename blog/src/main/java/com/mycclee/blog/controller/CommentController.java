package com.mycclee.blog.controller;

import com.mycclee.blog.beans.*;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.CommentService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.vo.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getCommentList(@RequestParam(value = "blogId",required = true) Long blogId, Model model){
        List<Comment> commentList = commentService.getBlogAllComments(blogId);

        Subject subject = SecurityUtils.getSubject();

        User user = userService.getUserByUsername((String) subject.getPrincipal());

        List<UComment> list = new ArrayList<>();
        for (Comment comment:commentList){
            User commentOwn = userService.getUserById(comment.getUid());
            UComment uComment = new UComment(commentOwn,comment);
            list.add(uComment);
        }

        model.addAttribute("commentOwner",subject.getPrincipal());
        model.addAttribute("comments",list);
        return "/userspace/blog :: #mainContainerRepleace";
    }

    @PostMapping
    public ResponseEntity<Response> comment(String commentContent,String username,Long blogid){
        User user = userService.getUserByUsername(username);
        try{
            commentService.comment(commentContent,user.getId(),blogid);
            blogService.changeCommentsPlus(blogid   );
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable("id") Long id,@RequestParam("blogId")Long blogId){
        Subject subject = SecurityUtils.getSubject();
        Comment comment = commentService.getCommentOwner(id);
        User user = userService.getUserByUsername((String) subject.getPrincipal());
        blogService.changeCommentsMinus(blogId);

        if (!comment.getUid().equals(user.getId())){
            return ResponseEntity.ok().body(new Response(false,"没有操作权限"));
        }
        try{
            commentService.deleteComment(id);

        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }

    @GetMapping("/list")
    public ModelAndView getCommentsList(Model model){
        List<Comment> commentList = commentService.getAllComments();
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByUsername((String) subject.getPrincipal());
        List<UComment> list = new ArrayList<>();
        for (Comment comment:commentList){
            User commentOwner = userService.getUserById(comment.getUid());
            UComment uComment = new UComment(commentOwner,comment);
            list.add(uComment);
        }
        model.addAttribute("commentOwner",subject.getPrincipal());
        model.addAttribute("comments",list);
        return new ModelAndView("userspace/commentslist","model",model);
    }
}
