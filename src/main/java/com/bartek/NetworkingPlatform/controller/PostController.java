package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.post.PostCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.post.PostUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.post.PostDetailResponse;
import com.bartek.NetworkingPlatform.dto.response.post.PostResponse;
import com.bartek.NetworkingPlatform.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostCreateRequest request
    ) {
        PostResponse postResponse = postService.createPost(request);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostById(
            @PathVariable Long postId
    ) {
        PostDetailResponse response = postService.getPostById(postId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("@postAuthorizationService.canModifyPost(authentication, #postId)")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        PostResponse response = postService.updatePost(postId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("@postAuthorizationService.canModifyPost(authentication, #postId)")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<PostResponse>> getUserPosts(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<PostResponse> response = postService.getUserPosts(userId, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<PostResponse>> getUserFeed(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<PostResponse> response = postService.getUserFeed(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> searchPosts(
            @RequestParam String query,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<PostResponse> response = postService.searchPosts(query, pageable);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId
    ) {
        postService.likePost(postId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long postId
    ) {
        postService.unlikePost(postId);

        return ResponseEntity.ok().build();
    }
}
