package se.jensen.yuki.userorderservice.shared.exception;

public class InventoryNotEnoughException extends RuntimeException {
    public InventoryNotEnoughException(String message) {
        super(message);
    }
}
