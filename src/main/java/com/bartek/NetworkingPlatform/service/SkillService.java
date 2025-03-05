package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.profile.ProfileSkillsRequest;
import com.bartek.NetworkingPlatform.dto.request.skill.SkillCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.skill.SkillUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.SkillResponse;

import java.util.Set;

public interface SkillService {

    SkillResponse createSkill(SkillCreateRequest request);
    SkillResponse getSkillById(Long id);
    Set<SkillResponse> getAllSkills();
    Set<SkillResponse> searchSkills(String query);
    SkillResponse updateSkill(Long id, SkillUpdateRequest request);
    void deleteSkill(Long id);
    Set<SkillResponse> addSkillsToProfile(Long profileId, ProfileSkillsRequest request);
    Set<SkillResponse> removeSkillsFromProfile(Long profileId, ProfileSkillsRequest request);
    Set<SkillResponse> getProfileSkills(Long profileId);
}
