package com.Estructura.API.requests.blogs;

import com.Estructura.API.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {
    private String title;

    private String content;

    private MultipartFile MainImage;

    private Integer userId;
    private List<Tag> tags;

}
