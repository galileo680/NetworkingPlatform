package com.bartek.NetworkingPlatform.service;


import com.bartek.NetworkingPlatform.dto.request.ProfileCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.ProfileUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.ProfileResponseDTO;

public interface ProfileService {
    ProfileResponseDTO createProfile(ProfileCreateRequest profileDTO);
    ProfileResponseDTO updateProfile(ProfileUpdateRequest profileDTO);
    ProfileResponseDTO getProfileByUserId(Long userId);
    void deleteProfile(Long profileId);
}
