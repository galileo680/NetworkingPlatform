package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.ExperienceCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.ExperienceUpdateRequest;
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

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceResponseDTO> updateExperience(
            @PathVariable Long id,
            @RequestBody ExperienceUpdateRequest requestDto
    ) {
        ExperienceResponseDTO updated = experienceService.updateExperience(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<ExperienceResponseDTO>> getAllExperiencesForProfile(
            @PathVariable Long profileId
    ) {
        List<ExperienceResponseDTO> experiences = experienceService.getAllExperiencesForProfile(profileId);
        return ResponseEntity.ok(experiences);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long id
    ) {
        experienceService.deleteExperience(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
