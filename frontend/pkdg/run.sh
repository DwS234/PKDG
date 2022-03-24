#!/usr/bin/env bash

export $(cat .env | xargs)

npm start
