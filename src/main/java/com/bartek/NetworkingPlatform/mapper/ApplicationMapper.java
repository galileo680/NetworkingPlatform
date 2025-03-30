package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.application.ApplicationResponse;
import com.bartek.NetworkingPlatform.entity.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {

    private final JobPostingMapper jobPostingMapper;
    private final UserMapper userMapper;

    public ApplicationResponse mapToApplicationResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .job(jobPostingMapper.mapToJobPostingResponse(application.getJob()))
                .applicant(userMapper.mapToUserSummaryResponse(application.getUser()))
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }
}
