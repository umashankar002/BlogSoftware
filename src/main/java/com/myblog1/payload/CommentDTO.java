package com.myblog1.payload;

import com.myblog1.entities.Post;
import lombok.Data;

@Data
public class CommentDTO {

    private long id;
    private String body;
    private String email;
    private String name;
}
