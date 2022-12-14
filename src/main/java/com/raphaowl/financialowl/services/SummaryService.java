package com.raphaowl.financialowl.services;

import com.raphaowl.financialowl.dtos.SummaryDTO;

public interface SummaryService {
    SummaryDTO getSummary(Integer year);
}
