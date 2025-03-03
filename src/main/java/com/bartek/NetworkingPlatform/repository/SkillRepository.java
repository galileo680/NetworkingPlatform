package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;


@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
    List<Skill> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}
