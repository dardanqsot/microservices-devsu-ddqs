package com.dardan.movement.service;

import com.dardan.movement.model.Account;

public interface AccountService extends CRUD<Account, Integer> {

    Account findAccountByIdAccountAndEnabled(Integer id);

    Account createAccount(Account account);

    Account updateAccount(Account account) throws Exception;
}
