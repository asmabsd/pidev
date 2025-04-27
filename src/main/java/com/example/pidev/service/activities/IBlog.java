package com.example.pidev.service.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.entity.activities.Region;

import java.util.List;
import java.util.Optional;

public interface IBlog {

    List<Blog> getAllBlogs();

    Optional<Blog> getBlogById(Long idBlog);

    Blog saveBlog(Blog blog);
    Blog updateBlog(Blog blog);

    void deleteBlog(Long idBlog);

    List<Blog> getBlogsByUserId(Integer userId);

    // New methods for affectation
    public Blog affectActivityToBlog(Long idBlog, List<Long> idActivity);
    public Blog addBlogAndAffectActivity(Blog blog, List<Long> idActivity);
    Activity affectActivitiesToBlog(Long idBlog, List<Long> idActivities);

    List<Blog> getBlogsByRegion(Region region);
}
