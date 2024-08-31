package com.dardan.movement.repository;

import com.dardan.movement.model.Account;

import java.util.Optional;

public interface AccountRepository extends GenericRepo<Account, Integer>{

    Optional<Account> findAccountByIdAccountAndEnabled(Integer id, Boolean enabled);

}
