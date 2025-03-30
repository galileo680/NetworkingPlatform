package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.application.ApplicationCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.application.ApplicationStatusUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.application.ApplicationResponse;
import com.bartek.NetworkingPlatform.entity.Application;
import com.bartek.NetworkingPlatform.entity.JobPosting;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.exception.BadRequestException;
import com.bartek.NetworkingPlatform.exception.UnauthorizedException;
import com.bartek.NetworkingPlatform.mapper.ApplicationMapper;
import com.bartek.NetworkingPlatform.mapper.CompanyMapper;
import com.bartek.NetworkingPlatform.mapper.JobPostingMapper;
import com.bartek.NetworkingPlatform.mapper.UserMapper;
import com.bartek.NetworkingPlatform.repository.ApplicationRepository;
import com.bartek.NetworkingPlatform.repository.JobPostingRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import com.bartek.NetworkingPlatform.service.ApplicationService;
import com.bartek.NetworkingPlatform.enums.ApplicationStatus;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    private final ApplicationMapper applicationMapper;

    private final UserUtil userUtil;

    @Override
    @Transactional
    public ApplicationResponse createApplication(ApplicationCreateRequest request) {

        User user = userUtil.getCurrentUser();
        JobPosting jobPosting = findJobPostingOrThrow(request.getJobId());

        if (applicationRepository.existsByJobIdAndUserId(request.getJobId(), user.getId())) {
            throw new BadRequestException("You have already applied for this job offer");
        }

        Application application = Application.builder()
                .job(jobPosting)
                .user(user)
                .status(ApplicationStatus.PENDING)
                .build();

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.mapToApplicationResponse(savedApplication);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long applicationId) {
        Application application = findApplicationOrThrow(applicationId);
        return applicationMapper.mapToApplicationResponse(application);
    }

    @Override
    @Transactional
    public ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatusUpdateRequest request) {
        User user = userUtil.getCurrentUser();
        Application application = findApplicationOrThrow(applicationId);

        if (!application.getJob().getPostedBy().getId().equals(user.getId()) && !isUserAdmin(user.getId())) {
            throw new UnauthorizedException("You do not have permission to update the status of this application");
        }

        application.setStatus(request.getStatus());
        Application updatedApplication = applicationRepository.save(application);

        return applicationMapper.mapToApplicationResponse(updatedApplication);
    }

    @Override
    @Transactional
    public void deleteApplication(Long applicationId) {
        Long userId = userUtil.getCurrentUser().getId();
        Application application = findApplicationOrThrow(applicationId);

        boolean isOwner = application.getUser().getId().equals(userId);
        boolean isRecruiter = application.getJob().getPostedBy().getId().equals(userId);
        boolean isAdmin = isUserAdmin(userId);

        if (!isOwner && !isRecruiter && !isAdmin) {
            throw new UnauthorizedException("You do not have permission to remove this application");
        }

        applicationRepository.delete(application);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponse> getApplicationsByJobId(Long jobId, Pageable pageable) {
        findJobPostingOrThrow(jobId);

        Page<Application> applications = applicationRepository.findByJobId(jobId, pageable);
        return applications.map(applicationMapper::mapToApplicationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponse> getApplicationsByJobIdAndStatus(Long jobId, ApplicationStatus status, Pageable pageable) {
        findJobPostingOrThrow(jobId);

        Page<Application> applications = applicationRepository.findByJobIdAndStatus(jobId, status, pageable);
        return applications.map(applicationMapper::mapToApplicationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponse> getApplicationsByUserId(Pageable pageable) {
        Long userId = userUtil.getCurrentUser().getId();

        Page<Application> applications = applicationRepository.findByUserId(userId, pageable);
        return applications.map(applicationMapper::mapToApplicationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponse> getApplicationsByUserIdAndStatus(ApplicationStatus status, Pageable pageable) {
        Long userId = userUtil.getCurrentUser().getId();

        Page<Application> applications = applicationRepository.findByUserIdAndStatus(userId, status, pageable);
        return applications.map(applicationMapper::mapToApplicationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserAppliedForJob(Long jobId) {
        Long userId = userUtil.getCurrentUser().getId();
        return applicationRepository.existsByJobIdAndUserId(jobId, userId);
    }

    // Helper methods
    private Application findApplicationOrThrow(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("No application found with ID: " + applicationId));
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with ID: " + userId));
    }

    private JobPosting findJobPostingOrThrow(Long jobId) {
        return jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("No job offer found with ID: " + jobId));
    }

    private boolean isUserAdmin(Long userId) {
        User user = findUserOrThrow(userId);
        return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }
}