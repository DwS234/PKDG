CREATE TABLE word (
    word_id integer PRIMARY KEY,
    entry varchar(255) NOT NULL,
    definition varchar(255) NOT NULL
);

CREATE UNIQUE INDEX word_entry_definition_uidx ON word (UPPER(entry), UPPER(definition));

CREATE TABLE word_in_sentence (
    word_example_id integer PRIMARY KEY,
    sentence varchar(255) NOT NULL,
    word_id integer REFERENCES word
);

CREATE UNIQUE INDEX word_in_sentence_word_id_sentence_uidx ON word_in_sentence (word_id, UPPER(sentence));

CREATE TABLE app_user (
    user_id integer PRIMARY KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL
);

CREATE UNIQUE INDEX user_username_uidx ON app_user (username);
CREATE UNIQUE INDEX user_email_uidx ON app_user (email);

CREATE TABLE repetition (
    repetition_id integer PRIMARY KEY,
    next_date date NOT NULL,
    easiness decimal(10, 2) NOT NULL DEFAULT '2.00',
    consecutive_correct_answers integer NOT NULL DEFAULT '0',
    user_id integer REFERENCES app_user,
    word_id integer REFERENCES word,
    times_seen integer NOT NULL DEFAULT '0',
    last_interval integer NOT NULL DEFAULT '0'
);

CREATE UNIQUE INDEX repetition_word_user_uidx ON repetition (user_id, word_id);

