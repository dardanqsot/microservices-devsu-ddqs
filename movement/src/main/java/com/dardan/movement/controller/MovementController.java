package com.dardan.movement.controller;

import com.dardan.movement.dto.MovementDto;
import com.dardan.movement.dto.ReportDto;
import com.dardan.movement.dto.ResponseDto;
import com.dardan.movement.model.Movement;
import com.dardan.movement.service.MovementService;
import com.dardan.movement.util.Constants;
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
    public ResponseEntity<ResponseDto<Void>> update(@Valid @RequestBody MovementDto movementDto, @PathVariable Integer id) {
        movementDto.setIdMovement(id);
        Movement movement = service.updateMovement(convertToEntity(movementDto));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> remove(@PathVariable Integer id) {
        service.deleteMovement(id);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.NO_CONTENT);
    }

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
