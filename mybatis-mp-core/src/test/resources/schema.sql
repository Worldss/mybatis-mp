CREATE TABLE IF NOT EXISTS t_sys_user
(
    _id   INTEGER PRIMARY KEY auto_increment,
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


insert into t_sys_user
values
    (1,'admin','123',0,'2023-10-10 10:10:10'),
    (2,'test1','123456',1,'2023-10-11 10:10:10'),
    (3,'test2',null,1,'2023-10-12 10:10:10');

insert into sys_role
values
    (1,'测试','2022-10-10'),
    (2,'运维','2022-10-10');