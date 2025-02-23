package com.bartek.NetworkingPlatform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EducationCreateRequest {

    @NotNull(message = "Profile ID is required.")
    private Long profileId;

    @NotBlank(message = "Institution cannot be empty.")
    @Size(min = 2, max = 100, message = "Institution must be between 2 and 100 characters.")
    private String institution;

    @NotBlank(message = "Degree cannot be empty.")
    @Size(min = 2, max = 100, message = "Degree must be between 2 and 100 characters.")
    private String degree;

    @NotBlank(message = "Field of study cannot be empty.")
    @Size(min = 2, max = 100, message = "Field of study must be between 2 and 100 characters.")
    private String fieldOfStudy;

    @NotNull(message = "Start date is required.")
    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;
}