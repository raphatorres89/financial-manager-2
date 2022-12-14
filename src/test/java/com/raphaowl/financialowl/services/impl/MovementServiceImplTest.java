package com.raphaowl.financialowl.services.impl;

import com.raphaowl.financialowl.dtos.MovementDTO;
import com.raphaowl.financialowl.repositories.MovementRepository;
import com.raphaowl.financialowl.stub.MovementDTOStub;
import com.raphaowl.financialowl.stub.MovementStub;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovementServiceImplTest {

    @InjectMocks
    MovementServiceImpl movementService;
    @Mock
    MovementRepository movementRepository;
    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void givenInstallments_whenSave_thenSaveAllInstallments() {
        MovementDTO movementDTO = MovementDTOStub.valid(40, 360);

        when(movementRepository.save(any())).thenReturn(MovementStub.valid());

        MovementDTO actual = movementService.save(movementDTO);

        verify(movementRepository, times(321)).save(any());
    }
}