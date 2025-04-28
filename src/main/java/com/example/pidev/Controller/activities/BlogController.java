package com.example.pidev.Controller.activities;

import com.example.pidev.Interface.User.IUser;
import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.entity.activities.Region;
import com.example.pidev.service.activities.IBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    IBlog blogService;

    @Autowired
    IUser userService;
    @GetMapping("/getAllBlogs")
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
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Blog> addBlog(@RequestBody Map<String, Object> rawData) {
        System.out.println("Raw request: " + rawData);

        Blog blog = new Blog();
        blog.setTitle((String) rawData.get("title"));
        blog.setContent((String) rawData.get("content"));
        blog.setPublication((String) rawData.get("publication"));
        String regionStr = (String) rawData.get("region");
        if (regionStr != null) {
            try {
                Region region = Region.valueOf(regionStr); // Attention : respecter la casse !
                blog.setRegion(region);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid region: " + regionStr);
                return ResponseEntity.badRequest().build();
            }
        }
        // Extract user ID from the nested user object
        Map<String, Object> userMap = (Map<String, Object>) rawData.get("user");
        if (userMap == null || userMap.get("id") == null) {
            return ResponseEntity.badRequest().build();
        }

        Long userId = Long.valueOf(userMap.get("id").toString());
        User user = userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        blog.setUser(user);
        Blog savedBlog = blogService.saveBlog(blog);
        return ResponseEntity.ok(savedBlog);
    }
    @PutMapping("/updateblog")
    public Blog updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }


    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
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

    @GetMapping("/blogs")
    public List<Blog> getBlogsByRegion(@RequestParam String region) {
        Region enumRegion = Region.valueOf(region);
        return blogService.getBlogsByRegion(enumRegion);
    }



}
