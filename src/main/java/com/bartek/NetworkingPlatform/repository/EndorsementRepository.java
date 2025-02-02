package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
}
