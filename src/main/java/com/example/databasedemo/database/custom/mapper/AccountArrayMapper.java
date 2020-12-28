package com.example.databasedemo.database.custom.mapper;

import com.example.databasedemo.models.Account;
import oracle.jdbc.driver.OracleConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;

import static java.sql.Types.ARRAY;

@Service
public class AccountArrayMapper extends SqlParameterMapper<Account[], Array> {
    private static final String TYPE_NAME = "ACCOUNT_ARRAY_T";

    @Autowired
    private AccountMapper mapper;

    @Override
    protected Account[] createObject(Array array) throws SQLException {
        Object[] values = (Object[]) array.getArray();

        if (values == null || values.length == 0) {
            return null;
        }

        Account[] accounts = new Account[values.length];

        for (int i = 0; i < values.length; i++) {
            accounts[i] = mapper.createObject((Struct) values[i]);
        }

        return accounts;
    }

    @Override
    protected Array createSqlValue(Connection con, Account[] accounts)
            throws SQLException {

        Object[] values = new Object[accounts.length];

        for (int i = 0; i < accounts.length; i++) {
            values[i] = mapper.createSqlValue(con, accounts[i]);
        }

        if (con.isWrapperFor(OracleConnection.class)) {
            OracleConnection oracleConn = con.unwrap(OracleConnection.class);
            return oracleConn.createOracleArray(TYPE_NAME, values);
        } else {
            // recover, not an oracle connection
            return con.createArrayOf(TYPE_NAME, values);
        }
    }

    @Override
    public SqlParameter createSqlParameter(String paramaterName,
                                           boolean outParameter) {
        if (outParameter) {
            return new SqlOutParameter(paramaterName, ARRAY, TYPE_NAME, this);
        } else {
            return new SqlParameter(paramaterName, ARRAY, TYPE_NAME);
        }
    }

}
