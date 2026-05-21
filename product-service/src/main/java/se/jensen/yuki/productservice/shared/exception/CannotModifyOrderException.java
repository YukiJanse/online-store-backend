package se.jensen.yuki.productservice.shared.exception;

public class CannotModifyOrderException extends RuntimeException {
    public CannotModifyOrderException(String message) {
        super(message);
    }
}
