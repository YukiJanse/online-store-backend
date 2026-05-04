package se.jensen.yuki.onlinestorebackend.shared.exception;

public class CannotModifyOrderException extends RuntimeException {
    public CannotModifyOrderException(String message) {
        super(message);
    }
}
