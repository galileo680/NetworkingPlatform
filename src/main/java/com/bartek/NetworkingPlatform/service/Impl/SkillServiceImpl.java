package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.ProfileSkillsRequest;
import com.bartek.NetworkingPlatform.dto.request.SkillCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.SkillUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.SkillResponse;
import com.bartek.NetworkingPlatform.entity.Profile;
import com.bartek.NetworkingPlatform.entity.Skill;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.mapper.SkillMapper;
import com.bartek.NetworkingPlatform.repository.ProfileRepository;
import com.bartek.NetworkingPlatform.repository.SkillRepository;
import com.bartek.NetworkingPlatform.service.SkillService;
import com.bartek.NetworkingPlatform.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final ProfileRepository profileRepository;

    private final SkillMapper skillMapper;

    @Override
    @Transactional
    public SkillResponse createSkill(SkillCreateRequest request) {
        if (skillRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(request.getName() + " skill already exists");
        }

        Skill skill = Skill.builder()
                .name(request.getName())
                .build();

        Skill savedSkill = skillRepository.save(skill);

        return skillMapper.toDTO(savedSkill);
    }

    @Override
    public SkillResponse getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill not found with id: " + id));
        return skillMapper.toDTO(skill);
    }

    @Override
    public Set<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SkillResponse> searchSkills(String query) {
        return skillRepository.findByNameContainingIgnoreCase(query).stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public SkillResponse updateSkill(Long id, SkillUpdateRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill not found with id: " + id));

        skillRepository.findByName(request.getName())
                .ifPresent(existingSkill -> {
                    if (!existingSkill.getId().equals(id)) {
                        throw new ResourceAlreadyExistsException(request.getName() + " skill already exists");
                    }
                });

        skill.setName(request.getName());
        Skill updatedSkill = skillRepository.save(skill);

        return skillMapper.toDTO(updatedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
           throw new NotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Set<SkillResponse> addSkillsToProfile(Long profileId, ProfileSkillsRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        Set<Skill> skillsToAdd = request.getSkillIds().stream()
                .map(skillId -> skillRepository.findById(skillId)
                        .orElseThrow(() -> new NotFoundException("Skill not found with id: " + skillId)))
                .collect(Collectors.toSet());

        profile.getSkills().addAll(skillsToAdd);
        profileRepository.save(profile);

        return profile.getSkills().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<SkillResponse> removeSkillsFromProfile(Long profileId, ProfileSkillsRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        Set<Skill> currentSkills = new HashSet<>(profile.getSkills());
        Set<Skill> filteredSkills = currentSkills.stream()
                .filter(skill -> !request.getSkillIds().contains(skill.getId()))
                .collect(Collectors.toSet());

        profile.setSkills(filteredSkills);
        profileRepository.save(profile);

        return profile.getSkills().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SkillResponse> getProfileSkills(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return profile.getSkills().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
