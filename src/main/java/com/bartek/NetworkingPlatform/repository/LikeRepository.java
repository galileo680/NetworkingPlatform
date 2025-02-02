package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
