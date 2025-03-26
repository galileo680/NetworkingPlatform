package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.jobposting.JobPostingCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.jobposting.JobPostingUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.jobposting.JobPostingResponse;
import com.bartek.NetworkingPlatform.dto.response.jobposting.JobSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostingService {
    JobPostingResponse createJobPosting(JobPostingCreateRequest request);
    JobPostingResponse getJobPostingById(Long jobId);
    JobPostingResponse updateJobPosting(Long jobId, JobPostingUpdateRequest request);
    void deleteJobPosting(Long jobId);
    Page<JobPostingResponse> getAllJobPostings(Pageable pageable);
    Page<JobPostingResponse> getJobPostingsByCompany(Long companyId, Pageable pageable);
    Page<JobPostingResponse> getJobPostingsByUser(Pageable pageable);
    Page<JobPostingResponse> searchJobPostings(JobSearchRequest searchRequest, Pageable pageable);
}
