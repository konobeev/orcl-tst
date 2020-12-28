package com.example.databasedemo.database.custom;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.OracleContainer;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.rnorth.visibleassertions.VisibleAssertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomAccountRepositoryTest {

    @Rule
    public static OracleContainer oracleContainer = new OracleContainer("name_of_your_oracle_xe_image");

    @BeforeAll
    public static void startup() {
        oracleContainer.start();
    }

    @TestConfiguration
    static class OracleTestConfiguration {

        @Bean
        DataSource dataSource() {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(oracleContainer.getJdbcUrl());
            hikariConfig.setUsername(oracleContainer.getUsername());
            hikariConfig.setPassword(oracleContainer.getPassword());

            return new HikariDataSource(hikariConfig);
        }
    }

    @Test
    public void testSimple() throws SQLException {
        try (OracleContainer oracle = new OracleContainer()) {
//            ResultSet resultSet = performQuery(oracle, "SELECT 1 FROM dual");
//
//            int resultSetInt = resultSet.getInt(1);
//
//            assertEquals("A basic SELECT query succeeds", 1, resultSetInt);
        }
    }

    @Test
    void callProc() {

    }

    @Test
    void callFun() {
    }

    @Test
    void callArrProc() {
    }

    @Test
    void callArrFun() {
    }
}