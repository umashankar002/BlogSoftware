package com.myblog1.service.IMPL;

import com.myblog1.entities.Comment;
import com.myblog1.entities.Post;
import com.myblog1.exception.ResourceNotFoundException;
import com.myblog1.payload.CommentDTO;
import com.myblog1.repository.CommentRepository;
import com.myblog1.repository.PostRepository;
import com.myblog1.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceIMPL implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public CommentDTO createComment(long postId,CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = maptoComment(commentDTO);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO updateComment(long postId, long id, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        commentRepository.deleteById(commentId);
    }

    Comment maptoComment(CommentDTO commentDTO){
        Comment comment = mapper.map(commentDTO, Comment.class);
//        Comment comment = new Comment();
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }

    CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
//        CommentDTO commentDTO=new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }
}
