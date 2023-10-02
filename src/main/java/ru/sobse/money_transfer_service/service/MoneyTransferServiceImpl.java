package ru.sobse.money_transfer_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;
import ru.sobse.money_transfer_service.exeption.*;
import ru.sobse.money_transfer_service.logger.Logger;
import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.StatusOfOperations;
import ru.sobse.money_transfer_service.model.TransferOperation;
import ru.sobse.money_transfer_service.repository.MoneyTransferRepository;

import java.util.Date;

@Service
public class MoneyTransferServiceImpl implements MoneyTransferService{
    @Value("${service.commission}")
    private int commission;
    private final VerificationCodeGenerator codeGenerator;
    private final MoneyTransferRepository repository;
    private final Logger logger;

    public MoneyTransferServiceImpl(VerificationCodeGenerator codeGenerator, MoneyTransferRepository repository, Logger logger) {
        this.codeGenerator = codeGenerator;
        this.repository = repository;
        this.logger = logger;
    }

    @Override
    public OperationDTO transfer(TransferOperationDTO transferOperationDTO) {
        BankCard bankCard = repository.getBankCardByNumber(transferOperationDTO.getCardFromNumber());
        if (bankCard == null) {
            throw new CardNotFound("Card not found");
        }
        if (!transferOperationDTO.getCardFromCVV().equals(bankCard.getCvv())) {
            throw new InvalidCVV("Invalid CVV");
        }
        if (!transferOperationDTO.getCardFromValidTill().equals(bankCard.getValidTill())) {
            throw new CardNotValid("Card not valid");
        }
        if (transferOperationDTO.getAmount().getValue() > bankCard.getBalance()) {
            throw new InsufficientFunds("Not enough money");
        }

        TransferOperation transferOperation = new TransferOperation(bankCard,
                transferOperationDTO.getCardToNumber(),
                transferOperationDTO.getAmount(),
                (float) (transferOperationDTO.getAmount().getValue() * commission) / 100,
                "0000"
        );
        return new OperationDTO(repository.createOperation(transferOperation).getOperationId());
    }

    @Override
    public OperationDTO confirmOperation(ConfirmingOperationDTO confirmingOperationDTO) {
        TransferOperation transferOperation = repository.getOperationById(confirmingOperationDTO.getOperationId());
        if (transferOperation == null) {
            throw new OperationNotFound(String.format("Operation %s not found", confirmingOperationDTO.getOperationId()));
        }
        if (!confirmingOperationDTO.getCode().equals(transferOperation.getVerificationCode())) {
            updateOperation(transferOperation, StatusOfOperations.CANCELLED);
            throw new InvalidCode("Invalid verification code");
        }
        updateOperation(transferOperation, StatusOfOperations.COMPLETED);

        BankCard card = transferOperation.getCardFrom();
        card.setBalance(card.getBalance() - transferOperation.getAmount().getValue());
        repository.updateBankCard(card);

        return new OperationDTO(transferOperation.getOperationId());
    }

    private void updateOperation(TransferOperation transferOperation, StatusOfOperations status) {
        transferOperation.setStatus(status);
        repository.updateOperation(transferOperation);
        logger.log(transferOperation, new Date());
    }
}
