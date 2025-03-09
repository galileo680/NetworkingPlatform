package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.post.PostCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.post.PostUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.post.PostDetailResponse;
import com.bartek.NetworkingPlatform.dto.response.post.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse createPost(PostCreateRequest request);
    PostDetailResponse getPostById(Long postId);
    PostResponse updatePost(Long postId, PostUpdateRequest request);
    void deletePost(Long postId);
    Page<PostResponse> getUserPosts(Long userId, Pageable pageable);
    Page<PostResponse> getUserFeed(Pageable pageable);
    Page<PostResponse> searchPosts(String query, Pageable pageable);
    void likePost(Long postId);
    void unlikePost(Long postId);
}
