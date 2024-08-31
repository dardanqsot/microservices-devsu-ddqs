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
                .orElseThrow(NotFoundException.supplier(Constants.NOT_FOUND));
        return account;
    }

    @Override
    public Account createAccount(Account account) {
        clientProxy.getClient(account.getIdClient()).getData();
        return save(account);
    }

    @Override
    public Account updateAccount(Account account) throws Exception {
        clientProxy.getClient(account.getIdClient()).getData();
        this.update(account, account.getIdAccount());
        return save(account);
    }
}