package com.raphaowl.financialowl.stub;

import com.raphaowl.financialowl.dtos.MovementDTO;

public class MovementDTOStub {

    public static MovementDTO valid(Integer installment, Integer totalInstallment) {
        return MovementDTO.builder()
                .installment(installment)
                .totalInstallment(totalInstallment)
                .build();
    }

}
