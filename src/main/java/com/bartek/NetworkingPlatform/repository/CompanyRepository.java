package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByName(String name);
    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Company> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    Page<Company> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String name, String location, Pageable pageable);
}
