package com.raphaowl.financialowl.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raphaowl.financialowl.enums.CategoryEnum;
import com.raphaowl.financialowl.enums.MovementTypeEnum;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementDTO {
    private String id;
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    @NotBlank(message = "Nome não pode ser nulo")
    private String name;
    private Boolean isPaid;
    @NotNull
    @PositiveOrZero
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal value;
    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Builder.Default
    private LocalDate dueDate = LocalDate.now();
    @Min(value = 1)
    private Integer installment;
    @Min(value = 1)
    private Integer totalInstallment;
    @NotNull
    @Builder.Default
    private MovementTypeEnum type = MovementTypeEnum.PAYMENT;
    private CategoryEnum category;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
