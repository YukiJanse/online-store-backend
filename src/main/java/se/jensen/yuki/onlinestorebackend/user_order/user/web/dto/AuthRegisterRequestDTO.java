package se.jensen.yuki.onlinestorebackend.user_order.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRegisterRequestDTO(
        @NotBlank(message = "username can't be empty")
        @Size(min = 3, max = 50, message = "username must be between 3 - 50 letters")
        @Pattern(regexp = "^[A-Za-z0-9 ]*$", message = "Only letters, numbers and space allowed")
        String username,

        @NotBlank(message = "email can't be empty")
        @Size(min = 3, max = 60, message = "email must be between 3 - 60 letters")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "Invalid email format")
        String email,

        @NotBlank(message = "password can't be empty")
        @Size(min = 8, max = 40, message = "password must be between 8 - 40 letters")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must be at least 8 characters, include one uppercase letter, one lowercase letter and one number")
        String password,

        @NotBlank(message = "phoneNumber can't be empty")
        @Size(max = 12, message = "phoneNumber must be 12 letters")
        String phoneNumber,

        String street,

        String postalCode,

        String city,

        String country
        ) {
}
