package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.JobPosting;
import com.bartek.NetworkingPlatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByCompanyId(Long companyId);
    List<JobPosting> findByPostedBy(User user);
}
