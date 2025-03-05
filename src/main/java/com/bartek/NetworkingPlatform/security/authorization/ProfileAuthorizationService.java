package com.bartek.NetworkingPlatform.security.authorization;

import com.bartek.NetworkingPlatform.entity.Profile;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.repository.ProfileRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileAuthorizationService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public boolean canModifyProfile(Authentication authentication, Long profileId) {
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

        Profile profile = profileRepository.findById(profileId).orElse(null);
        return profile != null && profile.getUser().getId().equals(user.getId());
    }
}
