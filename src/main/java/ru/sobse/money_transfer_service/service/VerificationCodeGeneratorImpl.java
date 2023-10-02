package ru.sobse.money_transfer_service.service;

import org.springframework.stereotype.Component;

@Component
public class VerificationCodeGeneratorImpl implements VerificationCodeGenerator {
    @Override
    public String generate() {
        int code = (int) (Math.random() * 10000);
        return String.format("%04d", code);
    }
}
