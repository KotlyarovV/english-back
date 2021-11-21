CREATE TABLE IF NOT EXISTS word (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    english_word varchar(100)
)