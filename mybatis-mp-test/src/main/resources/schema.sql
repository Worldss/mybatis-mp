CREATE TABLE IF NOT EXISTS `student`
(
    `id`   INTEGER PRIMARY KEY auto_increment,
    `name` VARCHAR(100),
    `excellent` BOOL,
    `create_time`  DATETIME NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS `achievement`
(
    `id`   INTEGER PRIMARY KEY auto_increment,
    `student_id` INTEGER not null,
    `score` decimal(5,2),
    `create_time`  DATETIME NOT NULL DEFAULT NOW()
);
