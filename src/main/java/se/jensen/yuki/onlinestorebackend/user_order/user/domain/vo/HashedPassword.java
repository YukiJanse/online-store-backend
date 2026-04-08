package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo;

import java.util.regex.Pattern;

public final class HashedPassword {
    private final String value;
    // BCrypt hashes are always 60 characters long
    private final static int LENGTH = 60;
    // Regex pattern to allow valid BCrypt hash formats (e.g., $2a$, $2b$, $2y$ followed by cost and salt+hash)
    private final static Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the hashed password string
     * @throws IllegalArgumentException if the password is null, blank, does not have the correct length, or does not match the BCrypt pattern
     */
    private HashedPassword(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        if (value.length() != LENGTH) {
            throw new IllegalArgumentException("Invalid BCrypt hash length");
        }

        if (!BCRYPT_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid password format");
        }

        this.value = value;
    }

    /**
     * Factory method to create a new HashedPassword instance.
     *
     * @param value the hashed password string
     * @return a new HashedPassword instance
     */
    public static HashedPassword of(String value) {
        return new HashedPassword(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashedPassword password)) return false;

        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
