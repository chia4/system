create schema system_database;

use system_database;

create table user_login
(
    username      char(20)                           not null,
    password_hash char(64)                           not null,
    type          enum ('PEOPLE', 'PLACE', 'AGENCY') not null,
    constraint user_login_pk
        primary key (username)
);

create table people_table
(
    username         char(20)        not null,
    full_name        char(20)        not null,
    phone_number     char(20)        not null,
    id_card_number   char(20)        not null,
    user_gender      enum ('F', 'M') not null,
    green_code_after int default 0   not null comment '该值表示时间戳，含义是这个时间后由红码回复绿码，初始值为0',
    constraint people_table_pk
        primary key (username),
    constraint people_table_user_login_username_fk
        foreign key (username) references user_login (username)
);

create unique index people_table_id_card_number_uindex
    on people_table (id_card_number);

create table agency_table
(
    username       char(20) not null,
    agency_name    char(20) not null,
    agency_address char(40) not null,
    constraint agency_table_pk
        primary key (username),
    constraint agency_table_user_login_username_fk
        foreign key (username) references user_login (username)
);

create unique index agency_table_agency_name_uindex
    on agency_table (agency_name);

create table covid_test_authorization
(
    people_username    char(20) not null,
    agency_username    char(20) not null,
    authorization_time int      not null comment '授权时间戳',
    constraint covid_test_authorization_pk
        primary key (people_username, agency_username, authorization_time),
    constraint covid_test_authorization_agency_table_username_fk
        foreign key (agency_username) references agency_table (username),
    constraint covid_test_authorization_people_table_username_fk
        foreign key (people_username) references people_table (username)
)
    comment '核酸授权表';


create table covid_test_result
(
    people_username char(20)                      not null,
    agency_username char(20)                      not null,
    result          enum ('POSITIVE', 'NEGATIVE') not null,
    result_time     int                           not null comment '检测结果时间戳',
    constraint covid_test_result_pk
        primary key (people_username, agency_username, result_time),
    constraint covid_test_result_agency_table_username_fk
        foreign key (agency_username) references agency_table (username),
    constraint covid_test_result_people_table_username_fk
        foreign key (people_username) references people_table (username)
)
    comment '核酸结果表';
