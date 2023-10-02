package ru.sobse.money_transfer_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Operation;
import ru.sobse.money_transfer_service.logger.LoggerImpl;
import ru.sobse.money_transfer_service.model.Amount;
import ru.sobse.money_transfer_service.model.BankCard;
import ru.sobse.money_transfer_service.model.TransferOperation;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class TestLogger {

    @Test
    public void TestLog() {
        //arrange
        BankCard card = new BankCard("4000001234567899",
                "10/25",
                "123",
                100000,
                "RUB");

        TransferOperation operation = new TransferOperation(card,
                "4000001234567890",
                new Amount(1000, "RUB"),
                (float) 10.00,
                "0000");
        operation.setOperationId("1");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        LoggerImpl logger = new LoggerImpl(writer);

        Date time = new Date();

        String expect = String.format("[%1$td.%1$tm.%1$tY %1$tT] %2$s; %3$s; %4$d; %5$f; %6$s\n",
                time,
                operation.getCardFrom().getNumber(),
                operation.getCardToNumber(),
                operation.getAmount().getValue(),
                operation.getCommission(),
                operation.getStatus());

        //act
        logger.log(operation, time);
        String actual = stream.toString();
        //assert
        Assertions.assertEquals(expect, actual);
    }
}
