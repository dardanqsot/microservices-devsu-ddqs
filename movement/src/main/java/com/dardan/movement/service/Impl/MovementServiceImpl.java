package com.dardan.movement.service.Impl;


import com.dardan.movement.dto.ReportDto;
import com.dardan.movement.exception.BadRequestApiException;
import com.dardan.movement.exception.NotFoundException;
import com.dardan.movement.model.Account;
import com.dardan.movement.model.Movement;
import com.dardan.movement.proxy.client.ClientProxy;
import com.dardan.movement.proxy.client.dto.ClientDto;
import com.dardan.movement.repository.AccountRepository;
import com.dardan.movement.repository.GenericRepo;
import com.dardan.movement.repository.MovementRepository;
import com.dardan.movement.service.MovementService;
import com.dardan.movement.util.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MovementServiceImpl extends CRUDImpl<Movement, Integer> implements MovementService {

    private final MovementRepository repo;
    private final AccountRepository accountRepo;
    private final ClientProxy clientProxy;
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
        evaluateBalance(movement, account);
        repo.save(movement);
        return movement;
    }

    @Override
    @Transactional
    public Movement updateMovement(Movement movement) {
        Movement movementUpdate = repo.findMovementByIdMovementAndEnabled(movement.getIdMovement(), Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_MOVEMENT));
        if(Objects.nonNull(movement.getMovementDate())){
            movementUpdate.setMovementDate(movement.getMovementDate());
        }
        return save(movementUpdate);
    }

    @Override
    @Transactional
    public void deleteMovement(Integer idMovement) {
        Movement movementDelete = repo.findMovementByIdMovementAndEnabled(idMovement, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_MOVEMENT));
        movementDelete.changeEnabled();
        repo.save(movementDelete);
        Account account = movementDelete.getAccount();

        if(movementDelete.getIdMovementType().equals(Constants.DEPOSIT_ID)){
            account.setBalance(account.getBalance().subtract(movementDelete.getValue()));
        } else if(movementDelete.getIdMovementType().equals(Constants.WITHDRAWAL_ID)){
            account.setBalance(account.getBalance().add(movementDelete.getValue()));
        }
        accountRepo.save(account);
    }

    @Override
    public List<ReportDto> findAllByIdClient(Integer idClient, LocalDate fromDate, LocalDate toDate){
        List<Movement> movementList = repo.findAllByIdClientAndDates(idClient, fromDate, toDate);
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

    private void evaluateBalance(Movement movement, Account account) {
        if(movement.getIdMovementType().equals(Constants.DEPOSIT_ID)){
            account.setBalance(account.getBalance().add(movement.getValue()));
        } else if(movement.getIdMovementType().equals(Constants.WITHDRAWAL_ID)){
            if(account.getBalance().compareTo(movement.getValue()) < 0) {
                throw new BadRequestApiException(Constants.UNAVAILABLE_BALANCE);
            }
            account.setBalance(account.getBalance().subtract(movement.getValue()));
        }
        accountRepo.save(account);
        movement.setBalance(account.getBalance());
    }

    private Account getAccount(Integer idAccount){
        return accountRepo.findAccountByIdAccountAndEnabled(idAccount, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_ACCOUNT));
    }

    private ReportDto getReportDto(Movement movement){
        ClientDto clientDto = clientProxy.getClient(movement.getAccount().getIdClient()).getData();
        return ReportDto.builder()
                .idMovement(movement.getIdMovement())
                .idAccount(movement.getIdAccount())
                .idClient(movement.getAccount().getIdClient())
                .fecha(movement.getMovementDate())
                .cliente(clientDto.getName())
                .numeroCuenta(movement.getAccount().getAccountNumber())
                .tipo(movement.getAccount().getAccountType().getAccountType())
                .saldoInicial(movement.getAccount().getInitialBalance())
                .estado(movement.getAccount().getStatus())
                .tipoMovimiento(movement.getMovementType().getMovementType())
                .movimiento(movement.getValue())
                .saldoDisponible(movement.getBalance())
                .build();
    }
}