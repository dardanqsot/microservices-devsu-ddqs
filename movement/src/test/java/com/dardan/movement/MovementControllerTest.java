package com.dardan.movement;

import com.dardan.movement.controller.MovementController;
import com.dardan.movement.dto.MovementDto;
import com.dardan.movement.dto.ResponseDto;
import com.dardan.movement.model.Movement;
import com.dardan.movement.service.MovementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MovementService movementService;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private MovementController movementController;

    public MovementDto dummyMovementRequestDto() {
        return MovementDto.builder()
                .movementDate(LocalDate.now())
                .idAccount(1)
                .idMovementType(1)
                .value(BigDecimal.valueOf(20))
                .build();
    }
    public Movement dummyMovement() {
        return Movement.builder()
                .movementDate(LocalDate.now())
                .idAccount(1)
                .idMovementType(1)
                .value(BigDecimal.valueOf(20))
                .build();
    }

    @Test
    @DisplayName("Save Movement 201")
    void when_call_save_movement_then_return_201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/movement")
                        .content("{\n" +
                                "    \"movementDate\": \"31/08/2024\",\n" +
                                "    \"idAccount\": 1,\n" +
                                "    \"idMovementType\": 2,\n" +
                                "    \"value\": 15.00\n" +
                                "}\n").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void saveProductController() {
        when(movementService.save(dummyMovement())).thenReturn(dummyMovement());

        ResponseEntity<ResponseDto<Void>> response =
                movementController.create(dummyMovementRequestDto());
        assertNotNull(response);
    }
}
