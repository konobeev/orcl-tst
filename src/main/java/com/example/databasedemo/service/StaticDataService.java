package com.example.databasedemo.service;

import com.example.databasedemo.models.Account;

import java.util.List;

public interface StaticDataService {
    long addAccount(Account account);

    List<Account> getAccounts();
}
