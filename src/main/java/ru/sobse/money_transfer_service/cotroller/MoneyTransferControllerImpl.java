package ru.sobse.money_transfer_service.cotroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;
import ru.sobse.money_transfer_service.exeption.*;
import ru.sobse.money_transfer_service.service.MoneyTransferService;

@CrossOrigin(origins = "https://serp-ya.github.io", maxAge = 3600)
@RestController
public class MoneyTransferControllerImpl implements MoneyTransferController{
    private final MoneyTransferService service;

    public MoneyTransferControllerImpl(MoneyTransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    @Override
    public OperationDTO transfer(@RequestBody TransferOperationDTO transferOperation) {
        return service.transfer(transferOperation);
    }

    @PostMapping("/confirmOperation")
    @Override
    public OperationDTO confirmOperation(@RequestBody ConfirmingOperationDTO confirmingOperation) {
        return service.confirmOperation(confirmingOperation);
    }

    @GetMapping("/ping")
    @Override
    public String ping() {
        return "OK";
    }

    @ExceptionHandler(CardNotFound.class)
    public ResponseEntity<ErrorResponse> cardNotFound(CardNotFound e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), TransferErrors.CARD_NOT_FOUND.getType()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CardNotValid.class)
    public ResponseEntity<ErrorResponse> cardNotValid(CardNotValid e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), TransferErrors.CARD_NOT_VALID.getType()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCVV.class)
    public ResponseEntity<ErrorResponse> invalidCVV(InvalidCVV e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), TransferErrors.INVALID_CVV.getType()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFunds.class)
    public ResponseEntity<ErrorResponse> insufficientFunds(InsufficientFunds e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), TransferErrors.INSUFFICIENT_FUNDS.getType()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationNotFound.class)
    public ResponseEntity<ErrorResponse> operationNotFound(OperationNotFound e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), ConfirmationError.OPERATION_NOT_FOUND.getType()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCode.class)
    public ResponseEntity<ErrorResponse> invalidCode(InvalidCode e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), ConfirmationError.INVALID_CODE.getType()),
                HttpStatus.BAD_REQUEST);
    }
}
