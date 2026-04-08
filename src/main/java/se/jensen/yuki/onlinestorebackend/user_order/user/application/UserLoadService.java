package se.jensen.yuki.onlinestorebackend.user_order.user.application;

import se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure.UserJpaEntity;


public interface UserLoadService {
    /**
     * Loads a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the UserJpaEntity corresponding to the given id, or throw an exception if not found
     */
    UserJpaEntity requireJpaById(Long id);

    /**
     * Loads a user by their email address.
     *
     * @param email the email address of the user
     * @return the UserJpaEntity corresponding to the given email, or throw an exception if not found
     */
    UserJpaEntity requireJpaByEmail(String email);
}
