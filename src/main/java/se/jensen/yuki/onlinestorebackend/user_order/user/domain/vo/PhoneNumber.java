package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo;

import java.util.regex.Pattern;

public class PhoneNumber {
    private final String value;
    // Simple regex pattern for basic phone number validation
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(?:\\+46|0)7[0-9]{8}$");
    // Maximum length for an phone number
    private static final int MAX_LENGTH = 12;

    private PhoneNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Phone number cannot be longer than " + MAX_LENGTH + " characters");
        }

        if (!PHONE_NUMBER_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid phone number format: " + value);
        }

        this.value = value;
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber PhoneNumber)) return false;

        return value.equals(PhoneNumber.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return String.valueOf(value); }
}
