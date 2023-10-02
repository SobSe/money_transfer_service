package ru.sobse.money_transfer_service.repository;

import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.TransferOperation;

import java.util.Map;

public interface MoneyTransferRepository {
    TransferOperation createOperation(TransferOperation operation);

    boolean updateOperation(TransferOperation operation);

    TransferOperation getOperationById(String id);

    BankCard getBankCardByNumber(String number);

    boolean updateBankCard(BankCard card);

    Map<String, BankCard> getBankCards();

    Map<String, TransferOperation> getTransferOperations();
}
