CREATE  DATABASE  IF NOT EXISTS students default  character set utf8mb4 collate  utf8mb4_unicode_ci;

USE students;

-- 用户表
create table if not exists users (
    id int primary key auto_increment, -- 自增主键
    username varchar(50) not null unique,-- 用户名 不空 不重
    password varchar(255) not null , -- 密码 不空
    role varchar(20) default 'admin', -- 角色 admin
    created_at datetime default current_timestamp -- 创建的时间戳
);

-- 学生表
create table if not exists student (
    id int primary key auto_increment,
    stu_number varchar(20) not null unique, -- 学号
    stu_name varchar(50) not null unique, -- 姓名
    gender varchar(10) default '未知', -- 性别
    class_name varchar(100),
    phone varchar(20),
    email varchar(100),
    avatar varchar(255) , -- 学生头像
    created_at datetime default  current_timestamp, -- 创建时间
    updated_at datetime default current_timestamp on update current_timestamp -- 更新时间
);

-- 课程表
create table if not exists course (
    id int primary key auto_increment,
    course_name varchar(50) not null, -- 课程名称
    course_number varchar(20) not null unique, -- 课程编号
    credit int default 0, -- 学分 默认0
    teacher varchar(50) not null, -- 教师
    semester varchar(10) default '0', -- 学期 默认0
    created_at datetime default current_timestamp -- 创建时间
);

-- 成绩表
create table if not exists score (
    id int primary key auto_increment,
    student_id int not null, -- 学生id（关联student表的id）
    course_id int not null, -- 课程id（关联course表的id）
    score int default 0, -- 成绩 默认0
    created_at datetime default current_timestamp, -- 创建时间
    foreign key (student_id) references student(id) on delete cascade on update cascade,
    foreign key (course_id) references course(id) on delete cascade on update cascade,
    check (score between 0 and 100),
    unique(student_id, course_id)
);
