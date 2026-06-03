package se.jensen.yuki.userorderservice.user.domain.vo;

import lombok.Getter;

@Getter
public final class LastName {
    private final String value;
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    private LastName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("LastName cannot be null or blank");
        }

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("LastName must be between " +
                    MIN_LENGTH + " and " + MIN_LENGTH + " characters"
            );
        }

        this.value = value;
    }

    public static LastName of(String value) {
        return new LastName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LastName lastName)) return false;

        return value.equals(lastName.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return String.valueOf(value); }
}
