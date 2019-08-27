create table springdb.hibernate_sequence (next_val bigint);
insert into springdb.hibernate_sequence values ( 1 );
insert into springdb.hibernate_sequence values ( 1 );
create table springdb.message(
    id bigint not null,
    filename varchar(255),
    message_text varchar(2048),
    tag varchar(255),
    user_id bigint,
    primary key (id));

create table springdb.user_role (
    user_id bigint not null,
    roles varchar(255));

create table springdb.usr (
    id bigint not null,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id));

alter table springdb.message
            add constraint message_user_fk
            foreign key (user_id) references usr (id);
alter table springdb.user_role
            add constraint user_role_user_fk
            foreign key (user_id) references usr (id);