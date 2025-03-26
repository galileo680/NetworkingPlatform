package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.jobposting.JobPostingResponse;
import com.bartek.NetworkingPlatform.entity.JobPosting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingMapper {

    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    public JobPostingResponse mapToJobPostingResponse(JobPosting jobPosting) {
        return JobPostingResponse.builder()
                .id(jobPosting.getId())
                .title(jobPosting.getTitle())
                .description(jobPosting.getDescription())
                .location(jobPosting.getLocation())
                .company(companyMapper.mapToCompanyResponse(jobPosting.getCompany()))
                .postedBy(userMapper.mapToUserSummaryResponse(jobPosting.getPostedBy()))
                .createdAt(jobPosting.getCreatedAt())
                .updatedAt(jobPosting.getUpdatedAt())
                .build();
    }
}
