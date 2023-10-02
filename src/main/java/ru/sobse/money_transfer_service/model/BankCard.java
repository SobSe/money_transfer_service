package ru.sobse.money_transfer_service.model;

import java.util.Objects;

public class BankCard {
    private String number;
    private String validTill;
    private String cvv;
    private int balance;
    private String currency;

    public BankCard(String number, String validTill, String cvv, int balance, String currency) {
        this.number = number;
        this.validTill = validTill;
        this.cvv = cvv;
        this.balance = balance;
        this.currency = currency;
    }

    public String getNumber() {
        return number;
    }

    public String getValidTill() {
        return validTill;
    }

    public int getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCvv() {
        return cvv;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(number, bankCard.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
