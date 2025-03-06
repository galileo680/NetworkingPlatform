package com.bartek.NetworkingPlatform.dto.response.post;

import com.bartek.NetworkingPlatform.dto.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserSummaryResponse author;
    private int commentsCount;
    private int likesCount;
    private boolean likedByCurrentUser;
}