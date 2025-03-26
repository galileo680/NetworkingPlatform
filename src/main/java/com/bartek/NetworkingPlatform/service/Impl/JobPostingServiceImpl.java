package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.jobposting.JobPostingCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.jobposting.JobPostingUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.jobposting.JobPostingResponse;
import com.bartek.NetworkingPlatform.dto.response.jobposting.JobSearchRequest;
import com.bartek.NetworkingPlatform.entity.Company;
import com.bartek.NetworkingPlatform.entity.JobPosting;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.exception.UnauthorizedException;
import com.bartek.NetworkingPlatform.mapper.JobPostingMapper;
import com.bartek.NetworkingPlatform.repository.CompanyRepository;
import com.bartek.NetworkingPlatform.repository.JobPostingRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import com.bartek.NetworkingPlatform.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    private final JobPostingMapper jobPostingMapper;

    @Override
    @Transactional
    public JobPostingResponse createJobPosting(JobPostingCreateRequest request, Long userId) {
        User user = findUserOrThrow(userId);
        Company company = findCompanyOrThrow(request.getCompanyId());

        JobPosting jobPosting = JobPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .company(company)
                .postedBy(user)
                .build();

        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);
        return jobPostingMapper.mapToJobPostingResponse(savedJobPosting);
    }

    @Override
    public JobPostingResponse getJobPostingById(Long jobId) {
        JobPosting jobPosting = findJobPostingOrThrow(jobId);
        return jobPostingMapper.mapToJobPostingResponse(jobPosting);
    }

    @Override
    @Transactional
    public JobPostingResponse updateJobPosting(Long jobId, JobPostingUpdateRequest request, Long userId) {
        JobPosting jobPosting = findJobPostingOrThrow(jobId);

        if (!jobPosting.getPostedBy().getId().equals(userId) && !isUserAdmin(userId)) {
            throw new UnauthorizedException("You do not have permission to update this job offer");
        }

        jobPosting.setTitle(request.getTitle());
        jobPosting.setDescription(request.getDescription());
        jobPosting.setLocation(request.getLocation());

        JobPosting updatedJobPosting = jobPostingRepository.save(jobPosting);
        return jobPostingMapper.mapToJobPostingResponse(updatedJobPosting);
    }

    @Override
    @Transactional
    public void deleteJobPosting(Long jobId, Long userId) {
        JobPosting jobPosting = findJobPostingOrThrow(jobId);

        if (!jobPosting.getPostedBy().getId().equals(userId) && !isUserAdmin(userId)) {
            throw new UnauthorizedException("You do not have permission to delete this job offer");
        }

        jobPostingRepository.delete(jobPosting);
    }

    @Override
    public Page<JobPostingResponse> getAllJobPostings(Pageable pageable) {
        Page<JobPosting> jobPostings = jobPostingRepository.findAll(pageable);
        return jobPostings.map(jobPostingMapper::mapToJobPostingResponse);
    }

    @Override
    public Page<JobPostingResponse> getJobPostingsByCompany(Long companyId, Pageable pageable) {
        findCompanyOrThrow(companyId);

        Page<JobPosting> jobPostings = jobPostingRepository.findByCompanyId(companyId, pageable);
        return jobPostings.map(jobPostingMapper::mapToJobPostingResponse);
    }

    @Override
    public Page<JobPostingResponse> getJobPostingsByUser(Long userId, Pageable pageable) {
        User user = findUserOrThrow(userId);

        Page<JobPosting> jobPostings = jobPostingRepository.findByPostedBy(user, pageable);
        return jobPostings.map(jobPostingMapper::mapToJobPostingResponse);
    }

    @Override
    public Page<JobPostingResponse> searchJobPostings(JobSearchRequest searchRequest, Pageable pageable) {
        Page<JobPosting> jobPostings = jobPostingRepository.findJobsByFilters(
                searchRequest.getTitle(),
                searchRequest.getLocation(),
                searchRequest.getCompanyId(),
                pageable);

        return jobPostings.map(jobPostingMapper::mapToJobPostingResponse);
    }

    //Helper methods
    private JobPosting findJobPostingOrThrow(Long jobId) {
        return jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("No job offer found with ID: " + jobId));
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with ID: " + userId));
    }

    private Company findCompanyOrThrow(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("No company found with ID: " + companyId));
    }

    private boolean isUserAdmin(Long userId) {
        User user = findUserOrThrow(userId);
        return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }
}