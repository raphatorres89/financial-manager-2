package com.raphaowl.financialowl.dtos;

public class SuccessAlert extends AlertDTO {
    public SuccessAlert(String message) {
        super.setTitle("Sucesso");
        super.setType("alert-success");
        super.setMessage(message);
    }
}
