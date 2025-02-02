package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
