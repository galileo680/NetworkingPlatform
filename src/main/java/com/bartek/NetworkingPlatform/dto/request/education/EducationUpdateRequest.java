package com.bartek.NetworkingPlatform.dto.request.education;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EducationUpdateRequest {

    @Size(min = 2, max = 100, message = "Institution must be between 2 and 100 characters.")
    private String institution;

    @Size(min = 2, max = 100, message = "Degree must be between 2 and 100 characters.")
    private String degree;

    @Size(min = 2, max = 100, message = "Field of study must be between 2 and 100 characters.")
    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;
}
