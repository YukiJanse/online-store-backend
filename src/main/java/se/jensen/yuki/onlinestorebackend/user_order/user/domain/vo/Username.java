package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo;

public final class Username {
    private final String value;
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    private Username(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Username must be between " +
                    MIN_LENGTH + " and " + MIN_LENGTH + " characters"
            );
        }

        this.value = value;
    }

    public static Username of(String value) {
        return new Username(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Username username)) return false;

        return value.equals(username.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return String.valueOf(value); }
}
