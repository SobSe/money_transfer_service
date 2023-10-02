package ru.sobse.money_transfer_service.DTO;

public class ConfirmingOperationDTO {
    private String operationId;
    private String code;

    public ConfirmingOperationDTO(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }
}
