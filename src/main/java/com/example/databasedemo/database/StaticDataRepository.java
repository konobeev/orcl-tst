package com.example.databasedemo.database;

import com.example.databasedemo.models.Account;

import java.util.List;

public interface StaticDataRepository {
    long createAccount(Account account);

    List<Account> getAccounts();
    List<Account> getAccountsFun();
}
