package com.dardan.movement.controller;

import com.dardan.movement.dto.MovementDto;
import com.dardan.movement.dto.ResponseDto;
import com.dardan.movement.model.Movement;
import com.dardan.movement.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/movement")
@RequiredArgsConstructor
public class MovementController {

    private final ModelMapper mapper;
    private final MovementService service;

    @GetMapping
    public ResponseEntity<ResponseDto<List<MovementDto>>> findAll(){
        List<MovementDto> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MovementDto>> findById(@PathVariable("id") Integer uuid){
        Movement obj = service.findById(uuid);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> create(@Valid @RequestBody MovementDto movementDto) {
        service.save(convertToEntity(movementDto));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> update(@Valid @RequestBody MovementDto movementDto, @PathVariable Integer id) throws Exception {
        movementDto.setIdMovement(id);
        Movement movement = service.update(convertToEntity(movementDto), id);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> remove(@PathVariable Integer id) throws Exception {
        service.delete(id);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.NO_CONTENT);
    }
    private MovementDto convertToDto(Movement obj){
        return mapper.map(obj, MovementDto.class);
    }
    private Movement convertToEntity(MovementDto dto){
        return mapper.map(dto, Movement.class);
    }

}
