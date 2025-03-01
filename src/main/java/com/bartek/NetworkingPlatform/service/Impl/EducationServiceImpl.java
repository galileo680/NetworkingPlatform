package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.EducationCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.EducationUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.EducationResponseDTO;
import com.bartek.NetworkingPlatform.entity.Education;
import com.bartek.NetworkingPlatform.entity.Profile;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.mapper.EducationMapper;
import com.bartek.NetworkingPlatform.repository.EducationRepository;
import com.bartek.NetworkingPlatform.repository.ProfileRepository;
import com.bartek.NetworkingPlatform.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final ProfileRepository profileRepository;

    private final EducationMapper educationMapper;

    @Override
    public EducationResponseDTO createEducation(Long profileId, EducationCreateRequest request) {
        Profile profile = profileRepository.findById(request.getProfileId())
                .orElseThrow(() -> new NotFoundException(
                        "Profile not found with id: " + request.getProfileId()));

        Education education = Education.builder()
                .profile(profile)
                .institution(request.getInstitution())
                .degree(request.getDegree())
                .fieldOfStudy(request.getFieldOfStudy())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .build();

        Education saved = educationRepository.save(education);

        return educationMapper.toDTO(saved);
    }

    @Override
    public EducationResponseDTO updateEducation(Long educationId, EducationUpdateRequest request) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException(
                        "Education not found with id: " + educationId));

        education.setInstitution(request.getInstitution());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setDescription(request.getDescription());

        educationRepository.save(education);

        return educationMapper.toDTO(education);
    }

    @Override
    public void deleteEducation(Long educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException("Education not found with id: " + educationId));

        educationRepository.delete(education);
    }

    @Override
    public List<EducationResponseDTO> getAllEducationsForProfile(Long profileId) {
        List<Education> educationList = educationRepository.findByProfileId(profileId);

        return educationList.stream()
                .map(educationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EducationResponseDTO getEducationById(Long educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException("Education not found with id: " + educationId));

        return educationMapper.toDTO(education);
    }
}
