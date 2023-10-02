package ru.sobse.money_transfer_service.exeption;

public class CardNotFound extends RuntimeException{
    public CardNotFound(String message) {
        super(message);
    }
}
