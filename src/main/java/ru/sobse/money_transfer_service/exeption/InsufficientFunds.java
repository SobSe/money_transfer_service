package ru.sobse.money_transfer_service.exeption;

public class InsufficientFunds extends RuntimeException{
    public InsufficientFunds(String message) {
        super(message);
    }
}
