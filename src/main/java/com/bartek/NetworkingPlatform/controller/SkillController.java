package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.ProfileSkillsRequest;
import com.bartek.NetworkingPlatform.dto.request.SkillCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.SkillUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.SkillResponse;
import com.bartek.NetworkingPlatform.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillResponse> createSkill(
            @Valid @RequestBody SkillCreateRequest request
    ) {
        return new ResponseEntity<>(skillService.createSkill(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @GetMapping
    public ResponseEntity<Set<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/search")
    public ResponseEntity<Set<SkillResponse>> searchSkills(
            @RequestParam String query
    ) {
        return ResponseEntity.ok(skillService.searchSkills(query));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id,
                                                     @Valid @RequestBody SkillUpdateRequest request) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{profileId}/skills")
    public ResponseEntity<Set<SkillResponse>> getProfileSkills(@PathVariable Long profileId) {
        return ResponseEntity.ok(skillService.getProfileSkills(profileId));
    }

    @PostMapping("/{profileId}/skills")
    @PreAuthorize("@profileAuthorizationService.canModifyProfile(authentication, #profileId)")
    public ResponseEntity<Set<SkillResponse>> addSkillsToProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileSkillsRequest request) {
        return ResponseEntity.ok(skillService.addSkillsToProfile(profileId, request));
    }

    @DeleteMapping("/{profileId}/skills")
    @PreAuthorize("@profileAuthorizationService.canModifyProfile(authentication, #profileId)")
    public ResponseEntity<Set<SkillResponse>> removeSkillsFromProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileSkillsRequest request) {
        return ResponseEntity.ok(skillService.removeSkillsFromProfile(profileId, request));
    }
}
