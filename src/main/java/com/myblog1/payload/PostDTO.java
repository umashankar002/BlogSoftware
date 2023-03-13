package com.myblog1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDTO {
    private long id;

    @NotNull
    @Size(min=2,message = "Post title should have atleast 2 characters")
    private String title;

    @NotNull
    @Size(min=10,message = "Post description should have atleast 10 characters")
    private String description;

    @NotNull
    @Size(min=5,message = "Post content should have atleast 5 characters")
    private String content;
}
