package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.EducationResponseDTO;
import com.bartek.NetworkingPlatform.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public EducationResponseDTO toDTO(Education education) {
        return EducationResponseDTO.builder()
                .id(education.getId())
                .profileId(education.getProfile().getId())
                .institution(education.getInstitution())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .build();
    }
}
