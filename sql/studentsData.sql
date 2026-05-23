use students;

-- 用户数据
insert into users (username, password, role) values
    ('admin', '123456', 'admin');

-- 学生数据
insert into student (student_number, name, gender, class_name, phone, email) values
                                                                                 ('202501', '聪明草', '女', '计算机1班', '13800000001', 'smartgrass@test.com'),
                                                                                 ('202502', '李华', '男', '计算机1班', '13800000002', 'lihua@test.com');

-- 课程数据
insert into course (course_name, course_number, credit, teacher, semester) values
                                                                               ('高等数学', '108', 5.0, '刘教授', 1),
                                                                               ('线性代数', '107', 5.0, '卢教授', 1);

-- 成绩数据
insert into score (student_id, course_id, score) values
                                                     (1, 1, 85),
                                                     (1, 2, 90),
                                                     (2, 1, 78);