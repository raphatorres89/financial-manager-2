package com.raphaowl.financialowl.services.impl;

import com.raphaowl.financialowl.dtos.MovementDTO;
import com.raphaowl.financialowl.entities.Movement;
import com.raphaowl.financialowl.repositories.MovementRepository;
import com.raphaowl.financialowl.services.MovementService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MovementDTO> findByYear(int year) {
        log.info("Finding movements by Year {}", year);
        return movementRepository.findByYear(year)
                .stream()
                .map(movement -> modelMapper.map(movement, MovementDTO.class))
                .toList();
    }

    @Override
    public Page<MovementDTO> findByYearMonth(int year, int month, Pageable pageRequest) {
        log.info("Finding movements by Year {}, Month {}, Page {}", year, month, pageRequest);
        return movementRepository.findByYearMonth(year, month, pageRequest)
                .map(movement -> modelMapper.map(movement, MovementDTO.class));
    }

    @Override
    public MovementDTO findById(String id) {
        log.info("Finding movement by id: {}", id);
        return modelMapper.map(movementRepository.findById(id)
                                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                               MovementDTO.class);
    }

    @Override
    public MovementDTO save(MovementDTO movementDTO) {
        log.info("Saving movement: {}", movementDTO);

        Movement movement = movementRepository.save(modelMapper.map(movementDTO, Movement.class));

        if (containsInstallment(movementDTO)) {
            for (int i = movementDTO.getInstallment() + 1; i <= movementDTO.getTotalInstallment(); i++) {
                movementDTO.setDueDate(movementDTO.getDueDate().plusMonths(1L));
                movementDTO.setInstallment(i);
                movementRepository.save(modelMapper.map(movementDTO, Movement.class));
            }
        }

        return modelMapper.map(movement, MovementDTO.class);
    }

    private boolean containsInstallment(MovementDTO movementDTO) {
        return Objects.nonNull(movementDTO.getInstallment()) && Objects.nonNull(movementDTO.getTotalInstallment());
    }

    @Override
    public MovementDTO update(String id, MovementDTO movementDTO) {
        log.info("Updating movement by id: {}, {}", id, movementDTO);
        Movement movement = modelMapper.map(findById(id), Movement.class);
        movement.setName(movementDTO.getName());
        movement.setType(movementDTO.getType());
        movement.setValue(movementDTO.getValue());
        movement.setIsPaid(movementDTO.getIsPaid());
        movement.setDueDate(movementDTO.getDueDate());
        movement.setInstallment(movementDTO.getInstallment());
        movement.setTotalInstallment(movementDTO.getTotalInstallment());
        return modelMapper.map(movementRepository.save(movement), MovementDTO.class);
    }

    @Override
    public void delete(String id) {
        log.info("Deleting movement by id: {}", id);
        findById(id);
        movementRepository.deleteById(id);
    }
}
