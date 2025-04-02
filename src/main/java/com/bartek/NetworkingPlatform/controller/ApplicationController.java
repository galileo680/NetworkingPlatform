package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.application.ApplicationCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.application.ApplicationStatusUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.application.ApplicationResponse;
import com.bartek.NetworkingPlatform.enums.ApplicationStatus;
import com.bartek.NetworkingPlatform.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody ApplicationCreateRequest request
    ) {
        ApplicationResponse response = applicationService.createApplication(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{applicationId}")
    @PreAuthorize("@applicationAuthorizationService.canAccessApplication(authentication, #applicationId)")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable Long applicationId
    ) {
        ApplicationResponse response = applicationService.getApplicationById(applicationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{applicationId}/status")
    @PreAuthorize("@applicationAuthorizationService.canUpdateApplicationStatus(authentication, #applicationId)")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request
    ) {
        ApplicationResponse response = applicationService.updateApplicationStatus(applicationId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{applicationId}")
    @PreAuthorize("@applicationAuthorizationService.canDeleteApplication(authentication, #applicationId)")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long applicationId
    ) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("@jobAuthorizationService.canViewJobApplications(authentication, #jobId)")
    public ResponseEntity<Page<ApplicationResponse>> getApplicationsByJobId(
            @PathVariable Long jobId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<ApplicationResponse> responses = applicationService.getApplicationsByJobId(jobId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/job/{jobId}/status/{status}")
    @PreAuthorize("@jobAuthorizationService.canViewJobApplications(authentication, #jobId)")
    public ResponseEntity<Page<ApplicationResponse>> getApplicationsByJobIdAndStatus(
            @PathVariable Long jobId,
            @PathVariable ApplicationStatus status,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<ApplicationResponse> responses = applicationService.getApplicationsByJobIdAndStatus(jobId, status, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/my")
    public ResponseEntity<Page<ApplicationResponse>> getMyApplications(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<ApplicationResponse> responses = applicationService.getApplicationsByUserId(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/my/status/{status}")
    public ResponseEntity<Page<ApplicationResponse>> getMyApplicationsByStatus(
            @PathVariable ApplicationStatus status,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<ApplicationResponse> responses = applicationService.getApplicationsByUserIdAndStatus(status, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<Page<ApplicationResponse>> getApplicationsByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<ApplicationResponse> responses = applicationService.getApplicationsByUserId(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserAppliedForJob(
            @RequestParam Long jobId
    ) {
        boolean hasApplied = applicationService.hasUserAppliedForJob(jobId);
        return ResponseEntity.ok(hasApplied);
    }
}