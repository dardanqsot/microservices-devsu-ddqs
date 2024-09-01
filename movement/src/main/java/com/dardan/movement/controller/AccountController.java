package com.dardan.movement.controller;


import com.dardan.movement.dto.AccountDto;
import com.dardan.movement.dto.ResponseDto;
import com.dardan.movement.model.Account;
import com.dardan.movement.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final ModelMapper mapper;
    private final AccountService service;

    @GetMapping
    public ResponseEntity<ResponseDto<List<AccountDto>>> findAll(){
        List<AccountDto> lst = service.findAll().stream()
                .filter(Account::isEnabled)
                .map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<AccountDto>> findById(@PathVariable("id") Integer id){
        Account obj = service.findAccountByIdAccountAndEnabled(id);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> create(@Valid @RequestBody AccountDto accountDto) {
        accountDto.setBalance(accountDto.getInitialBalance());
        service.createAccount(convertToEntity(accountDto));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> update(@Valid @RequestBody AccountDto accountDto, @PathVariable Integer id) throws Exception {
        accountDto.setIdAccount(id);
        service.updateAccount(convertToEntity(accountDto));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> remove(@PathVariable Integer id) {
        Account account = service.findAccountByIdAccountAndEnabled(id);
        account.changeEnabled();
        service.save(account);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.NO_CONTENT);
    }
    private AccountDto convertToDto(Account obj){
        return mapper.map(obj, AccountDto.class);
    }
    private Account convertToEntity(AccountDto dto){
        return mapper.map(dto, Account.class);
    }

}
