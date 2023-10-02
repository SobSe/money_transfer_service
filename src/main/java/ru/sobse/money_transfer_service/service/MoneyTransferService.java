package ru.sobse.money_transfer_service.service;

import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;
import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.TransferOperation;

public interface MoneyTransferService {
    OperationDTO transfer(TransferOperationDTO transferOperationDTO);
    OperationDTO confirmOperation(ConfirmingOperationDTO confirmingOperationDTO);
}
