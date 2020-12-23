package com.example.databasedemo.database;

import com.example.databasedemo.database.mapper.AccountMapper;
import com.example.databasedemo.database.mapper.AccountsMapper;
import com.example.databasedemo.models.Account;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.*;

@Service
public class StaticDataRepositoryImpl implements StaticDataRepository {

    private final SimpleJdbcCall createAccountCall;
    private final SimpleJdbcCall getAccountsPCall;
    private final AccountsMapper accountsMapper;
    private final SimpleJdbcCall getAccountsFCall;
    private final AccountMapper accountMapper;

    public StaticDataRepositoryImpl(DataSource dataSource,
                                    AccountMapper accountMapper,
                                    AccountsMapper accountsMapper) {
        this.accountsMapper = Objects.requireNonNull(accountsMapper);
        this.accountMapper = Objects.requireNonNull(accountMapper);

        this.createAccountCall = new SimpleJdbcCall(dataSource)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withProcedureName("ADD_ACCOUNT")
                .declareParameters(accountMapper.createSqlParameter("P_ACCOUNT", false));

        this.getAccountsPCall = new SimpleJdbcCall(dataSource)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withProcedureName("GET_ALL_ACCOUNTS_P")
                .declareParameters(accountsMapper.createSqlParameter("P_ACCOUNTS", true));

        this.getAccountsFCall = new SimpleJdbcCall(dataSource)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withFunctionName("GET_ALL_ACCOUNTS_F")
                .declareParameters(accountsMapper.createSqlParameter("RETURN", true));
    }


    @Override
    public long createAccount(final Account account) {
        Map map = Collections.singletonMap("p_account", accountMapper.createSqlTypeValue(account));

        createAccountCall.execute(map);
        return 0;
    }

    @Override
    public List<Account> getAccounts() {
        try {
            Map<String, Object> map = getAccountsPCall.execute();
            Account[] accounts = (Account[]) map.get("P_ACCOUNTS");
            return Arrays.asList(accounts);

        } catch (Throwable t){
            return Collections.emptyList();
        }
    }

    @Override
    public List<Account> getAccountsFun() {
        try {
            Account[] accounts = (Account[])getAccountsFCall.executeFunction(Object.class);
            return Arrays.asList(accounts);
        } catch (Throwable t){
            return Collections.emptyList();
        }
    }
}
