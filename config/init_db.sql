DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS sections;
DROP TABLE IF EXISTS resume;

CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name VARCHAR              NOT NULL
);

CREATE TABLE contacts
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume
        ON UPDATE RESTRICT ON DELETE CASCADE,
    type        VARCHAR  NOT NULL,
    value       VARCHAR  NOT NULL
);

create unique index contacts_uuid_type_index
    on contacts (resume_uuid, type);

CREATE TABLE sections
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume
        ON UPDATE RESTRICT ON DELETE CASCADE,
    type        VARCHAR  NOT NULL,
    value       VARCHAR  NOT NULL

);

CREATE UNIQUE INDEX sections_uuid_type_index
    ON sections (resume_uuid, type);

GRANT ALL PRIVILEGES ON DATABASE resumes TO postgres;