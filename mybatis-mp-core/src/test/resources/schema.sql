CREATE TABLE IF NOT EXISTS t_sys_user
(
    id   INTEGER PRIMARY KEY auto_increment,
    user_name VARCHAR(100),
    password VARCHAR(100),
    role_id  INTEGER,
    create_time  DATETIME NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS sys_role
(
    id   INTEGER PRIMARY KEY auto_increment,
    name VARCHAR(100) not null,
    create_time  DATETIME NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS sys_user_score
(
    user_id   INTEGER PRIMARY KEY,
    score decimal(6,2)
);

insert into t_sys_user
values
    (1,'admin','123',0,'2023-10-10 10:10:10'),
    (2,'test1','123456',1,'2023-10-11 10:10:10'),
    (3,'test2',null,1,'2023-10-12 10:10:10');

insert into sys_role
values
    (1,'测试','2022-10-10'),
    (2,'运维','2022-10-10');


insert into sys_user_score
values(2,3.2),(3,2.6);



CREATE TABLE IF NOT EXISTS id_test
(
    id   BIGINT PRIMARY KEY auto_increment,
    create_time  DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS version_test
(
    id   varchar(32) PRIMARY KEY,
    version INT NOT NULL,
    name VARCHAR(100) not null,
    create_time  DATETIME NOT NULL DEFAULT NOW()
);