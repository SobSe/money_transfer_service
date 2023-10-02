package ru.sobse.money_transfer_service.exeption;

public class InvalidCVV extends RuntimeException{
    public InvalidCVV(String message) {
        super(message);
    }
}
