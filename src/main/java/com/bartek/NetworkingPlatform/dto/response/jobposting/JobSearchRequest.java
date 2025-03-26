package com.bartek.NetworkingPlatform.dto.response.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchRequest {

    private String title;
    private String location;
    private Long companyId;
}