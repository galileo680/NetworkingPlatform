package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.profile.ProfileCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.profile.ProfileUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.ProfileResponseDTO;
import com.bartek.NetworkingPlatform.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDTO> getProfileByUserId(
            @RequestBody Long userId
    ) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(
            @RequestBody @Valid ProfileCreateRequest profileCreateRequest
    ) {
        return ResponseEntity.ok(profileService.createProfile(profileCreateRequest));
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest
    ) {
        return ResponseEntity.ok(profileService.updateProfile(profileUpdateRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(
            @RequestBody Long userId
    ) {
        profileService.deleteProfile(userId);

        return ResponseEntity.ok().build();
    }
}
