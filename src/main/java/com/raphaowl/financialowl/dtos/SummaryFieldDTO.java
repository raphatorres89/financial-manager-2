package com.raphaowl.financialowl.dtos;

import java.math.BigDecimal;
import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryFieldDTO {
    private YearMonth yearMonth;
    @Builder.Default
    private BigDecimal sumOfReceipt = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal sumOfPayment = BigDecimal.ZERO;
}
