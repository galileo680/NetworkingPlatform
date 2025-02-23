package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.EducationCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.EducationUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.EducationResponseDTO;

import java.util.List;

public interface EducationService {

    EducationResponseDTO createEducation(EducationCreateRequest request);
    EducationResponseDTO updateEducation(EducationUpdateRequest request);
    EducationResponseDTO deleteEducation(Long educationId);
    List<EducationResponseDTO> getAllEducationsForProfile(Long profileId);
    EducationResponseDTO getEducationById(Long educationId);
}
