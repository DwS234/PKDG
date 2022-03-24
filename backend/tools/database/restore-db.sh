#!/usr/bin/env bash

readonly TABLES_TO_RESTORE="--table=users --table=authorities --table=words --table=word_in_sentences --table=repetitions"

pg_restore --host=localhost --port=5432 --dbname=pkdg --username=pkdg $TABLES_TO_RESTORE pkdg.backup
