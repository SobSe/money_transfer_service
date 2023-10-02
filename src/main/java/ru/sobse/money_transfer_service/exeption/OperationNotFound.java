package ru.sobse.money_transfer_service.exeption;

public class OperationNotFound extends RuntimeException {
    public OperationNotFound(String message) {
        super(message);
    }
}
