package com.dardan.movement;

import com.dardan.movement.model.Movement;
import com.dardan.movement.repository.MovementRepository;
import com.dardan.movement.service.Impl.MovementServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MovementServiceTest {

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private MovementServiceImpl movementService;
    public Movement dummyMovement() {
        return Movement.builder()
                .movementDate(LocalDate.now())
                .idAccount(1)
                .idMovementType(1)
                .value(BigDecimal.valueOf(20))
                .build();
    }

    @Test
    public void createMovementService() {
        when(movementRepository.save(dummyMovement())).thenReturn(dummyMovement());
        Movement response = movementService.save(dummyMovement());
        assertNotNull(response);
    }
}
