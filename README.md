# orcl-tst

how to create oracle database docker image

    https://www.oracle.com/br/technical-resources/articles/database-performance/oracle-db19c-com-docker.html




get all accounts from database: 

    curl -i -v "http://localhost:8080"

create new account:

    curl -i \
    -H "Accept: application/json" \
    -H "Content-Type:application/json" \
    -X POST --data '{"name":"awesome account","number":"1122","currency":"XAU","loco":"LDN", "note":"test account"}' "http://localhost:8080/create"
