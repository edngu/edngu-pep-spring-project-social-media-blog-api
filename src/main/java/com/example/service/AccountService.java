package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {
    
    private AccountRepository accountRepository;


    /**
     * Constructor for AccountService
     * @param accountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /**
     * Get Account with correpsonding username. Returns null if not found.
     * @param username
     * @return Account
     */
    public Account getAccountByUsername(String username) {
        Optional<Account> account = accountRepository.findAccountByUsername(username);

        if (account.isPresent()) {
            return account.get();
        }
        return null;
    }


    /**
     * Add Account to database. Returns null if is username blank or password length is shorter than 4.
     * @param account
     * @return Account
     */
    public Account addAccount(Account account) {
        if (account.getUsername() == "") {
            return null;
        } else if (account.getPassword().length() < 4) {
            return null;
        }

        return accountRepository.save(account);
    }


    /**
     * Get Account with matching username and password. Returns null if not found.
     * @param username
     * @param password
     * @return Account
     */
    public Account getAccountByUsernameAndPassword(String username, String password) {
        Optional<Account> account = accountRepository.findAccountByUsernameAndPassword(username, password);

        if (account.isPresent()) {
            return account.get();
        }

        return null;
    }
}
