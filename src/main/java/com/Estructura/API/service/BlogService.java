package com.Estructura.API.service;

import com.Estructura.API.model.Blog;
import com.Estructura.API.model.Tag;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.blogs.BlogRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BlogService {
    GenericAddOrUpdateResponse<BlogRequest> saveOrUpdateBlog(
        @ModelAttribute BlogRequest blogRequest) throws IOException;

    GenericAddOrUpdateResponse<BlogRequest> updateBlog(
        @ModelAttribute BlogRequest blogRequest,
        long blogId) throws IOException;

    ResponseEntity<Blog> getBlogById(Long id);

    ResponseEntity<List<Blog>> getBlogsByUser(User user);

    ResponseEntity<List<Blog>> getBlogsByTags(Set<Tag> tags);

    ResponseEntity<List<Blog>> getAllBlogs();

    GenericDeleteResponse<Long> deleteBlog(Blog blog);
}
