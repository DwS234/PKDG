CREATE TABLE words (
    id BIGSERIAL PRIMARY KEY,
    word_id VARCHAR(255) UNIQUE NOT NULL,
    entry varchar(255) NOT NULL,
    definition varchar(255) NOT NULL
);

CREATE UNIQUE INDEX words_entry_definition_uidx ON words (UPPER(entry), UPPER(definition));

CREATE TABLE word_in_sentences (
    id BIGSERIAL PRIMARY KEY,
    sentence varchar(255) NOT NULL,
    word_id integer REFERENCES words(id)
);

CREATE UNIQUE INDEX word_in_sentences_word_id_sentence_uidx ON word_in_sentences (word_id, UPPER(sentence));

CREATE TABLE users (
    username varchar(255) PRIMARY KEY,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    enabled smallint NOT NULL DEFAULT 1
);

CREATE UNIQUE INDEX users_email_uidx ON users (email);

CREATE TABLE authorities (
  username VARCHAR(255) REFERENCES users(username),
  authority VARCHAR(50) NOT NULL
);

CREATE UNIQUE index authorities_username_authority_uidx on authorities (username,authority);

CREATE TABLE repetitions (
    id BIGSERIAL PRIMARY KEY,
    repetition_id VARCHAR(255) UNIQUE NOT NULL,
    next_date date NOT NULL,
    easiness decimal(10, 2) NOT NULL DEFAULT '2.00',
    consecutive_correct_answers integer NOT NULL DEFAULT '0',
    username varchar(255) REFERENCES users,
    word_id integer REFERENCES words,
    times_seen integer NOT NULL DEFAULT '0',
    last_interval integer NOT NULL DEFAULT '0'
);

CREATE UNIQUE INDEX repetitions_word_user_uidx ON repetitions (username, word_id);
