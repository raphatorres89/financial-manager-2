package com.raphaowl.financialowl.services.impl;

import com.raphaowl.financialowl.dtos.MovementDTO;
import com.raphaowl.financialowl.dtos.SummaryDTO;
import com.raphaowl.financialowl.dtos.SummaryFieldDTO;
import com.raphaowl.financialowl.enums.MovementTypeEnum;
import com.raphaowl.financialowl.services.MovementService;
import com.raphaowl.financialowl.services.SummaryService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final MovementService movementService;

    @Override
    public SummaryDTO getSummary(Integer year) {
        log.info("Getting summary of year: {}", year);
        return buildSummary(movementService.findByYear(year), year);
    }

    private SummaryDTO buildSummary(List<MovementDTO> movements, Integer year) {
        SummaryDTO summaryDTO = new SummaryDTO();

        movements.stream()
                .collect(groupingBy(movementDTO -> movementDTO.getDueDate().getMonth()))
                .forEach((month, movementDTOS) -> summaryDTO.getSummaryMonth().add(buildSummaryFieldPerMonth(year, month, movementDTOS)));

        summaryDTO.setTotalOfPayment(summaryDTO.getSummaryMonth().stream()
                                             .map(SummaryFieldDTO::getSumOfPayment)
                                             .reduce(BigDecimal.ZERO, BigDecimal::add));
        summaryDTO.setTotalOfReceipt(summaryDTO.getSummaryMonth().stream()
                                             .map(SummaryFieldDTO::getSumOfReceipt)
                                             .reduce(BigDecimal.ZERO, BigDecimal::add));
        summaryDTO.setTotal(summaryDTO.getTotalOfReceipt().subtract(summaryDTO.getTotalOfPayment()));
        summaryDTO.getSummaryMonth().sort(Comparator.comparing(SummaryFieldDTO::getYearMonth));
        return summaryDTO;
    }

    private SummaryFieldDTO buildSummaryFieldPerMonth(Integer year, Month month, List<MovementDTO> movementDTOS) {
        SummaryFieldDTO summaryFieldDTO = new SummaryFieldDTO();
        summaryFieldDTO.setYearMonth(YearMonth.of(year, month));
        movementDTOS.forEach(movementDTO -> buildSummaryField(movementDTO, summaryFieldDTO));
        return summaryFieldDTO;
    }

    private void buildSummaryField(MovementDTO movementDTO, SummaryFieldDTO summaryFieldDTO) {
        if (movementDTO.getType().equals(MovementTypeEnum.PAYMENT)) {
            summaryFieldDTO.setSumOfPayment(summaryFieldDTO.getSumOfPayment().add(movementDTO.getValue()));
        }
        if (movementDTO.getType().equals(MovementTypeEnum.RECEIPT)) {
            summaryFieldDTO.setSumOfReceipt(summaryFieldDTO.getSumOfReceipt().add(movementDTO.getValue()));
        }
    }
}
