-- liquibase formatted sql

-- changeset black2swan:1
CREATE TABLE notification_task (
                       id SERIAL PRIMARY KEY,
                       chatID int,
                       date TIMESTAMP,
                       notification TEXT
);



