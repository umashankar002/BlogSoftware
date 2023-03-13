package com.myblog1.service;

import com.myblog1.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment (long postId,CommentDTO commentDTO);
    List<CommentDTO> getCommentByPostId(long postId);

    CommentDTO updateComment(long postId, long id, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);
}
