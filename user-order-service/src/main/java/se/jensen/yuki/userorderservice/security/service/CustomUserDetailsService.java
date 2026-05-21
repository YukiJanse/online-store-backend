package se.jensen.yuki.userorderservice.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.jensen.yuki.userorderservice.security.infrastructure.CustomUserDetails;
import se.jensen.yuki.userorderservice.user.application.UserLoadService;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaEntity;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserLoadService userLoadService;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserJpaEntity user = userLoadService.requireJpaByEmail(email);
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
