package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}
