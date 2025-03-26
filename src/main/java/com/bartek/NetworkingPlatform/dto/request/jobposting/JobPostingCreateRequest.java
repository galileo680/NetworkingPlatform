package com.bartek.NetworkingPlatform.dto.request.jobposting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingCreateRequest {

    @NotBlank(message = "Job posting title cannot be empty")
    @Size(min = 5, max = 100, message = "The title of the job posting must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Job posting description cannot be empty")
    @Size(min = 50, max = 5000, message = "The description of the job posting must be between 50 and 5000 characters")
    private String description;

    @NotBlank(message = "Location cannot be empty")
    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @NotNull(message = "Company ID cannot be empty")
    private Long companyId;
}