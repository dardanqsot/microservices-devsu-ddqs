package com.dardan.movement.service.Impl;


import com.dardan.movement.dto.ReportDto;
import com.dardan.movement.exception.NotFoundException;
import com.dardan.movement.model.Account;
import com.dardan.movement.model.Movement;
import com.dardan.movement.repository.AccountRepository;
import com.dardan.movement.repository.GenericRepo;
import com.dardan.movement.repository.MovementRepository;
import com.dardan.movement.service.MovementService;
import com.dardan.movement.util.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MovementServiceImpl extends CRUDImpl<Movement, Integer> implements MovementService {

    private final ModelMapper modelMapper;
    private final MovementRepository repo;
    private final AccountRepository accountRepo;

    @Override
    protected GenericRepo<Movement, Integer> getRepo() {
        return repo;
    }

    @Override
    public Movement findMovementByIdMovementAndEnabled(Integer id) {
        Movement movement = repo.findMovementByIdMovementAndEnabled(id, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_MOVEMENT));
        return movement;
    }

    @Override
    @Transactional
    public Movement createMovement(Movement movement) {
        Account account = getAccount(movement.getIdAccount());
        account.setBalance(account.getBalance().add(movement.getValue()));
        accountRepo.save(account);
        movement.setBalance(account.getBalance());
        repo.save(movement);
        return movement;
    }

    @Override
    public Movement updateMovement(Movement movement) {
        Movement movementUpdate = repo.findByIdMovementAndIdAccountAndEnabled(movement.getIdMovement(), movement.getIdAccount(), Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_MOVEMENT));
        if(Objects.nonNull(movement.getMovementDate())){
            movementUpdate.setMovementDate(movement.getMovementDate());
        }
        if(Objects.nonNull(movement.getMovementType())){
            movementUpdate.setMovementType(movement.getMovementType());
        }
        if(Objects.nonNull(movement.getValue())){
            movementUpdate.setValue(movement.getValue());
            Account account = getAccount(movement.getIdAccount());
            account.setBalance(account.getBalance().add(movementUpdate.getValue()));
            account.setBalance(account.getBalance().add(movementUpdate.getValue()));
            accountRepo.save(account);
            movementUpdate.setBalance(account.getBalance());
        }
        repo.save(movement);
        return save(movement);
    }

    @Override
    public void deleteMovement(Integer idMovement) {
        Movement movementDelete = repo.findMovementByIdMovementAndEnabled(idMovement, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_MOVEMENT));
        movementDelete.changeEnabled();
        repo.save(movementDelete);
        Account account = getAccount(movementDelete.getIdAccount());
        account.setBalance(account.getBalance().add(movementDelete.getValue()));
        accountRepo.save(account);
    }

    @Override
    public List<ReportDto> findAllByIdClient(Integer idClient, LocalDate fromDate, LocalDate toDate){
        List<Movement> movementList = repo.findAllByIdClient(idClient);
        List<ReportDto> lst = movementList
                .stream()
                .map(this::getReportDto)
                .sorted(
                        Comparator.comparing(ReportDto::getFecha).reversed()
                                .thenComparing(Comparator.comparing(ReportDto::getIdMovement).reversed())
                )
                .collect(Collectors.toList());
        return lst;
    }
    private Account getAccount(Integer idAccount){
        return accountRepo.findAccountByIdAccountAndEnabled(idAccount, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_ACCOUNT));
    }

    private ReportDto getReportDto(Movement movement){
        return ReportDto.builder()
                .idMovement(movement.getIdMovement())
                .idAccount(movement.getIdAccount())
                .idClient(movement.getAccount().getIdClient())
                .fecha(movement.getMovementDate())
                .cliente("tes")
                .numeroCuenta(movement.getAccount().getAccountNumber())
                .tipo(movement.getAccount().getAccountType())
                .saldoInicial(movement.getAccount().getInitialBalance())
                .estado(movement.getAccount().getStatus())
                .movimiento(movement.getValue())
                .saldoDisponible(movement.getBalance())
                .build();
    }
}