package ru.sobse.money_transfer_service.cotroller;

public enum TransferErrors {
    CARD_NOT_FOUND(1),
    CARD_NOT_VALID(2),
    INVALID_CVV(3),
    INSUFFICIENT_FUNDS(4);
    private int type;

    TransferErrors(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
