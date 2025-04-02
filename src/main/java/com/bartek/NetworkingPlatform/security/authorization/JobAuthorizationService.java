package com.bartek.NetworkingPlatform.security.authorization;

import com.bartek.NetworkingPlatform.entity.JobPosting;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.repository.JobPostingRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobAuthorizationService {

    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    public boolean canViewJobApplications(Authentication authentication, Long jobId) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return false;
        }

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return true;
        }

        JobPosting jobPosting = jobPostingRepository.findById(jobId).orElse(null);
        if (jobPosting == null) {
            return false;
        }

        return jobPosting.getPostedBy().getId().equals(user.getId());
    }
}