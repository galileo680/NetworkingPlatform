package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.experience.ExperienceCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.experience.ExperienceUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.ExperienceResponseDTO;

import java.util.List;

public interface ExperienceService {

    ExperienceResponseDTO createExperience(ExperienceCreateRequest experienceDTO);
    ExperienceResponseDTO updateExperience(Long id, ExperienceUpdateRequest experienceDTO);
    List<ExperienceResponseDTO> getAllExperiencesForProfile(Long profileId);
    void deleteExperience(Long experienceId);

}
