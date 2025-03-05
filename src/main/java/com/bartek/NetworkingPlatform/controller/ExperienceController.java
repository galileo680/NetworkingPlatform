package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.experience.ExperienceCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.experience.ExperienceUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.ExperienceResponseDTO;
import com.bartek.NetworkingPlatform.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ExperienceResponseDTO> createExperience(
            @RequestBody @Valid ExperienceCreateRequest requestDto
    ) {
        ExperienceResponseDTO created = experienceService.createExperience(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponseDTO> updateExperience(
            @PathVariable Long experienceId,
            @RequestBody ExperienceUpdateRequest requestDto
    ) {
        ExperienceResponseDTO updated = experienceService.updateExperience(experienceId, requestDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<ExperienceResponseDTO>> getAllExperiencesForProfile(
            @PathVariable Long profileId
    ) {
        List<ExperienceResponseDTO> experiences = experienceService.getAllExperiencesForProfile(profileId);
        return ResponseEntity.ok(experiences);
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long experienceId
    ) {
        experienceService.deleteExperience(experienceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
