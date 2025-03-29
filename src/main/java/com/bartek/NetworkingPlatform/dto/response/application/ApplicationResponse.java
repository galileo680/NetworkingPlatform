package com.bartek.NetworkingPlatform.dto.response.application;

import com.bartek.NetworkingPlatform.dto.response.jobposting.JobPostingResponse;
import com.bartek.NetworkingPlatform.dto.response.user.UserSummaryResponse;
import com.bartek.NetworkingPlatform.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private JobPostingResponse job;
    private UserSummaryResponse applicant;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}