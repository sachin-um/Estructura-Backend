package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.requests.blogs.BlogRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BlogService {
    public GenericAddOrUpdateResponse<BlogRequest> saveOrUpdateBlog(@ModelAttribute BlogRequest blogRequest) throws IOException;
    public ResponseEntity<Blog> getBlogById(Long id);
    public ResponseEntity<List<Blog>> getBlogsbyUser(User user);

    public ResponseEntity<List<Blog>> getBlogsbyTags(Set<Tag> tags);

    public ResponseEntity<List<Blog>> getAllBlogs();
    public GenericDeleteResponse<Long> deleteBlog(Blog blog);
}
