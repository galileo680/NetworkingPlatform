package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.education.EducationCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.education.EducationUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.EducationResponseDTO;
import com.bartek.NetworkingPlatform.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/education")
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/{profileId}")
    public ResponseEntity<EducationResponseDTO> createEducation(
            @PathVariable Long profileId,
            @RequestBody @Valid EducationCreateRequest request
    ) {
        EducationResponseDTO responseDTO = educationService.createEducation(profileId, request);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponseDTO> updateEducation(
            @PathVariable Long educationId,
            @RequestBody @Valid EducationUpdateRequest request
    ) {
        EducationResponseDTO responseDTO = educationService.updateEducation(educationId, request);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<EducationResponseDTO> deleteEducation(
            @PathVariable Long educationId
    ) {
        educationService.deleteEducation(educationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<EducationResponseDTO>> getAllEducationsForProfile(
            @PathVariable Long profileId
    ) {
        List<EducationResponseDTO> educationList = educationService.getAllEducationsForProfile(profileId);
        return new ResponseEntity<>(educationList, HttpStatus.OK);
    }

    @GetMapping("/{educationId}")
    public ResponseEntity<EducationResponseDTO> getEducationById(
            @PathVariable Long educationId
    ) {
        EducationResponseDTO responseDTO = educationService.getEducationById(educationId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
