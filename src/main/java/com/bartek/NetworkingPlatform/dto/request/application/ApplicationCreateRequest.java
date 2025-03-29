package com.bartek.NetworkingPlatform.dto.request.application;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreateRequest {

    @NotNull(message = "Job IDs cannot be empty")
    private Long jobId;
}
