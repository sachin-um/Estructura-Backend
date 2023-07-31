package com.Estructura.API.controller;

import com.Estructura.API.model.Blog;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.blogs.BlogRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.service.BlogService;
import com.Estructura.API.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blogs")
public class BlogController {
    private final BlogService blogService;
    private final UserService userService;

    @PostMapping("/add")
    public GenericAddOrUpdateResponse<BlogRequest> addBlog(
            @ModelAttribute BlogRequest blogRequest) throws IOException {
        return blogService.saveOrUpdateBlog(blogRequest);
    }


    @GetMapping("/blog/{blogId}") // resp entity <Project>
    public ResponseEntity<Blog> blog(@PathVariable("blogId") Long blogId){
        return blogService.getBlogById(blogId);
    }

    @GetMapping("/all/{userid}") // resp ent <List<Project
    public ResponseEntity<List<Blog>> getUserBlogs(@PathVariable("userid") int userid){
        Optional<User> user=userService.findById(userid);
        if (user.isPresent()){
            return blogService.getBlogsbyUser(user.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/all") // resp ent <List<Project
    public ResponseEntity<List<Blog>> getBlogs(){
       return blogService.getAllBlogs();
    }

    @PostMapping("/update/{blogId}") // equal to add
    public GenericAddOrUpdateResponse<BlogRequest> updateBlog(
            @PathVariable("blogId") Long blogId,
            @ModelAttribute BlogRequest blogRequest) throws IOException {
        GenericAddOrUpdateResponse<BlogRequest> response=new GenericAddOrUpdateResponse<>();
        return blogService.updateBlog(blogRequest,blogId);
    }

    @DeleteMapping("/delete/{blogId}") // generic bool
    public GenericDeleteResponse<Long> deleteBlog(@PathVariable("blogId") Long blogId) {
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        ResponseEntity<Blog> blog=blogService.getBlogById(blogId);

        if (blog.getStatusCode().is2xxSuccessful()) {
            return blogService.deleteBlog(blog.getBody());

        } else {
            response.setSuccess(false);
            return response;
        }
    }



}
