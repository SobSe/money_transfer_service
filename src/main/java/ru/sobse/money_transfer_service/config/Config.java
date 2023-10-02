package ru.sobse.money_transfer_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Configuration
public class Config {
    @Value("${logger.logpath}")
    private String pathFolder;

    @Bean
    public OutputStreamWriter fileWriter() {
        File logPath = new File( pathFolder);
        if (!logPath.exists()) {
            logPath.mkdir();
        }
        OutputStreamWriter fileWriter = null;
        try {
            String logFileName = "operations.log";
            fileWriter  = new FileWriter(pathFolder + logFileName, true);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return fileWriter;
    }
}
