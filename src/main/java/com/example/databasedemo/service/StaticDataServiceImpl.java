package com.example.databasedemo.service;

import com.example.databasedemo.database.StaticDataRepository;
import com.example.databasedemo.database.custom.CustomAccountRepository;
import com.example.databasedemo.database.ext.ExtAccountRepository;
import com.example.databasedemo.models.Account;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


@Service
public class StaticDataServiceImpl implements StaticDataService {
    private final StaticDataRepository repository;
    private CustomAccountRepository customAccountRepository;
    private ExtAccountRepository extAccountRepository;

    public StaticDataServiceImpl(StaticDataRepository repository,
                                 CustomAccountRepository customAccountRepository,
                                 ExtAccountRepository extAccountRepository) {
        this.repository = Objects.requireNonNull(repository);
        this.customAccountRepository = Objects.requireNonNull(customAccountRepository);
        this.extAccountRepository = Objects.requireNonNull(extAccountRepository);
    }

    public long addAccount(Account account) {
        return this.repository.createAccount(account);
    }

    @Override
    public List<Account> getAccounts() {
        testCustomRepo();
        List<Account> accounts = repository.getAccounts();
        return accounts;
    }

    public void testCustomRepo() {
        Account account = create();
        Account[] accounts = new Account[1];
        accounts[0] = account;

        Account accFun = wrapper(customAccountRepository::callFun, account);
        Account accProc = wrapper(customAccountRepository::callProc, account);
        Account[] arrFun = wrapper(customAccountRepository::callArrFun, accounts);
        Account[] arrProc = wrapper(customAccountRepository::callArrProc, accounts);
    }

    public void testExtRepo() {
        Account account = create();
        Account[] accounts = new Account[1];
        accounts[0] = account;

        Account accFun = wrapper(extAccountRepository::callFun, account);
        Account accProc = wrapper(extAccountRepository::callProc, account);
        Account[] arrFun = wrapper(extAccountRepository::callArrFun, accounts);
        Account[] arrProc = wrapper(extAccountRepository::callArrProc, accounts);
    }

    private <T, R> R wrapper(Function<T, R> function, T param) {
        try {
            return function.apply(param);
        } catch (Throwable t) {
            return null;
        }
    }


    private Account create() {
        Account account = new Account();
        account.setAccountId(123);
        account.setAccountVersion(321);
        account.setAccountName("name");
        account.setAccountNumber("number");
        account.setCurrency("cur");
        account.setAssetLocation("loc");
        account.setNote("note");
        return account;

    }
}
