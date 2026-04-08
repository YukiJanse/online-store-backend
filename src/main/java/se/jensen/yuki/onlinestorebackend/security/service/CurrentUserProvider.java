package se.jensen.yuki.onlinestorebackend.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.jensen.yuki.onlinestorebackend.security.infrastructure.CustomUserDetails;
import se.jensen.yuki.onlinestorebackend.shared.exception.UnauthorizedException;

@Component
public class CurrentUserProvider {
    public Long currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() != null && auth.getPrincipal() instanceof CustomUserDetails user)) {
            throw new UnauthorizedException("Not Logged in");
        }

        return user.getId();
    }
}
