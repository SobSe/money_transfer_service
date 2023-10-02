package ru.sobse.money_transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.TransferOperation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MoneyTransferRepositoryImpl implements MoneyTransferRepository{
    private final Map<String, BankCard> bankCards;
    private final Map<String, TransferOperation> transferOperations;
    private final Counter counter;

    public MoneyTransferRepositoryImpl(Counter counter) {
        bankCards = new ConcurrentHashMap<>();
        addBankCards();
        transferOperations = new ConcurrentHashMap<>();
        this.counter = counter;
    }

    @Override
    public TransferOperation createOperation(TransferOperation operation) {
        operation.setOperationId(String.valueOf(counter.incrementAndGet()));
        transferOperations.put(operation.getOperationId(), operation);
        return operation;
    }

    @Override
    public TransferOperation getOperationById(String id) {
        return transferOperations.getOrDefault(id, null);
    }

    @Override
    public boolean updateOperation(TransferOperation operation) {
        transferOperations.put(operation.getOperationId(), operation);
        return true;
    }

    @Override
    public BankCard getBankCardByNumber(String number) {
        return bankCards.getOrDefault(number, null);
    }

    @Override
    public boolean updateBankCard(BankCard card) {
        bankCards.put(card.getNumber(), card);
        return true;
    }

    @Override
    public Map<String, BankCard> getBankCards() {
        return bankCards;
    }

    @Override
    public Map<String, TransferOperation> getTransferOperations() {
        return transferOperations;
    }

    private void addBankCards() {
        bankCards.put("4000001234567899",
                new BankCard("4000001234567899",
                        "10/25",
                        "123",
                        100000,
                        "RUB"));
        bankCards.put("5110000134567579",
                new BankCard("5110000134567579",
                        "10/25",
                        "321",
                        100000,
                        "RUB"));
    }
}
