package com.bartek.NetworkingPlatform.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;

    private String bio;

    private String location;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToMany
    @JoinTable(
            name = "profile_skills",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ToString.Exclude
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @ToString.Exclude
    private Set<Education> educations = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @ToString.Exclude
    private Set<Experience> experiences = new HashSet<>();
}
