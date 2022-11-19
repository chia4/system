create schema system_database;

use system_database;

create table user_login
(
    username      char(20)                           not null,
    password_hash char(64)                           not null,
    type          enum ('PEOPLE', 'PLACE', 'AGENCY') not null
);