package com.raphaowl.financialowl.services;

import com.raphaowl.financialowl.dtos.MovementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovementService {
    List<MovementDTO> findByYear(int year);
    Page<MovementDTO> findByYearMonth(int year, int month, Pageable of);
    MovementDTO save(MovementDTO movementDTO);
    MovementDTO update(String id, MovementDTO movementDTO);
    MovementDTO findById(String id);
    void delete(String id);
}
