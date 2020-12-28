package com.example.databasedemo.database.custom;

import com.example.databasedemo.database.custom.mapper.AccountArrayMapper;
import com.example.databasedemo.database.custom.mapper.AccountMapper;
import com.example.databasedemo.database.ext.oracle.SqlStructArrayValue;
import com.example.databasedemo.models.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
public class CustomAccountRepository {
    private final SimpleJdbcCall dummyFunCall;
    private final SimpleJdbcCall dummyArrayFunCall;
    private final SimpleJdbcCall dummyProcCall;
    private final SimpleJdbcCall dummyArrayProcCall;
    private final JdbcTemplate jdbcTemplate;
    private AccountMapper accountMapper;
    private AccountArrayMapper accountArrayMapper;

    public CustomAccountRepository(JdbcTemplate jdbcTemplate,
                                   AccountMapper accountMapper,
                                   AccountArrayMapper accountArrayMapper) {
        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate);
        this.accountMapper = Objects.requireNonNull(accountMapper);
        this.accountArrayMapper = Objects.requireNonNull(accountArrayMapper);
        //    function dummy_fun(p_account in account_t) return account_t;
        //    function dummy_array_fun(p_accounts in ACCOUNT_ARRAY_T) return ACCOUNT_ARRAY_T;
        //    procedure dummy_proc(p_in_account in ACCOUNT_T, p_out_account out ACCOUNT_T);
        //    procedure dummy_array_proc(p_in_accounts in ACCOUNT_ARRAY_T, p_out_accounts out ACCOUNT_ARRAY_T);

        this.dummyFunCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withFunctionName("DUMMY_FUN")
                .declareParameters(accountMapper.createSqlParameter("P_ACCOUNT", false))
                .declareParameters(accountMapper.createSqlParameter("RETURN", true));

        this.dummyArrayFunCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withFunctionName("DUMMY_ARRAY_FUN")
                .declareParameters(accountArrayMapper.createSqlParameter("P_ACCOUNTS", false))
                .declareParameters(accountArrayMapper.createSqlParameter("RETURN", true));

        this.dummyProcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withProcedureName("DUMMY_PROC")
                .declareParameters(accountMapper.createSqlParameter("P_IN_ACCOUNT", false))
                .declareParameters(accountMapper.createSqlParameter("P_OUT_ACCOUNT", true));

        this.dummyArrayProcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withProcedureName("DUMMY_ARRAY_PROC")
                .declareParameters(accountArrayMapper.createSqlParameter("P_IN_ACCOUNTS", false))
                .declareParameters(accountArrayMapper.createSqlParameter("P_OUT_ACCOUNTS", true));
    }

    public Account callProc(Account account) {
        Map input = Collections.singletonMap("P_IN_ACCOUNT", accountMapper.createSqlTypeValue(account));
        Map output = dummyProcCall.execute(input);
        Account result = (Account) output.get("P_OUT_ACCOUNT");

        return result;
    }

    public Account callFun(Account account) {
        Map input = Collections.singletonMap("p_account", accountMapper.createSqlTypeValue(account));
        Account result = dummyFunCall.executeFunction(Account.class, input);

        return result;
    }

    public Account[] callArrProc(Account[] accounts) {
        Map input = Collections.singletonMap("P_IN_ACCOUNTS", accountArrayMapper.createSqlTypeValue(accounts));
        Map output = dummyArrayProcCall.execute(input);
        Account[] result = (Account[]) output.get("P_OUT_ACCOUNTS");

        return result;
    }

    public Account[] callArrFun(Account[] accounts) {
        Map input = Collections.singletonMap("P_ACCOUNTS", accountArrayMapper.createSqlTypeValue(accounts));
        Account[] result = (Account[])dummyArrayFunCall.executeFunction(Object.class, input);

        return result;
    }

}
