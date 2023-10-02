package ru.sobse.money_transfer_service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.expression.Operation;
import ru.sobse.money_transfer_service.model.Amount;
import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.TransferOperation;
import ru.sobse.money_transfer_service.repository.Counter;
import ru.sobse.money_transfer_service.repository.MoneyTransferRepository;
import ru.sobse.money_transfer_service.repository.MoneyTransferRepositoryImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestMoneyTransferRepository {
    public BankCard card;
    public TransferOperation operation;
    public MoneyTransferRepository repository;


    @BeforeEach
    public void beforeEach() {
        card = new BankCard("4000001234567899",
                "10/25",
                "123",
                100000,
                "RUB");

        operation = new TransferOperation(card,
                "4000001234567890",
                new Amount(1000, "RUB"),
                (float) 10.00,
                "0000");
        Counter counter = Mockito.mock(Counter.class);
        Mockito.when(counter.incrementAndGet()).thenReturn(1L);
        repository = new MoneyTransferRepositoryImpl(counter);
    }

    @Test
    public void testCreateOperation() {
        //arrange
        Map<String, TransferOperation> expect = new ConcurrentHashMap<>();
        TransferOperation operationExpect = new TransferOperation(card,
                "4000001234567890",
                new Amount(1000, "RUB"),
                (float) 10.00,
                "0000");
        operationExpect.setOperationId("1");
        expect.put(operationExpect.getOperationId(), operation);
        //act
        repository.createOperation(operation);
        //assert
        Assertions.assertEquals(expect, repository.getTransferOperations());
    }

    @Test
    public void testGetOperationById() {
        //arrange
        operation.setOperationId("1");
        repository.getTransferOperations().put(operation.getOperationId(), operation);
        TransferOperation expect = operation;
        //act
        TransferOperation operationActual = repository.getOperationById(operation.getOperationId());
        TransferOperation operationActualNull = repository.getOperationById("2");
        //assert
        Assertions.assertEquals(expect, operationActual);
        Assertions.assertNull(operationActualNull);
    }

    @Test
    public void testUpdateOperation() {
        //arrange
        operation.setOperationId("1");
        repository.getTransferOperations().put(operation.getOperationId(), operation);
        boolean expect = true;
        //act
        boolean actual = repository.updateOperation(operation);
        //assert
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void testGetBankCardByNumber() {
        //arrange
        BankCard expect = card;
        //act
        BankCard actual = repository.getBankCardByNumber(card.getNumber());
        BankCard actualNull = repository.getBankCardByNumber("00000000000000000");
        //assert
        Assertions.assertEquals(expect, actual);
        Assertions.assertNull(actualNull);
    }

    @Test
    public void testUpdateBankCard() {
        //arrange
        boolean expect = true;
        //act
        boolean actual = repository.updateBankCard(card);
        //assert
        Assertions.assertEquals(expect, actual);
    }
}
