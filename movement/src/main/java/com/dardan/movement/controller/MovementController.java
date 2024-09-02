package com.dardan.movement.controller;

import com.dardan.movement.dto.MovementDto;
import com.dardan.movement.dto.MovementUpdateDto;
import com.dardan.movement.dto.ReportDto;
import com.dardan.movement.dto.ResponseDto;
import com.dardan.movement.model.Movement;
import com.dardan.movement.service.MovementService;
import com.dardan.movement.util.Constants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/movement")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Movements Service Microservice",
                version = "0.0.1",
                description = "Módulo para la gestión de cuentas y movimientos",
                contact = @Contact(
                        name = "Dardan",
                        url = "http://github.com/dardanqsot",
                        email = "quispesotodaniel@gmail.com"
                )
        ),
        servers = { @Server(url = "http:localhost:8081")},
        tags = @Tag(name = "Movements", description = "Microservicio para la gestión de cuentas y movimientos")
)
public class MovementController {

    private final ModelMapper mapper;
    private final MovementService service;

    @GetMapping
    public ResponseEntity<ResponseDto<List<MovementDto>>> findAll(){
        List<MovementDto> lst = service.findAll().stream()
                .filter(Movement::isEnabled)
                .map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MovementDto>> findById(@PathVariable("id") Integer id){
        Movement obj = service.findMovementByIdMovementAndEnabled(id);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> create(@Valid @RequestBody MovementDto movementDto) {
        service.createMovement(convertToEntity(movementDto));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> update(@Valid @RequestBody MovementUpdateDto movementDto, @PathVariable Integer id) {
        movementDto.setIdMovement(id);
        service.updateMovement(mapper.map(movementDto, Movement.class));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> remove(@PathVariable Integer id) {
        service.deleteMovement(id);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Endpoint que muestra reporte de movimientos",
            tags = {"reportes"},
            responses = {
                    @ApiResponse(
                            description = "Response Ok",
                            responseCode = "200"
                    ),
            }
    )
    @GetMapping("/reportes")
    public ResponseEntity<ResponseDto<List<ReportDto>>> findByStock(@RequestParam("idClient") Integer idClient,
                                       @RequestParam(value = "fromDate") @DateTimeFormat(pattern= Constants.DATE_FORMAT) LocalDate fromDate,
                                       @RequestParam(value = "toDate") @DateTimeFormat(pattern=Constants.DATE_FORMAT) LocalDate toDate) {
        List<ReportDto> lst = service.findAllByIdClient(idClient, fromDate, toDate);
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    private MovementDto convertToDto(Movement obj){
        return mapper.map(obj, MovementDto.class);
    }
    private Movement convertToEntity(MovementDto dto){
        return mapper.map(dto, Movement.class);
    }

}
