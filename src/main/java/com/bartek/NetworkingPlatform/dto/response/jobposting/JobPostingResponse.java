package com.bartek.NetworkingPlatform.dto.response.jobposting;

import com.bartek.NetworkingPlatform.dto.response.company.CompanyResponse;
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
public class JobPostingResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private CompanyResponse company;
    private UserSummaryResponse postedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
