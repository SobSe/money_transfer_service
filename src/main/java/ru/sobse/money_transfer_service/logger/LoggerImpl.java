package ru.sobse.money_transfer_service.logger;

import org.springframework.stereotype.Component;
import ru.sobse.money_transfer_service.model.TransferOperation;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

@Component
public class LoggerImpl implements Logger {

    private final OutputStreamWriter writer;

    public LoggerImpl(OutputStreamWriter writer) {
        this.writer = writer;
    }


    @Override
    public void log(TransferOperation operation, Date time) {
        try {
            writer.write(String.format("[%1$td.%1$tm.%1$tY %1$tT] %2$s; %3$s; %4$d; %5$f; %6$s\n",
                    time,
                    operation.getCardFrom().getNumber(),
                    operation.getCardToNumber(),
                    operation.getAmount().getValue(),
                    operation.getCommission(),
                    operation.getStatus()));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
