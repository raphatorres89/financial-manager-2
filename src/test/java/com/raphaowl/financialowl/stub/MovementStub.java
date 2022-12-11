package com.raphaowl.financialowl.stub;

import com.raphaowl.financialowl.entities.Movement;

public class MovementStub {

    public static Movement valid() {
        return Movement.builder()
                .build();
    }
}
