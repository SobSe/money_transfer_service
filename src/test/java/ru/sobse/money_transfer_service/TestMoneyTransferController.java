package ru.sobse.money_transfer_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;
import ru.sobse.money_transfer_service.cotroller.MoneyTransferController;
import ru.sobse.money_transfer_service.cotroller.MoneyTransferControllerImpl;
import ru.sobse.money_transfer_service.model.Amount;
import ru.sobse.money_transfer_service.service.MoneyTransferService;

public class TestMoneyTransferController {
    public TransferOperationDTO transferOperationDTO;
    public OperationDTO operationDTO;
    public ConfirmingOperationDTO confirmingOperationDTO;

    @BeforeEach
    public void beforeEach() {
        transferOperationDTO = new TransferOperationDTO("4000001234567899",
                "11/25",
                "123",
                "123456789123456",
                new Amount(1000, "RUB"));

        operationDTO = new OperationDTO("1");

        confirmingOperationDTO = new ConfirmingOperationDTO("1", "0000");
    }

    @Test
    public void testTransfer() {
        //arrange
        MoneyTransferService service = Mockito.mock(MoneyTransferService.class);
        Mockito.when(service.transfer(transferOperationDTO)).thenReturn(operationDTO);

        OperationDTO expect = new OperationDTO("1");

        MoneyTransferController controller = new MoneyTransferControllerImpl(service);
        //act
        OperationDTO actual = controller.transfer(transferOperationDTO);
        //assert
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void testConfirmOperation() {
        //arrange
        MoneyTransferService service = Mockito.mock(MoneyTransferService.class);
        Mockito.when(service.confirmOperation(confirmingOperationDTO)).thenReturn(operationDTO);

        OperationDTO expect = new OperationDTO("1");

        MoneyTransferController controller = new MoneyTransferControllerImpl(service);
        //act
        OperationDTO actual = controller.confirmOperation(confirmingOperationDTO);
        //assert
        Assertions.assertEquals(expect, actual);
    }
}