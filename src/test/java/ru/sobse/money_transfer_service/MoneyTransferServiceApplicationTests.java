package ru.sobse.money_transfer_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.sobse.money_transfer_service.DTO.ConfirmingOperationDTO;
import ru.sobse.money_transfer_service.DTO.OperationDTO;
import ru.sobse.money_transfer_service.DTO.TransferOperationDTO;
import ru.sobse.money_transfer_service.model.Amount;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MoneyTransferServiceApplicationTests {
	private static final GenericContainer<?> mtsApp = new GenericContainer<>("mts:latest")
			.withExposedPorts(5500);

	@Autowired
	private TestRestTemplate testRestTemplate;

	@BeforeAll
	public static void setUp() {
		mtsApp.start();
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void mtsAppTransferTest() {
		//arrange
		TransferOperationDTO transferOperationDTO = new TransferOperationDTO("4000001234567899",
				"10/25",
				"123",
				"123456789123456",
				new Amount(1000, "RUB"));
		String url = "http://localhost:" + mtsApp.getMappedPort(5500) + "/transfer";
		OperationDTO expect = new OperationDTO("1");
		//act
		ResponseEntity<OperationDTO> forEntity = testRestTemplate.postForEntity(url,
				transferOperationDTO,
				OperationDTO.class);
		OperationDTO actual = forEntity.getBody();
		//assert
		Assertions.assertEquals(expect, actual);
	}

	@Test
	public void mtsAppConfirmTest() {
		//arrange
		TransferOperationDTO transferOperationDTO = new TransferOperationDTO("4000001234567899",
				"10/25",
				"123",
				"123456789123456",
				new Amount(1000, "RUB"));

		String url = "http://localhost:" + mtsApp.getMappedPort(5500) + "/transfer";
		ResponseEntity<OperationDTO> forEntity = testRestTemplate.postForEntity(url,
				transferOperationDTO,
				OperationDTO.class);
		OperationDTO operation = forEntity.getBody();
		ConfirmingOperationDTO confirmingOperationDTO = new ConfirmingOperationDTO(operation.getOperationId(), "0000");
		OperationDTO expect = new OperationDTO(operation.getOperationId());

		String urlConfirm = "http://localhost:" + mtsApp.getMappedPort(5500) + "/confirmOperation";
		//act
		ResponseEntity<OperationDTO> forEntityConfirm = testRestTemplate.postForEntity(urlConfirm,
				confirmingOperationDTO,
				OperationDTO.class);
		OperationDTO actual = forEntityConfirm.getBody();
		//assert
		Assertions.assertEquals(expect, actual);
	}

	@Test
	public void mtsAppPingTest() {
		//arrange
		String url = "http://localhost:" + mtsApp.getMappedPort(5500) + "/ping";
		String expect = "OK";
		//act
		ResponseEntity<String> forEntityConfirm = testRestTemplate.getForEntity(url, String.class);
		String actual = forEntityConfirm.getBody();
		//assert
		Assertions.assertEquals(expect, actual);
	}
}
