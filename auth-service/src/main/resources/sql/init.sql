create table auth_client_details
(
    id                     int          not null
        primary key,
    access_token_validity  int          null,
    additional_information varchar(255) null,
    client_id              varchar(255) null,
    client_secret          varchar(255) null,
    grant_types            varchar(255) null,
    redirect_uris          varchar(255) null,
    refresh_token_validity int          null,
    resources              varchar(255) null,
    scopes                 varchar(255) null
);

INSERT INTO registration_service.auth_client_details (id, access_token_validity, additional_information, client_id, client_secret, grant_types, redirect_uris, refresh_token_validity, resources, scopes) VALUES (1, null, null, 'browser', '$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK', 'refresh_token,password', null, null, null, 'ui');
INSERT INTO registration_service.auth_client_details (id, access_token_validity, additional_information, client_id, client_secret, grant_types, redirect_uris, refresh_token_validity, resources, scopes) VALUES (2, null, null, 'employee-service', '$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK', 'refresh_token,password', null, null, null, 'server');

create table user
(
    id           int auto_increment
        primary key,
    create_at    datetime     not null,
    update_at    datetime     not null,
    version      int          null,
    avatar       varchar(255) null,
    email        varchar(255) null,
    full_name    varchar(255) null,
    password     varchar(255) not null,
    phone_number varchar(255) null,
    user_social  varchar(255) null,
    username     varchar(255) not null
);

INSERT INTO registration_service.user (id, create_at, update_at, version, avatar, email, full_name, password, phone_number, user_social, username) VALUES (1, '2020-04-15 08:46:01', '2020-04-15 08:46:01', 0, 'https://www.gravatar.com/avatar/a260ee254e3517cd312688d3b90c275f?d=identicon', 'phantiensy195@gmail.com', 'Phan tien sy', '$2a$10$EZ.9MnaE.4gvgIg4KG2EUOzUTQ56BwgCGzp4LNevTCX.wrHZ/oZpG', '0366487919', null, 'syphan');

create table role
(
    id        int auto_increment
        primary key,
    create_at datetime     not null,
    update_at datetime     not null,
    version   int          null,
    code      varchar(255) not null,
    name      varchar(255) not null
);

INSERT INTO registration_service.role (id, create_at, update_at, version, code, name) VALUES (1, '2020-04-15 08:41:03', '2020-04-15 08:41:03', 0, 'ROLE_USER', 'User');
INSERT INTO registration_service.role (id, create_at, update_at, version, code, name) VALUES (2, '2020-04-15 08:41:17', '2020-04-15 08:41:17', 0, 'ROLE_SUPER_ADMIN', 'User');

create table users_roles
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint FKgd3iendaoyh04b95ykqise6qh
        foreign key (user_id) references user (id),
    constraint FKt4v0rrweyk393bdgt107vdx0x
        foreign key (role_id) references role (id)
);

INSERT INTO registration_service.users_roles (user_id, role_id) VALUES (1, 2);

create table permission
(
    id        int auto_increment
        primary key,
    create_at datetime     not null,
    update_at datetime     not null,
    version   int          null,
    code      varchar(255) not null,
    name      varchar(255) not null
);

INSERT INTO registration_service.permission (id, create_at, update_at, version, code, name) VALUES (1, '2020-04-15 08:39:09', '2020-04-15 08:39:09', 0, 'UPMS_PERMISSION_EDIT', 'Edit Permission');
INSERT INTO registration_service.permission (id, create_at, update_at, version, code, name) VALUES (2, '2020-04-15 08:39:41', '2020-04-15 08:39:41', 0, 'UPMS_ROLE_READ', 'Read Role');


create table roles_permissions
(
    role_id       int not null,
    permission_id int not null,
    primary key (role_id, permission_id),
    constraint FK4hrolwj4ned5i7qe8kyiaak6m
        foreign key (role_id) references role (id),
    constraint FKboeuhl31go7wer3bpy6so7exi
        foreign key (permission_id) references permission (id)
);

INSERT INTO registration_service.roles_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO registration_service.roles_permissions (role_id, permission_id) VALUES (2, 2);