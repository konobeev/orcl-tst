CREATE OR REPLACE TYPE id_version_t AS OBJECT (id number(10), version number(10));
CREATE TYPE id_version_array_t AS TABLE OF id_version_t;


CREATE OR REPLACE TYPE account_t AS OBJECT (
                                               account_id   number( 10 ) ,
                                               account_version   number( 10 ) ,
                                               account_name VARCHAR2( 40 ),
                                               account_number VARCHAR2( 40 ),
                                               currency VARCHAR2( 3 ),
                                               asset_location VARCHAR2( 3 ),
                                               note VARCHAR2( 256 ));
CREATE TYPE account_array_t AS TABLE OF account_t;
