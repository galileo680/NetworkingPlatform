package com.bartek.NetworkingPlatform.security.authorization;

import com.bartek.NetworkingPlatform.entity.Application;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.repository.ApplicationRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAuthorizationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public boolean canAccessApplication(Authentication authentication, Long applicationId) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return false;
        }

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return true;
        }

        Application application = applicationRepository.findById(applicationId).orElse(null);
        if (application == null) {
            return false;
        }

        return application.getUser().getId().equals(user.getId()) ||
                application.getJob().getPostedBy().getId().equals(user.getId());
    }

    public boolean canUpdateApplicationStatus(Authentication authentication, Long applicationId) {
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

        Application application = applicationRepository.findById(applicationId).orElse(null);
        if (application == null) {
            return false;
        }

        return application.getJob().getPostedBy().getId().equals(user.getId());
    }

    public boolean canDeleteApplication(Authentication authentication, Long applicationId) {
        return canAccessApplication(authentication, applicationId);
    }
}