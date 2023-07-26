package com.Estructura.API.responses.blogs;

import com.Estructura.API.model.Blog;
import com.Estructura.API.requests.blogs.BlogRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse extends ValidatedResponse<BlogRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("blog")
    private Blog blog;
}
