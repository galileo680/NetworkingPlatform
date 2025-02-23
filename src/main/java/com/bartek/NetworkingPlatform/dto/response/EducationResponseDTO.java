package com.bartek.NetworkingPlatform.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationResponseDTO {

    private Long id;
    private Long profileId;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}