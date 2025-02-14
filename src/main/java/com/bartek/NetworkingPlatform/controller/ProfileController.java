package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.ProfileCreateDTO;
import com.bartek.NetworkingPlatform.dto.request.ProfileUpdateDTO;
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
            @RequestBody @Valid ProfileCreateDTO profileCreateDTO
    ) {
        return ResponseEntity.ok(profileService.createProfile(profileCreateDTO));
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @RequestBody @Valid ProfileUpdateDTO profileUpdateDTO
    ) {
        return ResponseEntity.ok(profileService.updateProfile(profileUpdateDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(
            @RequestBody Long userId
    ) {
        profileService.deleteProfile(userId);

        return ResponseEntity.ok().build();
    }
}
