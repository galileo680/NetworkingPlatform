package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.comment.CommentCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.comment.CommentUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.comment.CommentResponse;
import com.bartek.NetworkingPlatform.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.createComment(postId, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId) {
        CommentResponse response = commentService.getCommentById(commentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/comments/{commentId}")
    @PreAuthorize("@commentAuthorizationService.canModifyComment(authentication, #commentId)")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        CommentResponse response = commentService.updateComment(commentId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("@commentAuthorizationService.canModifyComment(authentication, #commentId)")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Page<CommentResponse>> getCommentsByPostId(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {

        Page<CommentResponse> response = commentService.getCommentsByPostId(postId, pageable);
        return ResponseEntity.ok(response);
    }
}