begin
    INSERT INTO ACCOUNTS
    (account_id,
     account_version,
     account_name,
     account_number,
     currency,
     asset_location,
     note)
    VALUES (ACCOUNT_SEQ.nextval,
            1,
            'first account',
            '12345',
            'XAU',
            'LDN',
            'test account');

    INSERT INTO ACCOUNTS
    (account_id,
     account_version,
     account_name,
     account_number,
     currency,
     asset_location,
     note)
    VALUES (ACCOUNT_SEQ.nextval,
            1,
            'second account',
            '12345',
            'XAG',
            'LDN',
            'test account');

    INSERT INTO ACCOUNTS
    (account_id,
     account_version,
     account_name,
     account_number,
     currency,
     asset_location,
     note)
    VALUES (ACCOUNT_SEQ.nextval,
            1,
            'third account',
            '220022',
            'XAU',
            'LDN',
            'test account');
    INSERT INTO ACCOUNTS
    (account_id,
     account_version,
     account_name,
     account_number,
     currency,
     asset_location,
     note)
    VALUES (ACCOUNT_SEQ.nextval,
            1,
            'forth account',
            '220022',
            'XAG',
            'LDN',
            'test account');

end;
/