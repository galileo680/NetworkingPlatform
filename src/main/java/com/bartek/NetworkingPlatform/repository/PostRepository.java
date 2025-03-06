package com.bartek.NetworkingPlatform.repository;

import com.bartek.NetworkingPlatform.entity.Post;
import com.bartek.NetworkingPlatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    // Demo version of user feed
    @Query("SELECT p FROM Post p WHERE p.user.id IN " +
            "(SELECT c.receiver.id FROM Connection c WHERE c.sender.id = :userId AND c.status = 'ACCEPTED' " +
            "UNION " +
            "SELECT c.sender.id FROM Connection c WHERE c.receiver.id = :userId AND c.status = 'ACCEPTED' " +
            "UNION " +
            "SELECT :userId) " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findPostsForUserFeed(@Param("userId") Long userId, Pageable pageable);

    Page<Post> findByContentContainingIgnoreCaseOrderByCreatedAtDesc(String content, Pageable pageable);

    boolean existsById(Long id);
}
