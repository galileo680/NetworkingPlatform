package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.jobposting.JobPostingCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.jobposting.JobPostingUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.jobposting.JobPostingResponse;
import com.bartek.NetworkingPlatform.dto.response.jobposting.JobSearchRequest;
import com.bartek.NetworkingPlatform.service.JobPostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<JobPostingResponse> createJobPosting(
            @Valid @RequestBody JobPostingCreateRequest request
    ) {
        JobPostingResponse response = jobPostingService.createJobPosting(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobPostingResponse> getJobPostingById(
            @PathVariable Long jobId
    ) {
        JobPostingResponse response = jobPostingService.getJobPostingById(jobId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<JobPostingResponse> updateJobPosting(
            @PathVariable Long jobId,
            @Valid @RequestBody JobPostingUpdateRequest request
    ) {
        JobPostingResponse response = jobPostingService.updateJobPosting(jobId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteJobPosting(
            @PathVariable Long jobId
    ) {
        jobPostingService.deleteJobPosting(jobId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<JobPostingResponse>> getAllJobPostings(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<JobPostingResponse> responses = jobPostingService.getAllJobPostings(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<JobPostingResponse>> getJobPostingsByCompany(
            @PathVariable Long companyId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<JobPostingResponse> responses = jobPostingService.getJobPostingsByCompany(companyId, pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<JobPostingResponse>> getJobPostingsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<JobPostingResponse> responses = jobPostingService.getJobPostingsByUser(userId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<JobPostingResponse>> searchJobPostings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long companyId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        JobSearchRequest searchRequest = JobSearchRequest.builder()
                .title(title)
                .location(location)
                .companyId(companyId)
                .build();

        Page<JobPostingResponse> responses = jobPostingService.searchJobPostings(searchRequest, pageable);
        return ResponseEntity.ok(responses);
    }
}