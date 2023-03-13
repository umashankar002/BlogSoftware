package com.myblog1.controller;

import com.myblog1.payload.CommentDTO;
import com.myblog1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId, @RequestBody CommentDTO commentDTO){
        CommentDTO dto = commentService.createComment(postId, commentDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getAllCommentsByPostId(@PathVariable("postId") long postId){
        List<CommentDTO> dto = commentService.getCommentByPostId(postId);
        return dto;
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") long postId,@PathVariable("id") long id,@RequestBody CommentDTO commentDTO){
        CommentDTO dto = commentService.updateComment(postId, id, commentDTO);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,@PathVariable("id") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment is deleted!!!",HttpStatus.OK);
    }
}
