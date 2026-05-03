-- Drop and recreate the auth schema for a clean slate on each test run
DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;

-- A user of the application
CREATE TABLE IF NOT EXISTS auth.app_user (
    internal_id   bigint        PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    external_id   uuid          NOT NULL,
    email_address varchar(100)  NOT NULL,
    first_name    varchar(45)   NOT NULL,
    last_name     varchar(45)   NOT NULL,
    -- Audit fields
    created_at    timestamptz   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamptz   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_app_user_external_id UNIQUE (external_id),
    CONSTRAINT uq_app_user_email       UNIQUE (email_address),
    CONSTRAINT chk_email_not_empty     CHECK (email_address <> ''),
    CONSTRAINT chk_first_name_not_empty CHECK (first_name <> ''),
    CONSTRAINT chk_last_name_not_empty  CHECK (last_name <> '')
);