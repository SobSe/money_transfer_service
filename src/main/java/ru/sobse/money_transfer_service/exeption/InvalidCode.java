package ru.sobse.money_transfer_service.exeption;

public class InvalidCode extends RuntimeException{
    public InvalidCode(String message) {
        super(message);
    }
}
