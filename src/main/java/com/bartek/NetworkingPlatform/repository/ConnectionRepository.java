package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Connection;
import com.bartek.NetworkingPlatform.enums.ConnectionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query("SELECT c FROM Connection c WHERE (c.sender.id = :userId OR c.receiver.id = :userId) AND c.status = 'ACCEPTED'")
    Page<Connection> findAcceptedConnectionsByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<Connection> findBySenderIdAndStatus(Long senderId, ConnectionStatus status, Pageable pageable);

    Page<Connection> findByReceiverIdAndStatus(Long receiverId, ConnectionStatus status, Pageable pageable);

    @Query("SELECT c FROM Connection c WHERE (c.sender.id = :user1Id AND c.receiver.id = :user2Id) OR (c.sender.id = :user2Id AND c.receiver.id = :user1Id)")
    Optional<Connection> findConnectionBetweenUsers(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Connection c WHERE ((c.sender.id = :user1Id AND c.receiver.id = :user2Id) OR (c.sender.id = :user2Id AND c.receiver.id = :user1Id)) AND c.status = 'ACCEPTED'")
    boolean areUsersConnected(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Query("SELECT CASE WHEN c.sender.id = :userId THEN c.receiver.id ELSE c.sender.id END FROM Connection c WHERE (c.sender.id = :userId OR c.receiver.id = :userId) AND c.status = 'ACCEPTED'")
    List<Long> findConnectedUserIds(@Param("userId") Long userId);
}
