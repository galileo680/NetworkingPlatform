package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.ProfileCreateDTO;
import com.bartek.NetworkingPlatform.dto.request.ProfileUpdateDTO;
import com.bartek.NetworkingPlatform.dto.response.ProfileResponseDTO;
import com.bartek.NetworkingPlatform.entity.Profile;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.exception.ProfileAlreadyExistsException;
import com.bartek.NetworkingPlatform.mapper.ProfileMapper;
import com.bartek.NetworkingPlatform.repository.ProfileRepository;
import com.bartek.NetworkingPlatform.service.ProfileService;
import com.bartek.NetworkingPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    @Override
    public ProfileResponseDTO createProfile(ProfileCreateDTO profileDTO) {
        User user = userService.getCurrentUser();

        if(profileRepository.findByUserId(user.getId()).isPresent()){
            throw new ProfileAlreadyExistsException("User already has a profile");
        }

        Profile profile = Profile.builder()
                .headline(profileDTO.getHeadline())
                .bio(profileDTO.getBio())
                .location(profileDTO.getLocation())
                .user(user)
                .build();

        Profile profileSaved = profileRepository.save(profile);

        return profileMapper.toDTO(profileSaved);
    }

    @Override
    public ProfileResponseDTO updateProfile(ProfileUpdateDTO profileDTO) {
        User user = userService.getCurrentUser();

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        Optional.ofNullable(profileDTO.getHeadline()).ifPresent(profile::setHeadline);
        Optional.ofNullable(profileDTO.getBio()).ifPresent(profile::setBio);
        Optional.ofNullable(profileDTO.getLocation()).ifPresent(profile::setLocation);

        Profile savedProfile = profileRepository.save(profile);

        return profileMapper.toDTO(savedProfile);
    }

    @Override
    public ProfileResponseDTO getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return profileMapper.toDTO(profile);
    }

    @Override
    public void deleteProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        profileRepository.delete(profile);
    }
}
