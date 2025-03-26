package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Company;
import com.bartek.NetworkingPlatform.entity.JobPosting;
import com.bartek.NetworkingPlatform.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    Page<JobPosting> findByCompanyId(Long companyId, Pageable pageable);

    Page<JobPosting> findByPostedBy(User postedBy, Pageable pageable);

    Page<JobPosting> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<JobPosting> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    Page<JobPosting> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String title, String location, Pageable pageable);

    Page<JobPosting> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompany(
            String title, String location, Company company, Pageable pageable);

    @Query("SELECT j FROM JobPosting j WHERE " +
            "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:companyId IS NULL OR j.company.id = :companyId)")
    Page<JobPosting> findJobsByFilters(
            @Param("title") String title,
            @Param("location") String location,
            @Param("companyId") Long companyId,
            Pageable pageable);
}
