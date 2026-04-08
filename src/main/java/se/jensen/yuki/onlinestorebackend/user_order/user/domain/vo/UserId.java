package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo;

public final class UserId{
    private final Long value;

    private UserId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        this.value = value;
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId id)) return false;

        return value.equals(id.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return String.valueOf(value); }
}
