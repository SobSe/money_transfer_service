package ru.sobse.money_transfer_service.exeption;

public class CardNotValid extends RuntimeException{
    public CardNotValid(String message) {
        super(message);
    }
}
