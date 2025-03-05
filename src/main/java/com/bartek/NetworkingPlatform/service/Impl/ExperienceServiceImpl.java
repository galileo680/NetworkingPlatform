package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.experience.ExperienceCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.experience.ExperienceUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.ExperienceResponseDTO;
import com.bartek.NetworkingPlatform.entity.Experience;
import com.bartek.NetworkingPlatform.entity.Profile;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.mapper.ExperienceMapper;
import com.bartek.NetworkingPlatform.repository.ExperienceRepository;
import com.bartek.NetworkingPlatform.repository.ProfileRepository;
import com.bartek.NetworkingPlatform.service.ExperienceService;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ProfileRepository profileRepository;

    private final UserUtil userUtil;

    private final ExperienceMapper experienceMapper;

    @Override
    public ExperienceResponseDTO createExperience(ExperienceCreateRequest experienceDTO) {
        User user = userUtil.getCurrentUser();

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException(
                        "Profile not found for user with name = " + user.getFirstname() + " " + user.getLastname()));

        Experience experience = Experience.builder()
                .profile(profile)
                .companyName(experienceDTO.getCompanyName())
                .position(experienceDTO.getPosition())
                .startDate(experienceDTO.getStartDate())
                .endDate(experienceDTO.getEndDate())
                .description(experienceDTO.getDescription())
                .build();

        Experience saved = experienceRepository.save(experience);

        return experienceMapper.toDTO(saved);
    }

    @Override
    public ExperienceResponseDTO updateExperience(Long id, ExperienceUpdateRequest experienceDTO) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Experience not found with id = " + id));

        //TODO: zmienic tak zeby nie zmienialo pol niepodanych w dto
        experience.setCompanyName(experienceDTO.getCompanyName());
        experience.setPosition(experienceDTO.getPosition());
        experience.setStartDate(experienceDTO.getStartDate());
        experience.setEndDate(experienceDTO.getEndDate());
        experience.setDescription(experienceDTO.getDescription());

        Experience updated = experienceRepository.save(experience);

        return experienceMapper.toDTO(updated);
    }

    @Override
    public List<ExperienceResponseDTO> getAllExperiencesForProfile(Long profileId) {
        List<Experience> experiences = experienceRepository.findByProfileId(profileId);
        return experiences.stream()
                .map(experienceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteExperience(Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("Experience not found with id = " + experienceId));

        experienceRepository.delete(experience);
    }
}
