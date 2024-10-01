package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;


@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{

    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
    
}
