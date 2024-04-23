1. Получить всех студентов, возраст которых находится между 10 и 20 (можно подставить любые числа, главное, чтобы нижняя
граница была меньше верхней).

select *
from students
where age > 18 and age < 24;

select *
from students
where age between 23 and 26;

2. Получить всех студентов, но отобразить только список их имен.

select name
from students;

3. Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).

select *
from students
where name like '%а%';

4. Получить всех студентов, у которых возраст меньше идентификатора.

select *
from student
where age < id;

5. Получить всех студентов, упорядоченных по возрасту.

select *
from students
ORDER BY age;