package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByProfileId(Long profileId);
}
