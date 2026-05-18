package se.jensen.yuki.userorderservice.shared.exception;

public class CannotModifyOrderException extends RuntimeException {
    public CannotModifyOrderException(String message) {
        super(message);
    }
}
