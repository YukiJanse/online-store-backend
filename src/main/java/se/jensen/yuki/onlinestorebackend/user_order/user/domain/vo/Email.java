package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo;

import java.util.regex.Pattern;

public final class Email {
    private final String value;
    // Simple regex pattern for basic email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    // Maximum length for an email address
    private static final int MAX_LENGTH = 255;

    private Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Email cannot be longer than " + MAX_LENGTH + " characters");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }

        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;

        return value.equals(email.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return String.valueOf(value); }
}
