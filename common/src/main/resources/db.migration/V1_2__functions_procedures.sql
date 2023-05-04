create function public.countsessionaccidents(session integer) returns integer
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

alter function public.countsessionaccidents(integer) owner to postgres;

create function public.selectlongestsessionduration(person_id bigint) returns timestamp without time zone
    language plpgsql
as
$$
DECLARE
    sessionDuration timestamp;
begin
    SELECT MAX(end_time - start_time)
    INTO sessionDuration
    FROM sessions
    WHERE user_id = person_id;
    RETURN sessionDuration;
end;
$$;

alter function public.selectlongestsessionduration(bigint) owner to postgres;

