package se.jensen.yuki.onlinestorebackend.user_order.user.domain;

import lombok.Getter;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.*;

import java.util.Objects;

@Getter
public class User {
    private final UserId id;
    private final Username username;
    private Email email;
    private PhoneNumber phoneNumber;
    private HashedPassword password;
    private Address address;

    private User(UserId id, Username username, Email email, PhoneNumber phoneNumber, HashedPassword password, Address address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }

    /**
     * Factory method to create a new user with the specified properties. The ID is set to null for new users.
     * ID will be created by Database
     * 
     * @param username    the username of the user, must not be null
     * @param email       the email address of the user, must not be null
     * @param phoneNumber the phone number of the user, must not be null
     * @param password    the hashed password of the user, must not be null
     * @param address     the address of the user, must not be null
     * @return a User instance with the specified properties and a null ID
     */
    public static User create(Username username, Email email, PhoneNumber phoneNumber, HashedPassword password, Address address) {
        return new User(null, username, email, phoneNumber, password, address);
    }

    /**
     * Factory method to reconstruct an existing user with the specified properties, including the ID
     * 
     * @param id          the unique identifier of the user, must not be null
     * @param username    the username of the user, must not be null
     * @param email       the email address of the user, must not be null
     * @param phoneNumber the phone number of the user, must not be null
     * @param password    the hashed password of the user, must not be null
     * @param address     the address of the user, must not be null
     * @return a User instance with the specified properties and ID
     */
    public static User reconstruct(UserId id, Username username, Email email, PhoneNumber phoneNumber, HashedPassword password, Address address) {
        return new User(id, username, email, phoneNumber, password, address);
    }

    /**
     * Updates the user's email address. The new email must not be null and must be different from the current email.
     *
     * @param newEmail the new email address to set, must not be null and must be different from the current email
     * @throws IllegalArgumentException if the new email is null or the same as the current email
     */
    public void changeEmail(Email newEmail) {
        Objects.requireNonNull(newEmail, "New email cannot be null");
        
        if (this.email.equals(newEmail)) {
            throw new IllegalArgumentException("New email must be different from the current email");
        }
        
        this.email = newEmail;
    }

    /**
     * Updates the user's password. The new password must not be null and must be different from the current password.
     *
     * @param newHashedPassword the new hashed password to set, must not be null and must be different from the current password
     * @throws IllegalArgumentException if the new password is null or the same as the current password
     */
    public void changePassword(HashedPassword newHashedPassword) {
        Objects.requireNonNull(newHashedPassword, "New password cannot be null");

        if (this.password.equals(newHashedPassword)) {
            throw new IllegalArgumentException("New password must be different from the current password.");
        }

        this.password = newHashedPassword;
    }

    /**
     * Updates the user's address. The new address must not be null and must be different from the current address.
     *
     * @param newAddress the new address to set, must not be null and must be different from the current address
     * @throws IllegalArgumentException if the new address is null or the same as the current address
     */
    public void changeAddress(Address newAddress) {
        Objects.requireNonNull(newAddress, "New address cannot be null");

        if (this.address.equals(newAddress)) {
            throw new IllegalArgumentException("New address must be different from the current address.");
        }

        this.address = newAddress;
    }

    /**
     * Updates the user's phone number. The new phone number must not be null and must be different from the current phone number.
     *
     * @param newPhoneNumber the new phone number to set, must not be null and must be different from the current phone number
     * @throws IllegalArgumentException if the new phone number is null or the same as the current phone number
     */
    public void changePhoneNumber(PhoneNumber newPhoneNumber) {
        Objects.requireNonNull(newPhoneNumber, "New phone number cannot be null");

        if (this.phoneNumber.equals(newPhoneNumber)) {
            throw new IllegalArgumentException("New phone number must be different from the current phone number.");
        }

        this.phoneNumber = newPhoneNumber;
    }
}
