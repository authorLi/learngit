package com.mycclee.blog.controller;

import com.github.pagehelper.PageInfo;
import com.mycclee.blog.beans.Blog;
import com.mycclee.blog.beans.Catalog;
import com.mycclee.blog.beans.User;
import com.mycclee.blog.beans.Vote;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.CatalogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.service.VoteService;
import com.mycclee.blog.vo.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@PropertySource("classpath:pagination.properties")
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    @Value("${pageSize}")
    private Integer pageSize;

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private CatalogService catalogService;
    @Value("${file.server.url}")
    private String fileServerUrl;

    @GetMapping
    public String toMySpace() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return "redirect:/u/" + username;
    }

    @GetMapping("/{username}")
    public ModelAndView userSpace(@PathVariable("username") String username, @RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum, Model model) {
        User user = userService.getUserByUsername(username);
        PageInfo<Blog> blogList = blogService.getBlogByOwner(pageNum,pageSize,user.getId());
        model.addAttribute("userModel", user);
        model.addAttribute("blogModel", blogList);
        return new ModelAndView("userspace/u", "model", model);
    }

    @RequestMapping("/ok")
    @ResponseBody
    public String ok(@RequestParam("pageNum") Integer pageNum,@RequestParam("uid") Long uid){
        return blogService.getBlogByOwner(pageNum,pageSize,uid).toString();
    }

    @GetMapping("/{username}/blogs")
    public ModelAndView listBlogsByOrder(@PathVariable("username") String username,
                                         @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                         @RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "async", required = false) boolean async,
                                         @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                         Model model) {
        System.out.println(order + " " + " " + keyword + " " + async);
        User user = userService.getUserByUsername(username);
        PageInfo<Blog> list = null;
        if (keyword != null && keyword.isEmpty() == false) {
            list = blogService.getBlogsByTags(pageNum,pageSize,new Blog(keyword,user.getId()));
            order = "";
        }
        if (order.equals("hot")) {
            list = blogService.listOwnerBlogsByHot(pageNum,pageSize,user.getId());
        }
        if (order.equals("new")) {
            list = blogService.listOwnerBlogsByNew(pageNum,pageSize,user.getId());
        }
        model.addAttribute("order", order);
        model.addAttribute("blogModel", list);
        model.addAttribute("userModel", user);
        return new ModelAndView(!"true".equals(async) ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u", "model", model);
    }
@GetMapping("/{username}/blogs/catalog/{catalogId}")
public ModelAndView listBlogsByOrder(@PathVariable("username") String username,
                                     @PathVariable Long catalogId,
                                     @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                     Model model) {
    User user = userService.getUserByUsername(username);
    PageInfo<Blog> list = null;
    if (catalogId != null) {
        list = blogService.getBlogsByCid(pageNum,pageSize,new Blog(catalogId,user.getId()));
    }
    model.addAttribute("blogModel", list);
    model.addAttribute("userModel", user);
    return new ModelAndView("/userspace/u", "model", model);
}

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        blogService.readingIncrease(id);
        Blog blog = blogService.getBlogById(id);
        User user = userService.getUserByUsername(username);
        Subject subject = SecurityUtils.getSubject();
        User cur = userService.getUserByUsername((String) subject.getPrincipal());
        List<Blog> blogList = blogService.getBlogListByOwner(user.getId());
        boolean b = cur.getId().equals(blog.getUid());

        Vote currentVote = null;
        List<Vote> voteList = voteService.selectVoteByUid(cur.getId());
        for (Vote vote : voteList) {
            if (vote.getBlogid().equals(id)) {
                currentVote = vote;
                break;
            }
        }

        Catalog catalog = catalogService.getCatalogById(blog.getCid());

        model.addAttribute("isBlogOwner", b);
        model.addAttribute("curUser", subject.getPrincipal());
        model.addAttribute("currentVote", currentVote);
        model.addAttribute("userModel", user);
        model.addAttribute("blogModel", blog);
        model.addAttribute("blogListModel",blogList);
        model.addAttribute("catalog", catalog);
        return "userspace/blog";
    }

    @GetMapping("/{username}/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return "redirect:/error";
        }

        return "redirect:/index";
    }

    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByUsername((String) subject.getPrincipal());
        List<Catalog> catalogList = catalogService.getOwnerCatalog(user.getId());
        model.addAttribute("blog", new Blog());
        model.addAttribute("userModel", user);
        model.addAttribute("catalogs", catalogList);
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        User user = userService.getUserByUsername(username);
        List<Catalog> catalogList = catalogService.getOwnerCatalog(user.getId());
        model.addAttribute("userModel", user);
        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("catalogs", catalogList);
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    @PostMapping("/{username}/blogs/edit")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        if (null == blog.getId()) {
            User user = userService.getUserByUsername(username);
            blog.setUid(user.getId());
            blog.setCreatetime(new Date());
            Long blogId = null;
            try {
                blogId = blogService.saveBlog(blog);
            } catch (Exception e) {
                return ResponseEntity.ok().body(new Response(false, e.getMessage()));
            }
            String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
            return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
        } else {
            try {
                blogService.updateBlog(blog);
            } catch (Exception e) {
                return ResponseEntity.ok().body(new Response(false, e.getMessage()));
            }
            String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
            return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
        }
    }

    @GetMapping("/profile")
    public ModelAndView profile(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByUsername((String) subject.getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("fileServerUrl", fileServerUrl);
        return new ModelAndView("userspace/profile", "userModel", model);
    }

    @PostMapping("/{username}/profile")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User orginalUser = userService.getUserById(user.getId());
        orginalUser.setEmail(user.getEmail());
        orginalUser.setName(user.getName());
        orginalUser.setPassword(user.getPassword());
        userService.saveOrUpdateUser(orginalUser);
        return "redirect:/u";
    }

    @GetMapping("/{username}/avatar")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }

    @PostMapping("/{username}/avatar")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originalUrl = userService.getUserById(user.getId());
        originalUrl.setAvatar(avatarUrl);
        userService.saveOrUpdateUser(originalUrl);
        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }
}
