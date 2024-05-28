Устанавливаем ограничения:

1. Возраст студента не может быть меньше 16 лет
alter table students add constraint age_constraint check (age >= 16);

- Выполнение проверки
insert into students (id, age, name, faculty_id) values (11, 15, 'Никитин', 1);

2. Имена студентов должны быть уникальными и не равны нулю
alter table students
alter column name set not null,
add constraint name_unique unique (name);

- Выполнение проверки
insert into students (id, age, name, faculty_id) values (11, 20, null, 1);
insert into students (id, age, name, faculty_id) values (11, 20, 'Захаров', 1);

- Команда "Первичный ключ" - группирует в себе две проверки
alter table students add primary key (name);

3. Пара “значение названия” - “цвет факультета” должна быть уникальной
alter table faculties add constraint name_color_unique unique (name, color);

- Выполнение проверки
insert into faculties (id, name, color) values (5, 'Slizerin', 'Blue');

4. При создании студента без возраста ему автоматически должно присваиваться 20 лет
alter table students alter column age set default 20;

- Выполнение проверки
insert into students (id, name, faculty_id) values (15, 'Васильев', 4);