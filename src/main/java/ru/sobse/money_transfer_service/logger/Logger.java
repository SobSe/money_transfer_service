package ru.sobse.money_transfer_service.logger;

import ru.sobse.money_transfer_service.model.TransferOperation;

import java.util.Date;

public interface Logger {
    void log(TransferOperation operation, Date time);
}
