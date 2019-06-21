package com.mycclee.blog.controller;

import com.github.pagehelper.PageInfo;
import com.mycclee.blog.beans.Blog;
import com.mycclee.blog.beans.EsBlog;
import com.mycclee.blog.beans.Good;
import com.mycclee.blog.beans.User;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.EsBlogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.vo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@PropertySource("classpath:pagination.properties")
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private EsBlogService esBlogService;

    @GetMapping
    public String listBlogs(@RequestParam(value = "order",required = false,defaultValue = "new") String order,
                                  @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword,
                                  @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "async" ,required = false, defaultValue = "true") boolean async,
                                  Model model){
//        System.out.println("order:" + order + " keyword:" + keyword);
//
//        PageInfo<Blog> blogList = new PageInfo<>();
//        if ("new".equals(order)){
//            blogList = blogService.getIndexBlog(pageNum,pageSize);
//        }else if ("hot".equals(order)){
//            blogList = blogService.selectAllBlogByHot(pageNum,pageSize);
//        }
//        List<User> userList = userService.getUserList();
//        model.addAttribute("blogModel",blogList);
//        model.addAttribute("userModel",userList);
//        return "/index :: #mainContainer";
        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        boolean isEmpty = true; // 系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) { // 最热查询
                Sort sort = new Sort(Sort.Direction.DESC,"readSize","commentSize","voteSize","createTime");
                Pageable pageable = new PageRequest(pageNum, pageSize, sort);
                page = esBlogService.listHotestEsBlogs(keyword, pageable);
            } else if (order.equals("new")) { // 最新查询
                Sort sort = new Sort(Sort.Direction.DESC,"createTime");
                Pageable pageable = new PageRequest(pageNum, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }

            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = new PageRequest(pageNum, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }

        list = page.getContent();	// 当前所在页面数据列表


        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        return (async==true?"/index :: #mainContainerRepleace":"/index");
    }
}
