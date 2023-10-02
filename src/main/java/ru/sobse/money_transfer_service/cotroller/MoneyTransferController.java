package ru.sobse.money_transfer_service.cotroller;

import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;

public interface MoneyTransferController {
    OperationDTO transfer(TransferOperationDTO transfer);

    OperationDTO confirmOperation(ConfirmingOperationDTO confirmingOperation);

    String ping();
}
