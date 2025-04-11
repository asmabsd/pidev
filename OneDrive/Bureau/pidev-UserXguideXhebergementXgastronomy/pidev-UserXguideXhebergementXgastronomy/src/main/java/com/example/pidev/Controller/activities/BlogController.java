package com.example.pidev.Controller.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.service.activities.IBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    IBlog blogService;
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return new ResponseEntity<>(blogService.getAllBlogs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(blog -> new ResponseEntity<>(blog, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addblog")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        return new ResponseEntity<>(blogService.saveBlog(blog), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        return blogService.getBlogById(id)
                .map(existingBlog -> {
                    blog.setIdBlog(id);
                    return new ResponseEntity<>(blogService.updateBlog(blog), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(blog -> {
                    blogService.deleteBlog(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Blog>> getBlogsByUserId(@PathVariable Integer userId) {
        return new ResponseEntity<>(blogService.getBlogsByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/affectActivityToBlog/{idBlog}/{idActivity}")
    public Blog affectActivityToBlog(@PathVariable Long idBlog, @PathVariable List<Long> idActivity) {
        return blogService.affectActivityToBlog(idBlog, idActivity);
    }

    @PutMapping("/affectActivitiesToBlog/{idBlog}")
    public Activity affectActivitiesToBlog(@PathVariable Long idBlog, @RequestBody List<Long> idActivities) {
        return blogService.affectActivitiesToBlog(idBlog, idActivities);
    }

    @PostMapping("/addBlogAndAffectActivity/{idActivity}")
    public Blog addBlogAndAffectActivity( @RequestBody Blog blog, @PathVariable List<Long> idActivity) {
        return blogService.addBlogAndAffectActivity(blog, idActivity);
    }



}
