ALTER TABLE IF EXISTS users DROP CONSTRAINT users_pkey;
ALTER TABLE IF EXISTS users ADD COLUMN id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;