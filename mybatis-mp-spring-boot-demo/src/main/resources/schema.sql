CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`   INTEGER PRIMARY KEY auto_increment,
    `name` VARCHAR(100) comment '系统用户',
    `role_id` INTEGER NOT NULL,
    `create_time`  DATETIME NOT NULL DEFAULT NOW()
) ;


CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`   INTEGER PRIMARY KEY auto_increment,
    `name` VARCHAR(100),
    `create_time`  DATETIME NOT NULL DEFAULT NOW()
);
