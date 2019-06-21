package com.mycclee.blog.controller;

import com.mycclee.blog.beans.Catalog;
import com.mycclee.blog.beans.User;
import com.mycclee.blog.service.CatalogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.vo.CatalogVo;
import com.mycclee.blog.vo.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getCatalogList(@RequestParam(value = "username",required = true) String username, Model model){
        System.out.println(username);
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByUsername(username);
        List<Catalog> catalogList = catalogService.getOwnerCatalog(user.getId());
        model.addAttribute("isCatalogsOwner",subject.getPrincipal().equals(username)?true:false);
        model.addAttribute("catalogs",catalogList);
        model.addAttribute("username",user.getUsername());
        return "/userspace/u :: #catalogRepleace";
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody CatalogVo catalogVo){
        System.out.println(catalogVo);
        User user = userService.getUserByUsername(catalogVo.getUsername());
        Catalog catalog = catalogVo.getCatalog();
        catalog.setUid(user.getId());
        try {
            catalogService.insertCatalog(catalog);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }

    @GetMapping("/edit")
    public String getCatalogEdit(Model model){
        Catalog catalog = new Catalog();
        model.addAttribute("catalog",catalog);
        return "/userspace/catalogedit";
    }

    @GetMapping("/edit/{id}")
    public String getCatalogEditById(@PathVariable("id") Long id,Model model){
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog",catalog);
        return "/userspace/catalogedit";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCatalog(@PathVariable("id") Long id,@RequestParam("username") String username){
        try{
            catalogService.deleteCatalog(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }

}
