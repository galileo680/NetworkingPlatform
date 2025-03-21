package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.comment.CommentResponse;
import com.bartek.NetworkingPlatform.dto.response.post.PostDetailResponse;
import com.bartek.NetworkingPlatform.dto.response.post.PostResponse;
import com.bartek.NetworkingPlatform.dto.response.user.UserSummaryResponse;
import com.bartek.NetworkingPlatform.entity.Comment;
import com.bartek.NetworkingPlatform.entity.Post;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final LikeRepository likeRepository;

    private final UserMapper userMapper;

    public PostResponse mapToPostResponse(Post post, Long currentUserId) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .author(userMapper.mapToUserSummaryResponse(post.getUser()))
                .commentsCount(post.getComments().size())
                .likesCount(post.getLikes().size())
                .likedByCurrentUser(isPostLikedByUser(post, currentUserId))
                .build();
    }

    public PostDetailResponse mapToPostDetailResponse(Post post, Long currentUserId) {
        List<CommentResponse> commentResponses = post.getComments().stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());

        return PostDetailResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .author(userMapper.mapToUserSummaryResponse(post.getUser()))
                .comments(commentResponses)
                .likesCount(post.getLikes().size())
                .likedByCurrentUser(isPostLikedByUser(post, currentUserId))
                .build();
    }

    public CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .author(userMapper.mapToUserSummaryResponse(comment.getUser()))
                .build();
    }

    private boolean isPostLikedByUser(Post post, Long userId) {
        return likeRepository.existsByPostIdAndUserId(post.getId(), userId);
    }
}
