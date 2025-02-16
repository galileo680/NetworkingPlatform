package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.ExperienceResponseDTO;
import com.bartek.NetworkingPlatform.entity.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public ExperienceResponseDTO toDTO(Experience experience) {
        return ExperienceResponseDTO.builder()
                .profileId(experience.getProfile().getId())
                .companyName(experience.getCompanyName())
                .position(experience.getPosition())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .description(experience.getDescription())
                .build();
    }
}
