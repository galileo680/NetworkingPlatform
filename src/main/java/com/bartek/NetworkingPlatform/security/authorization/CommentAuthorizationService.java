package com.bartek.NetworkingPlatform.security.authorization;

import com.bartek.NetworkingPlatform.entity.Comment;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.repository.CommentRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAuthorizationService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final UserUtil userUtil;

    public boolean canModifyComment(Authentication authentication, Long commentId) {
        /*String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);*/
        User user = userUtil.getCurrentUser();

        if (user == null) {
            return false;
        }

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return true;
        }

        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return false;
        }

        boolean isCommentAuthor = comment.getUser().getId().equals(user.getId());
        boolean isPostAuthor = comment.getPost().getUser().getId().equals(user.getId());

        return isCommentAuthor || isPostAuthor;
    }
}