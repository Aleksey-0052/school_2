-JOIN-запрос о получении информации обо всех студентах (имя и возраст студента) школы Хогвартс вместе
с названиями факультетов.

select students.name, students.age, faculties.name
from students
inner join faculties on students.faculty_id = faculties.id;

select s.name, s.age, f.name
from students s
join faculties f on s.faculty_id = f.id;


- JOIN-запрос о получении только тех студентов, у которых есть аватарки.

select *
from students s
inner join avatar a on s.id = a.student_id;

select *
from students s
join avatar a on s.id = a.student_id
where a.student_id is not null;