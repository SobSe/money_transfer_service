package ru.sobse.money_transfer_service.model;

import java.util.Objects;

public class TransferOperation {
    private String operationId;
    private String verificationCode;
    private BankCard cardFrom;
    private String cardToNumber;
    private Amount amount;
    private StatusOfOperations status;
    private float commission;

    public TransferOperation(BankCard cardFrom,
                             String cardToNumber,
                             Amount amount,
                             float commission,
                             String verificationCode) {
        this.cardFrom = cardFrom;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
        this.commission = commission;
        this.status = StatusOfOperations.CREATED;
        this.verificationCode = verificationCode;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public BankCard getCardFrom() {
        return cardFrom;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public StatusOfOperations getStatus() {
        return status;
    }

    public float getCommission() {
        return commission;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setCardFrom(BankCard cardFrom) {
        this.cardFrom = cardFrom;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setStatus(StatusOfOperations status) {
        this.status = status;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferOperation that = (TransferOperation) o;
        return Objects.equals(operationId, that.operationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId);
    }
}
