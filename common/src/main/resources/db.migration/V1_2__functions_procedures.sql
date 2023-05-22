-- +flyway-repeatability=always
-- +flyway-allow-unsupported-migration-types=true
create or replace function countsessionaccidents(session bigint) returns integer
    language plpgsql
as
$$
DECLARE
    sessionCount int;
begin
    SELECT count(session_id)
    INTO sessionCount
    FROM accidents
    WHERE session_id = session;
    RETURN sessionCount;
end;
$$;

alter function countsessionaccidents(bigint) owner to postgres;


create or replace function selectlongestsessionduration(person_id bigint) returns timestamp without time zone
    language plpgsql
as
$$
DECLARE
    session_duration interval;
    session_end      timestamp;
BEGIN
    SELECT MAX(end_time - start_time)
    INTO session_duration
    FROM sessions
    WHERE user_id = person_id;

    session_end := '1970-01-01'::timestamp + session_duration;

    RETURN session_end;
END;
$$;

alter function selectlongestsessionduration(bigint) owner to postgres;

