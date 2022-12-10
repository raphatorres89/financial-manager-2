package com.raphaowl.financialowl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AlertDTO {
    private String type;
    private String title;
    private String message;
}
