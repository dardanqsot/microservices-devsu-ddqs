package com.dardan.movement.service.Impl;

import com.dardan.movement.exception.NotFoundException;
import com.dardan.movement.model.Account;
import com.dardan.movement.proxy.client.ClientProxy;
import com.dardan.movement.repository.AccountRepository;
import com.dardan.movement.repository.GenericRepo;
import com.dardan.movement.service.AccountService;
import com.dardan.movement.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends CRUDImpl<Account, Integer> implements AccountService {

    private final AccountRepository repo;
    private final ClientProxy clientProxy;
    @Override
    protected GenericRepo<Account, Integer> getRepo() {
        return repo;
    }

    @Override
    public Account findAccountByIdAccountAndEnabled(Integer id) {
        Account account = repo.findAccountByIdAccountAndEnabled(id, Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_ACCOUNT));
        return account;
    }

    @Override
    public Account createAccount(Account account) {
        clientProxy.getClient(account.getIdClient()).getData();
        return save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        Account accountUpdate = repo.findAccountByIdAccountAndEnabled(account.getIdAccount(), Constants.ENABLED)
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND_ACCOUNT));
        if(Objects.nonNull(account.getIdClient())){
            clientProxy.getClient(account.getIdClient()).getData();
            accountUpdate.setIdClient(account.getIdClient());
        }

        if(Objects.nonNull(account.getIdAccountType())){
            accountUpdate.setIdAccountType(account.getIdAccountType());
        }

        if(Objects.nonNull(account.getStatus())){
            accountUpdate.setStatus(account.getStatus());
        }
        return save(accountUpdate);
    }
}