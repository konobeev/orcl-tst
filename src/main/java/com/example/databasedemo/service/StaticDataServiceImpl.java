package com.example.databasedemo.service;

import com.example.databasedemo.database.StaticDataRepository;
import com.example.databasedemo.models.Account;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StaticDataServiceImpl implements StaticDataService {
    private final StaticDataRepository repository;

    public StaticDataServiceImpl(StaticDataRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public long addAccount(Account account) {
        return this.repository.createAccount(account);
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = repository.getAccounts();
        return accounts;
    }
}
