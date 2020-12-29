/*
 * Copyright 2008-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.databasedemo.database.ext.oracle;

import oracle.jdbc.driver.OracleConnection;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of the SqlTypeValue interface, for convenient
 * creation of provided scalar values as an ARRAY.
 *
 * <p>A usage example from a StoredProcedure:
 *
 * <pre class="code">proc.declareParameter(new SqlParameter("myarray", Types.ARRAY, "NUMBERS"));
 * ...
 *
 * Map in = new HashMap();
 * in.put("myarray", new SqlArrayValue&lt;Number&gt;(objectArray);
 * Map out = proc.execute(in);
 * </pre>
 *
 * @author Thomas Risberg
 * @see org.springframework.jdbc.core.SqlTypeValue
 * @see AbstractSqlTypeValue
 * @see org.springframework.jdbc.core.simple.SimpleJdbcCall
 * @see org.springframework.jdbc.object.StoredProcedure
 * @since 1.0
 */
public class SqlArrayValue<T> extends AbstractSqlTypeValue {

    private T[] values;

    private String defaultTypeName;


    /**
     * Constructor that takes one parameter with the array of values passed in to the
     * statement.
     *
     * @param values the array containing the values.
     */
    public SqlArrayValue(T[] values) {
        this.values = values;
    }

    /**
     * Constructor that takes two parameters, one parameter with the array of values passed in to the
     * statement and one that takes the default type name to be used when the context where this class
     * is used is not aware of the type name to use.
     *
     * @param values          the array containing the values.
     * @param defaultTypeName the default type name.
     */
    public SqlArrayValue(T[] values, String defaultTypeName) {
        this.values = values;
        this.defaultTypeName = defaultTypeName;
    }


    /**
     * The implementation for this specific type. This method is called internally by the
     * Spring Framework during the out parameter processing and it's not accessed by application
     * code directly.
     *
     * @see AbstractSqlTypeValue
     */
    protected Object createTypeValue(Connection conn, int sqlType, String typeName)
            throws SQLException {
        if (typeName == null && defaultTypeName == null) {
            throw new InvalidDataAccessApiUsageException(
                    "The typeName is null in this context. Consider setting the defaultTypeName.");
        }

        return ArrayValueUtils.createOracleArray(conn, typeName != null ? typeName : defaultTypeName, values);
    }
}
