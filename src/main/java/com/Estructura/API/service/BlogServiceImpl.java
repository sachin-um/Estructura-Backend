package com.Estructura.API.service;

import com.Estructura.API.model.Blog;
import com.Estructura.API.model.BlogTag;
import com.Estructura.API.model.Tag;
import com.Estructura.API.model.User;
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
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserService userService;
    private final BlogTagRepository blogTagRepository;

    @Override
    public GenericAddOrUpdateResponse<BlogRequest> saveOrUpdateBlog(
        BlogRequest blogRequest) throws IOException {
        GenericAddOrUpdateResponse<BlogRequest> response =
            new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(blogRequest)) {
            Optional<User> user = userService.findById(blogRequest.getUserId());
            if (user.isPresent()) {
                Blog blog = Blog.builder()
                                .title(blogRequest.getTitle())
                                .content(blogRequest.getContent())
                                .user(user.get())
                                .createdBy(blogRequest.getUserId())
                                .creatorName(user.get().getFirstName() + " " +
                                             user.get().getLastName())
                                .build();

                Blog theBlog = blogRepository.save(blog);

                savemainImage(blogRequest, theBlog);

                Blog savedBlog = blogRepository.save(theBlog);

                if (blogRequest.getTags() != null) {
                    blogRequest.getTags().forEach(
                        blogTag -> saveBlogTag(savedBlog, blogTag));
                }

                response.setSuccess(true);
                response.setId(theBlog.getId());
            } else {
                response.addError("fatal", "Invalid User ID");
            }
        } else {
            response.addError("fatal", "Bad Request");
        }
        return response;
    }

    private void savemainImage(BlogRequest blogRequest,
        Blog blog) throws IOException {
        if (blogRequest.getMainImage() != null) {
            var originalFileName = blogRequest.getMainImage()
                                              .getOriginalFilename();
            if (originalFileName != null) {
                String mainImageName = StringUtils.cleanPath(originalFileName);
                String uploadDir =
                    "./files/blog-files/" + blog.getUser().getId() + "/" +
                    blog.getId();
                String generatedName = FileUploadUtil.generateFileName(
                    mainImageName);
                blog.setMainImage(mainImageName);
                blog.setMainImageName(generatedName);
                FileUploadUtil.saveFile(uploadDir, blogRequest.getMainImage(),
                                        generatedName
                );
                // Maybe delete the old file here? or in update method?
            }
        }
    }

    @Override
    public GenericAddOrUpdateResponse<BlogRequest> updateBlog(
        @ModelAttribute BlogRequest blogRequest,
        long blogId) throws IOException {
        GenericAddOrUpdateResponse<BlogRequest> response =
            new GenericAddOrUpdateResponse<>();

        if (response.checkValidity(blogRequest)) {
            Optional<Blog> existingBlog = blogRepository.findById(blogId);
            if (existingBlog.isPresent()) {
                Blog blog = existingBlog.get();
                // Set Every Editable field here
                blog.setTitle(blogRequest.getTitle());
                blog.setContent(blogRequest.getContent());
                // Handle changed files. Leave it alone if no files are
                // supplied in the request
                savemainImage(blogRequest, blog);
                blog = blogRepository.save(blog);
                response.setSuccess(true);
                response.setId(blog.getId());
            } else {
                response.addError("fatal", "Blog doesn't exist");
            }
        }
        return response;
    }

    @Override
    public ResponseEntity<Blog> getBlogById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Blog>> getBlogsByUser(User user) {
        List<Blog> blogs = blogRepository.findAllByUser(user);
        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(blogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<Blog>> getBlogsByTags(Set<Tag> tags) {
        List<Blog> blogs = blogRepository.findByTagsIn(tags);
        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(blogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(blogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public GenericDeleteResponse<Long> deleteBlog(Blog blog) {
        GenericDeleteResponse<Long> response = new GenericDeleteResponse<>();
        blogRepository.delete(blog);
        Optional<Blog> theBlog = blogRepository.findById(blog.getId());
        if (theBlog.isPresent()) {
            response.setSuccess(false);
            response.setMessage("Something went wrong please try again");
        } else {
            response.setSuccess(true);
        }
        return response;
    }

    private void saveBlogTag(Blog blog, Tag tag) {
        var theTag = BlogTag.builder()
                            .tag(tag)
                            .blog(blog)
                            .build();
        blogTagRepository.save(theTag);
    }
}
