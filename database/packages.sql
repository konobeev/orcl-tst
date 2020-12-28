CREATE OR REPLACE PACKAGE static_data
IS
    PROCEDURE add_account(p_account in ACCOUNT_T);
    --PROCEDURE get_account_by_id(p_id in number(10));
    --PROCEDURE get_account_by_id_version(p_id_version in ID_VERSION_T);
    --PROCEDURE get_account_by(p_account in ACCOUNT_T, p_result out ACCOUNT_ARRAY_T);
    FUNCTION get_all_accounts_f RETURN ACCOUNT_ARRAY_T;
    procedure get_all_accounts_p(p_accounts out ACCOUNT_ARRAY_T);

    function dummy_fun(p_account in account_t) return account_t;
    function dummy_array_fun(p_accounts in ACCOUNT_ARRAY_T) return ACCOUNT_ARRAY_T;
    procedure dummy_proc(p_in_account in ACCOUNT_T, p_out_account out ACCOUNT_T);
    procedure dummy_array_proc(p_in_accounts in ACCOUNT_ARRAY_T, p_out_accounts out ACCOUNT_ARRAY_T);
END static_data;
/


CREATE OR REPLACE PACKAGE BODY static_data
IS

    procedure check_account_unique(p_account in ACCOUNT_T)
        is
        v_count number;
    begin
        SELECT count(*)
        INTO v_count
        FROM ACCOUNTS a
        WHERE p_account.ACCOUNT_NUMBER = a.ACCOUNT_NUMBER
          and p_account.ASSET_LOCATION = a.ASSET_LOCATION
          and p_account.CURRENCY = a.CURRENCY;

        if (v_count > 0)
        then
            raise_application_error(-20000, 'account exists');
        end if;
    end;

    PROCEDURE add_account(p_account in ACCOUNT_T)
        IS
    BEGIN
        check_account_unique(p_account);

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
                p_account.ACCOUNT_NAME,
                p_account.ACCOUNT_NUMBER,
                p_account.CURRENCY,
                p_account.ASSET_LOCATION,
                p_account.NOTE);
        COMMIT;
    END add_account;

--     FUNCTION get_record(p_emp_no IN NUMBER)
--     RETURN emp%ROWTYPE
--     IS
--     l_emp_rec emp%ROWTYPE;
--     BEGIN
--     SELECT * INTO l_emp_rec FROM emp where emp_no=p_emp_no
--     RETURN l_emp_rec;
--     END get_record;
    FUNCTION get_all_accounts_f RETURN ACCOUNT_ARRAY_T
        IS
        v_result ACCOUNT_ARRAY_T;
    BEGIN
        select ACCOUNT_T(
                       a.ACCOUNT_ID,
                       a.ACCOUNT_VERSION,
                       a.ACCOUNT_NAME,
                       a.ACCOUNT_NUMBER,
                       a.CURRENCY,
                       a.ASSET_LOCATION,
                       a.NOTE)
            BULK COLLECT
        INTO v_result
        from ACCOUNTS a;

        return v_result;
    end get_all_accounts_f;

    procedure get_all_accounts_p(p_accounts out ACCOUNT_ARRAY_T)
        is
        v_result ACCOUNT_ARRAY_T;

    begin
        select ACCOUNT_T(
                       a.ACCOUNT_ID,
                       a.ACCOUNT_VERSION,
                       a.ACCOUNT_NAME,
                       a.ACCOUNT_NUMBER,
                       a.CURRENCY,
                       a.ASSET_LOCATION,
                       a.NOTE)
            BULK COLLECT
        INTO v_result
        from ACCOUNTS a;

        p_accounts := v_result;
    end get_all_accounts_p;


    function dummy_fun(p_account in account_t) return account_t
        is
    begin
        return p_account;
    end;
    function dummy_array_fun(p_accounts in ACCOUNT_ARRAY_T) return ACCOUNT_ARRAY_T
        is
    begin
        return p_accounts;
    end;
    procedure dummy_proc(p_in_account in ACCOUNT_T, p_out_account out ACCOUNT_T)
        is
    begin
        p_out_account := p_in_account;
    end;

    procedure dummy_array_proc(p_in_accounts in ACCOUNT_ARRAY_T, p_out_accounts out ACCOUNT_ARRAY_T)
        is
    begin
        p_out_accounts := p_in_accounts;
    end;
    BEGIN
    dbms_output.put_line('Control is now executing the package initialization part');
END static_data;
/