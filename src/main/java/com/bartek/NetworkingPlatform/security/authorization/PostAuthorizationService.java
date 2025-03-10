package com.bartek.NetworkingPlatform.security.authorization;

import com.bartek.NetworkingPlatform.entity.Post;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.repository.PostRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostAuthorizationService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public boolean canModifyPost(Authentication authentication, Long postId) {
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

        Post post = postRepository.findById(postId).orElse(null);
        return post != null && post.getUser().getId().equals(user.getId());
    }
}