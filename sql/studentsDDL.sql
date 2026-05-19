CREATE  DATABASE  IF NOT EXISTS students_back default  character set utf8mb4 collate  utf8mb4_unicode_ci;

USE students_back;

/* 用户表 */
create table if not exists users (
    id int primary key auto_increment,
    username varchar(50) not null unique,
    password varchar(255) not null,
    role varchar(20) default 'admin',
    created_time datetime default current_timestamp
);

/* 学生表 */
create table if not exists student (
    id int primary key auto_increment,
    student_number varchar(20) not null unique,
    name varchar(50) not null unique,
    gender varchar(10) default '未知',
    class_name varchar(100),
    phone varchar(20),
    email varchar(100),
    avatar varchar(255),
    created_time datetime default current_timestamp,
    updated_time datetime default current_timestamp on update current_timestamp
);

/* 课程表 */
create table if not exists course (
    id int primary key auto_increment,
    course_name varchar(50) not null,
    course_number varchar(20) not null unique,
    credit double default 0,
    teacher varchar(50) not null,
    semester int default '0',
    created_time datetime default current_timestamp
);

/* 成绩表 */
create table if not exists score (
    id int primary key auto_increment,
    student_id int not null,
    course_id int not null,
    score double default 0,
    created_time datetime default current_timestamp,
    foreign key (student_id) references student(id) on delete cascade on update cascade,
    foreign key (course_id) references course(id) on delete cascade on update cascade,
    check (score between 0 and 100),
    unique(student_id, course_id)
);
