package ru.sobse.money_transfer_service.cotroller;

public enum ConfirmationError {
    OPERATION_NOT_FOUND(1),
    INVALID_CODE(2);
    private final int type;

    ConfirmationError(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
