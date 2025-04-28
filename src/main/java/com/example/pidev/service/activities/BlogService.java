package com.example.pidev.service.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.entity.activities.Region;
import com.example.pidev.repository.activities.ActivityRepository;
import com.example.pidev.repository.activities.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor

public class BlogService implements IBlog {

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    ActivityRepository activityRepository;

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();


    }

    @Override
    public Optional<Blog> getBlogById(Long idBlog) {
        return blogRepository.findById(idBlog);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long idBlog) {
        blogRepository.deleteById(idBlog);


    }

    @Override
    public List<Blog> getBlogsByUserId(Integer userId) {
        return blogRepository.findByUserId(userId);
    }

    public Blog affectActivityToBlog(Long idBlog, List<Long> idActivity) {
        Blog blog = blogRepository.findById(idBlog)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        List<Activity> activities = activityRepository.findAllById(idActivity);

        // Clear existing associations by setting blog=null for any current activities
        for (Activity existingActivity : blog.getActivities()) {
            existingActivity.setBlog(null);
        }

        // Associate new activities with blog
        for (Activity activity : activities) {
            activity.setBlog(blog);
        }

        blog.setActivities(activities);
        return blogRepository.save(blog);
    }

    @Override
    public Blog addBlogAndAffectActivity(Blog blog, List<Long> idActivity) {
        List<Activity> activity = activityRepository.findAllById(idActivity);
        Blog savedBlog = blogRepository.save(blog);

        blog.setActivities(activity);
        return    blogRepository.save(blog);
    }

    @Override
    public Activity affectActivitiesToBlog(Long idBlog, List<Long> idActivities) {
        List<Activity> activities = activityRepository.findAllById(idActivities);
        Blog blog = blogRepository.findById(idBlog).get();

        for (Activity activity : activities) {
            activity.setBlog(blog);
            activityRepository.save(activity);
        }

        Activity lastActivity = activities.isEmpty() ? null : activities.get(activities.size() - 1);
        return lastActivity;
    }

    public List<Blog> getBlogsByRegion(Region region) {
        return blogRepository.findByRegion(region);
    }
}
