package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.comment.CommentCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.comment.CommentUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.comment.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentResponse createComment(Long postId, CommentCreateRequest request);
    CommentResponse getCommentById(Long commentId);
    CommentResponse updateComment(Long commentId, CommentUpdateRequest request);
    void deleteComment(Long commentId);
    Page<CommentResponse> getCommentsByPostId(Long postId, Pageable pageable);
}
