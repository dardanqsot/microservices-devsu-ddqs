package com.dardan.movement.service.Impl;


import com.dardan.movement.exception.NotFoundException;
import com.dardan.movement.model.Movement;
import com.dardan.movement.repository.GenericRepo;
import com.dardan.movement.repository.MovementRepository;
import com.dardan.movement.service.MovementService;
import com.dardan.movement.util.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MovementServiceImpl extends CRUDImpl<Movement, Integer> implements MovementService {

    private final ModelMapper modelMapper;
    private final MovementRepository repo;

    @Override
    protected GenericRepo<Movement, Integer> getRepo() {
        return repo;
    }

    @Override
    public Movement findMovementByIdMovementAndEnabled(Integer id) {
        Movement movement = repo.findMovementByIdMovementAndEnabled(id, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND));
        return movement;
    }
}