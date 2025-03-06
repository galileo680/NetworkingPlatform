package com.bartek.NetworkingPlatform.dto.response.post;

import com.bartek.NetworkingPlatform.dto.response.comment.CommentResponse;
import com.bartek.NetworkingPlatform.dto.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserSummaryResponse author;
    private List<CommentResponse> comments;
    private int likesCount;
    private boolean likedByCurrentUser;
}