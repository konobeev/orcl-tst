package com.example.databasedemo.database.mapper;

import static java.sql.Types.STRUCT;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.example.databasedemo.models.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper extends SqlParameterMapper<Account, Struct> {
    private static final String TYPE_NAME = "ACCOUNT_T";

    @Override
    protected Account createObject(final Struct struct) throws SQLException {
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

    @Override
    protected Struct createSqlValue(Connection con, Account account) throws SQLException {

        return con.createStruct(TYPE_NAME, new Object[]{
                account.getAccountId(),
                account.getAccountVersion(),
                account.getAccountName(),
                account.getAccountNumber(),
                account.getCurrency(),
                account.getAssetLocation(),
                account.getNote()
        });
    }

    @Override
    public SqlParameter createSqlParameter(String paramaterName, boolean outParameter) {
        if (outParameter) {
            return new SqlOutParameter(paramaterName, STRUCT, TYPE_NAME, this);
        } else {
            return new SqlParameter(paramaterName, STRUCT, TYPE_NAME);
        }
    }
}
