CREATE EXTENSION IF NOT EXISTS dblink;

CREATE OR REPLACE function create_role_if_not_exists(rname TEXT, secret TEXT) RETURNS void as
$BODY$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE  rolname = rname) THEN
BEGIN
EXECUTE format(
        'CREATE ROLE %I LOGIN PASSWORD %L',
        rname,
        secret
    );
RAISE NOTICE 'role ''%'' created', rname;
EXCEPTION WHEN DUPLICATE_OBJECT THEN
            RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END;
END IF;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE function create_database_if_not_exists(dbowner TEXT, dbname TEXT) RETURNS void as
$BODY$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = dbname) THEN
BEGIN
        PERFORM dblink_exec('dbname=' || current_database(),
                            format('CREATE DATABASE %I WITH OWNER=%L',
                                        dbname,
                                        dbowner));
END;
END IF;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE function create_schema_in_db_if_not_exists(dbname TEXT, schname TEXT) RETURNS void as
$BODY$
BEGIN
    PERFORM dblink_exec(format('dbname=%I', dbname),
                        format('CREATE SCHEMA IF NOT EXISTS %I', schname));
END;
$BODY$
LANGUAGE plpgsql;

select create_role_if_not_exists('tz_app', 'password');
select create_database_if_not_exists('tz_app', 'tok_zhizni');
select create_schema_in_db_if_not_exists('tok_zhizni', 'api_data');