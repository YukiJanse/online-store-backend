package se.jensen.yuki.productservice.shared.exception;

public class InventoryNotEnoughException extends RuntimeException {
    public InventoryNotEnoughException(String message) {
        super(message);
    }
}
