package se.jensen.yuki.onlinestorebackend.user_order.user.domain;

import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Email;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.UserId;

import java.util.List;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository {
    /**
     * Finds a User by their unique identifier.
     *
     * @param id the unique identifier of the User
     * @return the User with the specified id, or throw exception if no such User exists
     */
    User findById(UserId id);

    /**
     * Saves a User to the repository. If the User already exists, it will be updated; otherwise, a new User will be created.
     *
     * @param user the User to save
     */
    void save(User user);

    /**
     * Checks if a User exists in the repository by their email address.
     *
     * @param email the email address to check for existence
     * @return true if a User with the specified email exists, false otherwise
     */
    boolean existsByEmail(Email email);

    /**
     * Deletes a User from the repository by their unique identifier.
     *
     * @param id the unique identifier of the User to delete
     */
    void deleteById(UserId id);

    /**
     * Retrieves all Users from the repository.
     *
     * @return a list of all Users
     */
    List<User> findAll();
}
