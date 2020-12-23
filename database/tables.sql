CREATE SEQUENCE account_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE accounts
(
    account_id      number(10),
    account_version number(10),
    account_name    VARCHAR2(40)  NOT NULL,
    account_number  VARCHAR2(40)  NOT NULL,
    currency        VARCHAR2(3)   NOT NULL,
    asset_location  VARCHAR2(3)   NOT NULL,
    note            VARCHAR2(256) NOT NULL,

    CONSTRAINT pk_account
        PRIMARY KEY (account_id, account_version)
);


