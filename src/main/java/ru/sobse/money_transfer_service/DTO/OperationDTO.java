package ru.sobse.money_transfer_service.DTO;

import java.util.Objects;

public class OperationDTO {
    private String operationId;

    public OperationDTO() {

    }

    public OperationDTO(String id) {
        this.operationId = id;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDTO that = (OperationDTO) o;
        return Objects.equals(operationId, that.operationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId);
    }
}
