package com.example.databasedemo.database.ext;

import com.example.databasedemo.database.ext.oracle.*;
import com.example.databasedemo.models.Account;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ExtAccountRepository {
    private final SimpleJdbcCall dummyFunCall;
    private final SimpleJdbcCall dummyArrayFunCall;
    private final SimpleJdbcCall dummyProcCall;
    private final SimpleJdbcCall dummyArrayProcCall;
    private final JdbcTemplate jdbcTemplate;
    private final AccountMapperImpl accountMapper;

    public ExtAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate);
        //    function dummy_fun(p_account in account_t) return account_t;
        //    function dummy_array_fun(p_accounts in ACCOUNT_ARRAY_T) return ACCOUNT_ARRAY_T;
        //    procedure dummy_proc(p_in_account in ACCOUNT_T, p_out_account out ACCOUNT_T);
        //    procedure dummy_array_proc(p_in_accounts in ACCOUNT_ARRAY_T, p_out_accounts out ACCOUNT_ARRAY_T);

        accountMapper = new AccountMapperImpl();
        new SqlOutParameter("P_ACCOUNTS", OracleTypes.ARRAY, "ACCOUNT_ARRAY_T", new SqlReturnStructArray<>(accountMapper))
        ;

        this.dummyFunCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withFunctionName("DUMMY_FUN")
                .withReturnValue()
                .declareParameters(
                        new SqlParameter("P_ACCOUNT", OracleTypes.STRUCT, "ACCOUNT_T"),
                        new SqlOutParameter("RETURN", OracleTypes.STRUCT, "ACCOUNT_T", new SqlReturnStruct(accountMapper)));

        this.dummyArrayFunCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withFunctionName("DUMMY_ARRAY_FUN")
                .declareParameters(
                        new SqlParameter("P_ACCOUNTS", OracleTypes.ARRAY, "ACCOUNT_ARRAY_T"),
                        new SqlOutParameter("RETURN", OracleTypes.ARRAY, "ACCOUNT_ARRAY_T", new SqlReturnStructArray<>(accountMapper)));

        this.dummyProcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withProcedureName("DUMMY_PROC")
                .declareParameters(
                        new SqlParameter("P_IN_ACCOUNT", OracleTypes.STRUCT, "ACCOUNT_T"),
                        new SqlOutParameter("P_OUT_ACCOUNT", OracleTypes.STRUCT, "ACCOUNT_T", new SqlReturnStruct(accountMapper)));

        this.dummyArrayProcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("APP_OWNER")
                .withCatalogName("STATIC_DATA")
                .withProcedureName("DUMMY_ARRAY_PROC")
                .declareParameters(
                        new SqlParameter("P_IN_ACCOUNTS", OracleTypes.ARRAY, "ACCOUNT_ARRAY_T"),
                        new SqlOutParameter("P_OUT_ACCOUNTS", OracleTypes.ARRAY, "ACCOUNT_ARRAY_T", new SqlReturnStructArray<>(accountMapper)));
    }


    public Account callProc(Account account) {
        Map output = dummyProcCall.execute(new SqlStructValue<>(account, accountMapper));
        Account result = (Account) output.get("P_OUT_ACCOUNT");

        return result;
    }

    public Account callFun(Account account) {
        Account result = dummyFunCall.executeFunction(Account.class, new SqlStructValue<>(account, accountMapper));

        return result;
    }

    public Account[] callArrProc(Account[] accounts) {
        Map output = dummyArrayProcCall.execute(new SqlStructArrayValue<>(accounts, accountMapper, "ACCOUNT_T"));
        Object[] account_t = (Object[]) output.get("P_OUT_ACCOUNTS");

        return Arrays.copyOf(account_t, account_t.length, Account[].class);
    }

    public Account[] callArrFun(Account[] accounts) {
        Object[] account_t = dummyArrayFunCall.executeFunction(Object[].class, new SqlStructArrayValue<>(accounts, accountMapper, "ACCOUNT_T"));

        return Arrays.copyOf(account_t, account_t.length, Account[].class);
    }


    static class AccountMapperImpl implements StructMapper<Account> {

        @Override
        public Struct toStruct(Account source, Connection conn, String typeName) throws SQLException {
            return conn.createStruct("ACCOUNT_T", new Object[]{
                    source.getAccountId(),
                    source.getAccountVersion(),
                    source.getAccountName(),
                    source.getAccountNumber(),
                    source.getCurrency(),
                    source.getAssetLocation(),
                    source.getNote()
            });
        }

        @Override
        public Account fromStruct(Struct struct) throws SQLException {
            return new Account() {
                {
                    Object[] attributes = struct.getAttributes();
                    BigDecimal id = (BigDecimal) attributes[0];
                    if (id != null) {
                        setAccountId(id.longValue());
                    }

                    setAccountVersion(((BigDecimal) attributes[1]).longValue());
                    setAccountName((String) attributes[2]);
                    setAccountNumber((String) attributes[3]);
                    setCurrency((String) attributes[4]);
                    setAssetLocation((String) attributes[5]);
                    setNote((String) attributes[6]);
                }
            };
        }
    }
}
