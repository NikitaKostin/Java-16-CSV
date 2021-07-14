ALTER TABLE history
	ADD type text;

alter table history
	add person_id bigint;

CREATE OR REPLACE FUNCTION trigger_func()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO history(name, add, phones, type, person_id)
        VALUES (NEW.name, NEW.name, NEW.phones, 'insert', NEW.id);
    END IF;

    IF TG_OP = 'DELETE' THEN
        INSERT INTO history(name, add, phones, type, person_id)
        VALUES (OLD.name, OLD.name, OLD.phones, 'deleted', OLD.id);
    END IF;

RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';


CREATE TRIGGER view_trigger
    AFTER INSERT OR DELETE
    ON person
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_func();

UPDATE person SET add = add || ' del fired' WHERE id % 3 = 0;