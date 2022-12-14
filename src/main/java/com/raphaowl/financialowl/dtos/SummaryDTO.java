package com.raphaowl.financialowl.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDTO {
    @Builder.Default
    private List<SummaryFieldDTO> summaryMonth = new ArrayList<>();
    private BigDecimal totalOfReceipt;
    private BigDecimal totalOfPayment;
    private BigDecimal total;
}
