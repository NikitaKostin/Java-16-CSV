CREATE PROCEDURE insert_data() AS
$$

DECLARE

    i INTEGER := 0;

BEGIN
    CREATE TABLE person
    (
        id     BIGSERIAL NOT NULL PRIMARY KEY,
        name   TEXT,
        add    TEXT,
        phones TEXT[]
    );
    WHILE i < 50
        LOOP
            i := i + 1;
            insert into person(name, add, phones)
            values (concat('name', i::text), 'init',
                    CASE
                        WHEN i % 3 = 0
                            THEN ARRAY [repeat('1', 3) || '-' || repeat('2', 3) || '-' || repeat('3', 3)]
                        WHEN i % 3 = 1
                            THEN ARRAY [repeat('1', 3) || '-' || repeat('2', 3) || '-' || repeat('3', 3), repeat('4', 3) || '-' || repeat('5', 3) || '-' || repeat('6', 3)]
                        ELSE ARRAY [repeat('1', 3) || '-' || repeat('2', 3) || '-' || repeat('3', 3), repeat('4', 3) || '-' || repeat('5', 3) || '-' || repeat('6', 3), repeat('7', 3) || '-' || repeat('8', 3) || '-' || repeat('9', 3)] END);
        END LOOP;
    CREATE TABLE history
    (
        id     BIGSERIAL PRIMARY KEY,
        name   TEXT,
        add    TEXT,
        phones TEXT[]
    );

END;

$$
    LANGUAGE plpgsql;

CALL insert_data();