package ru.sobse.money_transfer_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;
import ru.sobse.money_transfer_service.exeption.*;
import ru.sobse.money_transfer_service.logger.Logger;
import ru.sobse.money_transfer_service.model.Amount;
import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.TransferOperation;
import ru.sobse.money_transfer_service.repository.MoneyTransferRepository;
import ru.sobse.money_transfer_service.service.MoneyTransferService;
import ru.sobse.money_transfer_service.service.MoneyTransferServiceImpl;
import ru.sobse.money_transfer_service.service.VerificationCodeGenerator;

public class TestMoneyTransferService {
    public BankCard card;
    public TransferOperation operation;
    public Logger logger;
    private MoneyTransferRepository repository;
    private VerificationCodeGenerator generator;
    private TransferOperation operation1NullId;

    @BeforeEach
    public void beforeEach() {
        logger = Mockito.mock(Logger.class);
        repository = Mockito.mock(MoneyTransferRepository.class);
        generator = Mockito.mock(VerificationCodeGenerator.class);

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

        operation1NullId = new TransferOperation(operation.getCardFrom(),
                operation.getCardToNumber(),
                operation.getAmount(),
                (float) 10,
                "0000");
    }

    @Test
    public void testTransfer() {
        //arrange
        operation.setOperationId("1");
        TransferOperation operation1NullId = new TransferOperation(operation.getCardFrom(),
                operation.getCardToNumber(),
                operation.getAmount(),
                (float) 10,
                "0000");

        Mockito.when(repository.getBankCardByNumber("4000001234567899")).thenReturn(card);
        Mockito.when(repository.createOperation(operation1NullId)).thenReturn(operation);

        TransferOperationDTO transferOperationDTO = new TransferOperationDTO("4000001234567899",
                "10/25",
                "123",
                "123456789123456",
                new Amount(1000, "RUB"));

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        OperationDTO operationDTOExpect = new OperationDTO("1");
        //act
        OperationDTO operationDTOActual = service.transfer(transferOperationDTO);
        //assert
        Assertions.assertEquals(operationDTOExpect, operationDTOActual);
    }

    @Test
    public void testTransferCardNotFound() {
        //arrange
        operation.setOperationId("1");
        TransferOperation operation1NullId = new TransferOperation(operation.getCardFrom(),
                operation.getCardToNumber(),
                operation.getAmount(),
                (float) 10,
                "0000");

        Mockito.when(repository.getBankCardByNumber("4000001234567899")).thenReturn(null);
        Mockito.when(repository.createOperation(operation1NullId)).thenReturn(operation);

        TransferOperationDTO transferOperationDTO = new TransferOperationDTO("4000001234567899",
                "10/25",
                "123",
                "123456789123456",
                new Amount(1000, "RUB"));

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        //act
        Executable action = () -> service.transfer(transferOperationDTO);
        //assert
        Assertions.assertThrowsExactly(CardNotFound.class, action);
    }

    @Test
    public void testTransferCardNotValid() {
        //arrange
        operation.setOperationId("1");
        TransferOperation operation1NullId = new TransferOperation(operation.getCardFrom(),
                operation.getCardToNumber(),
                operation.getAmount(),
                (float) 10,
                "0000");

        Mockito.when(repository.getBankCardByNumber("4000001234567899")).thenReturn(card);
        Mockito.when(repository.createOperation(operation1NullId)).thenReturn(operation);

        TransferOperationDTO transferOperationDTO = new TransferOperationDTO("4000001234567899",
                "11/25",
                "123",
                "123456789123456",
                new Amount(1000, "RUB"));

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        //act
        Executable action = () -> service.transfer(transferOperationDTO);
        //assert
        Assertions.assertThrowsExactly(CardNotValid.class, action);
    }

    @Test
    public void testTransferInsufficientFunds() {
        //arrange
        operation.setOperationId("1");

        Mockito.when(repository.getBankCardByNumber("4000001234567899")).thenReturn(card);
        Mockito.when(repository.createOperation(operation1NullId)).thenReturn(operation);

        TransferOperationDTO transferOperationDTO = new TransferOperationDTO("4000001234567899",
                "10/25",
                "123",
                "123456789123456",
                new Amount(1000000, "RUB"));

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        //act
        Executable action = () -> service.transfer(transferOperationDTO);
        //assert
        Assertions.assertThrowsExactly(InsufficientFunds.class, action);
    }

    @Test
    public void testConfirmOperation() {
        //arrange
        operation.setOperationId("1");
        Mockito.when(repository.getOperationById("1")).thenReturn(operation);

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        OperationDTO expect = new OperationDTO("1");
        //act
        OperationDTO actual = service.confirmOperation(new ConfirmingOperationDTO("1", "0000"));
        //assert
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void testOperationNotFound() {
        //arrange
        TransferOperation operationNotFound = null;
        Mockito.when(repository.getOperationById("1")).thenReturn(operationNotFound);

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        //act
        Executable executable = () -> service.confirmOperation(new ConfirmingOperationDTO("1", "0000"));
        //assert
        Assertions.assertThrowsExactly(OperationNotFound.class, executable);
    }

    @Test
    public void testInvalidCode() {
        //arrange
        operation.setOperationId("1");
        operation.setVerificationCode("0001");
        Mockito.when(repository.getOperationById("1")).thenReturn(operation);

        MoneyTransferService service = new MoneyTransferServiceImpl(generator, repository, logger);

        //act
        Executable executable = () -> service.confirmOperation(new ConfirmingOperationDTO("1", "0000"));
        //assert
        Assertions.assertThrowsExactly(InvalidCode.class, executable);
    }

}
