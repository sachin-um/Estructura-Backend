package com.Estructura.API.service;


import com.Estructura.API.model.*;
import com.Estructura.API.repository.BlogRepository;
import com.Estructura.API.repository.BlogTagRepository;
import com.Estructura.API.requests.blogs.BlogRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final BlogTagRepository blogTagRepository;
    @Override
    public GenericAddOrUpdateResponse<BlogRequest> saveOrUpdateBlog(BlogRequest blogRequest) throws IOException {
        GenericAddOrUpdateResponse<BlogRequest> response=new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(blogRequest)) {
            Optional<User> user = userService.findById(blogRequest.getUserId());
            if (user.isPresent()) {
                String mainImageName = null;
                if (blogRequest.getMainImage() != null) {
                    mainImageName = StringUtils.cleanPath(blogRequest.getMainImage().getOriginalFilename());
                }
                Blog blog = Blog.builder()
                        .title(blogRequest.getTitle())
                        .content(blogRequest.getContent())
                        .user(user.get())
                        .createdBy(blogRequest.getUserId())
                        .build();

                if (mainImageName != null) {
                    blog.setMainImage(mainImageName);
                    blog.setMainImageName(FileUploadUtil.generateFileName(mainImageName));
                }

                Blog theBlog=blogRepository.save(blog);
                if (blogRequest.getTags()!=null){
                    blogRequest.getTags().forEach(blogTag->{
                        saveBlogTag(theBlog,blogTag);
                    });
                }
                String uploadDir = "./files/blog-files/" + theBlog.getUser().getId() + "/" + theBlog.getId();
                if (blogRequest.getMainImage() !=null){
                    FileUploadUtil.saveFile(uploadDir, blogRequest.getMainImage(), mainImageName);
                }

                response.setSuccess(true);
                response.setId(theBlog.getId());
            } else {
                response.addError("fatal", "Invalid User ID");
            }
        }
        else {
            response.addError("fatal", "Bad Request");
        }
        return response;
    }

    @Override
    public ResponseEntity<Blog> getBlogById(Long id) {
        Optional<Blog> blog= blogRepository.findById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Blog>> getBlogsbyUser(User user) {
        List<Blog> blogs=blogRepository.findAllByUser(user);
        if (!blogs.isEmpty()){
            return ResponseEntity.ok(blogs);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<Blog>> getBlogsbyTags(Set<Tag> tags) {
        List<Blog> blogs=blogRepository.findByTagsIn(tags);
        if (!blogs.isEmpty()){
            return ResponseEntity.ok(blogs);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs=blogRepository.findAll();
        if (!blogs.isEmpty()){
            return ResponseEntity.ok(blogs);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public GenericDeleteResponse<Long> deleteBlog(Blog blog) {
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        blogRepository.delete(blog);
        Optional<Blog> theBlog= blogRepository.findById(blog.getId());
        if (theBlog.isPresent()){
            response.setSuccess(false);
            response.setMessage("Somthing went wrong please try again");

        }
        else {
            response.setSuccess(true);
        }
        return response;
    }

    private void saveBlogTag(Blog blog,Tag tag){
        var theTag=BlogTag.builder()
                .tag(tag)
                .blog(blog)
                .build();
        blogTagRepository.save(theTag);
    }
}
