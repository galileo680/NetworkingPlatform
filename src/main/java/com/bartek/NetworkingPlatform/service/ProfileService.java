package com.bartek.NetworkingPlatform.service;


import com.bartek.NetworkingPlatform.dto.request.ProfileCreateDTO;
import com.bartek.NetworkingPlatform.dto.request.ProfileUpdateDTO;
import com.bartek.NetworkingPlatform.dto.response.ProfileResponseDTO;

public interface ProfileService {
    ProfileResponseDTO createProfile(ProfileCreateDTO profileDTO);
    ProfileResponseDTO updateProfile(ProfileUpdateDTO profileDTO);
    ProfileResponseDTO getProfileByUserId(Long userId);
    void deleteProfile(Long profileId);
}
