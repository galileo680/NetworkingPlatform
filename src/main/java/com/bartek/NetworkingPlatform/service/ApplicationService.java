package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.application.ApplicationCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.application.ApplicationStatusUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.application.ApplicationResponse;
import com.bartek.NetworkingPlatform.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    ApplicationResponse createApplication(ApplicationCreateRequest request);
    ApplicationResponse getApplicationById(Long applicationId);
    ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatusUpdateRequest request);
    void deleteApplication(Long applicationId);
    Page<ApplicationResponse> getApplicationsByJobId(Long jobId, Pageable pageable);
    Page<ApplicationResponse> getApplicationsByJobIdAndStatus(Long jobId, ApplicationStatus status, Pageable pageable);
    Page<ApplicationResponse> getApplicationsByUserId(Long userId, Pageable pageable);
    Page<ApplicationResponse> getApplicationsByUserIdAndStatus(Long userId, ApplicationStatus status, Pageable pageable);
    boolean hasUserAppliedForJob(Long jobId, Long userId);
}