package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.post.PostCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.post.PostUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.post.PostDetailResponse;
import com.bartek.NetworkingPlatform.dto.response.post.PostResponse;
import com.bartek.NetworkingPlatform.entity.Like;
import com.bartek.NetworkingPlatform.entity.Post;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.mapper.PostMapper;
import com.bartek.NetworkingPlatform.repository.CommentRepository;
import com.bartek.NetworkingPlatform.repository.LikeRepository;
import com.bartek.NetworkingPlatform.repository.PostRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import com.bartek.NetworkingPlatform.service.PostService;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    private final PostMapper postMapper;

    private final UserUtil userUtil;

    @Override
    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        User user = userUtil.getCurrentUser();

        Post post = Post.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return postMapper.mapToPostResponse(savedPost, user.getId());
    }

    @Override
    public PostDetailResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found"));

        User user = userUtil.getCurrentUser();

        return postMapper.mapToPostDetailResponse(post, user.getId());
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found"));

        User user = userUtil.getCurrentUser();

        post.setContent(request.getContent());
        Post updatedPost = postRepository.save(post);

        return postMapper.mapToPostResponse(updatedPost, user.getId());
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found"));


        postRepository.delete(post);
    }

    @Override
    public Page<PostResponse> getUserPosts(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found"));

        Page<Post> posts = postRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        return posts.map(post -> postMapper.mapToPostResponse(post, user.getId()));
    }

    @Override
    public Page<PostResponse> getUserFeed(Pageable pageable) {
        Long userId = userUtil.getCurrentUser().getId();

        Page<Post> posts = postRepository.findPostsForUserFeed(userId, pageable);

        return posts.map(post -> postMapper.mapToPostResponse(post, userId));
    }

    @Override
    public Page<PostResponse> searchPosts(String query, Pageable pageable) {
        Page<Post> posts = postRepository.findByContentContainingIgnoreCaseOrderByCreatedAtDesc(query, pageable);

        return posts.map(post -> postMapper.mapToPostResponse(post, userUtil.getCurrentUser().getId()));
    }

    @Override
    @Transactional
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found"));

        User user = userUtil.getCurrentUser();

        boolean alreadyLiked = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());

        if (!alreadyLiked) {
           Like like = Like.builder()
                   .post(post)
                   .user(user)
                   .build();

           likeRepository.save(like);
        }
    }

    @Override
    @Transactional
    public void unlikePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException("Post with id: " + postId + " not found");
        }

        User user = userUtil.getCurrentUser();

        likeRepository.deleteByPostIdAndUserId(postId, user.getId());
    }
}
