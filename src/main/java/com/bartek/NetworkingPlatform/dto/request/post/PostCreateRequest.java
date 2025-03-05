package com.bartek.NetworkingPlatform.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "Post content cannot be empty")
    @Size(min = 1, max = 1000, message = "Post content must be between 1 and 1000 words")
    private String content;
}