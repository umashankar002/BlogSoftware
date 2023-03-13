package com.myblog1.service.IMPL;

import com.myblog1.entities.Post;
import com.myblog1.exception.ResourceNotFoundException;
import com.myblog1.payload.PostDTO;
import com.myblog1.payload.PostResponse;
import com.myblog1.repository.PostRepository;
import com.myblog1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceIMPL implements PostService {

    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceIMPL(PostRepository postRepo,ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post= mapToEntity(postDTO);
        Post newPost = postRepo.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepo.findAll(pageable);

        List<Post> content = posts.getContent();

        List<PostDTO> contents= content.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post updatedPost = postRepo.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }

    Post mapToEntity(PostDTO postDTO){
        Post post = mapper.map(postDTO, Post.class);
//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;
    }

    PostDTO mapToDTO(Post post){
        PostDTO postDTO = mapper.map(post, PostDTO.class);
//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;
    }
}
