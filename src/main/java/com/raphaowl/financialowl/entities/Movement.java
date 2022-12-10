package com.raphaowl.financialowl.entities;

import com.raphaowl.financialowl.enums.CategoryEnum;
import com.raphaowl.financialowl.enums.MovementTypeEnum;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    @Id
    private String id;
    @NotBlank
    private String name;
    private Boolean isPaid;
    @NotNull
    private BigDecimal value;
    @NotNull
    private LocalDate dueDate;
    private Integer installment;
    private Integer totalInstallment;
    @NotNull
    private MovementTypeEnum type;
//    @NotNull
    private CategoryEnum category;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
