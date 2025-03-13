package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.comment.CommentCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.comment.CommentUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.comment.CommentResponse;
import com.bartek.NetworkingPlatform.entity.Comment;
import com.bartek.NetworkingPlatform.entity.Post;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.mapper.CommentMapper;
import com.bartek.NetworkingPlatform.repository.CommentRepository;
import com.bartek.NetworkingPlatform.repository.PostRepository;
import com.bartek.NetworkingPlatform.service.CommentService;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    private final UserUtil userUtil;

    @Override
    @Transactional
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found"));

        User user = userUtil.getCurrentUser();

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.mapToCommentResponse(savedComment);
    }

    @Override
    public CommentResponse getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        return commentMapper.mapToCommentResponse(comment);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        Long userId = userUtil.getCurrentUser().getId();

        //if (!comment.getUser().getId().equals(userId)) {
        //    throw new UnauthorizedException("Unauthorized action");
        //}

        comment.setContent(request.getContent());
        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.mapToCommentResponse(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Page<CommentResponse> getCommentsByPostId(Long postId, Pageable pageable) {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException("Post with id " + postId + " not found");
        }

        Page<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId, pageable);

        return comments.map(commentMapper::mapToCommentResponse);
    }
}
