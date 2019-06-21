package com.mycclee.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mycclee.blog.beans.Blog;
import com.mycclee.blog.beans.Good;
import com.mycclee.blog.beans.User;
import com.mycclee.blog.beans.UserTag;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@PropertySource("classpath:pagination.properties")
@Controller
public class MainController {

    @Value("${pageSize}")
    public int pageSize;
    @Value("${userMax}")
    private int userMax;

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum, Model model){
        int max = 0;
        PageInfo<Blog> blogList = blogService.getIndexBlog(pageNum,pageSize);
        List<Object> tagList = new ArrayList<>();
        List<User> hotUsers = new ArrayList<>();
        List<Blog> newest = blogService.getBlogsOrderByTime();
        List<Blog> hotest = blogService.getBlogsOrderByCommentsAndLikes();

        for (Blog blog : hotest){
            if (max <= userMax){
                if(!hotUsers.contains(blog.getUser())){
                    hotUsers.add(blog.getUser());
                }
                if(!tagList.contains(new UserTag(blog.getUser(),blog.getTag()))){
                    tagList.add(new UserTag(blog.getUser(),blog.getTag()));
                }
            }else{
                break;
            }
            max ++;
        }


        model.addAttribute("blogModel",blogList);
        model.addAttribute("hotUsers",hotUsers);
        model.addAttribute("tagList",tagList);
        model.addAttribute("newest",newest);
        model.addAttribute("hotest",hotest);
        return new ModelAndView("index","model",model);
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(String username,String password,Model model){
        String info = null;

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            info = String.valueOf(subject.isAuthenticated());
            model.addAttribute("username",username);
            return new ModelAndView("redirect:/","userModel",model);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/login-error","userModel",null);
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        model.addAttribute("errorMessage","登陆失败，用户名或密码错误");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user){
        userService.registerUser(user);
        return "redirect:/login";
    }

    @RequestMapping("/ok")
    @ResponseBody
    public String testYou(@RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum){
        PageInfo<Blog> blogList = blogService.getIndexBlog(pageNum,pageSize);
        return blogList.toString();
    }

    @RequestMapping("/look")
    @ResponseBody
    public String testMe(){
        PageInfo<Blog> blogList = blogService.getIndexBlog(0,pageSize);
        return blogList.toString();
    }

}
