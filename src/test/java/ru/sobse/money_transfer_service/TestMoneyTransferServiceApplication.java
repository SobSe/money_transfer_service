package ru.sobse.money_transfer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestMoneyTransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MoneyTransferServiceApplication::main).with(TestMoneyTransferServiceApplication.class).run(args);
	}

}
