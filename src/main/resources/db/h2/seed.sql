DELETE FROM enrollments;
DELETE FROM students;
DELETE FROM courses;

INSERT INTO students (id, first_name, last_name, email, study_year) VALUES
(1, 'Anna', 'Novakova', 'anna.novakova@school.cz', 1),
(2, 'Petr', 'Svoboda', 'petr.svoboda@school.cz', 2),
(3, 'Lucie', 'Dvorakova', 'lucie.dvorakova@school.cz', 3);

INSERT INTO courses (id, code, title, teacher_name, credits) VALUES
(1, 'WEB101', 'Webove technologie', 'Ing. Jan Vesely', 5),
(2, 'JAVA201', 'Java backend', 'Mgr. Petra Kralova', 6),
(3, 'DB150', 'Databazove systemy', 'Ing. Tomas Hruby', 4);

INSERT INTO enrollments (id, student_id, course_id, enrolled_at, grade) VALUES
(1, 1, 1, '2026-02-12', 'IN_PROGRESS'),
(2, 2, 2, '2026-02-13', 'B'),
(3, 3, 3, '2026-02-14', 'A');

ALTER TABLE students ALTER COLUMN id RESTART WITH 100;
ALTER TABLE courses ALTER COLUMN id RESTART WITH 100;
ALTER TABLE enrollments ALTER COLUMN id RESTART WITH 100;
