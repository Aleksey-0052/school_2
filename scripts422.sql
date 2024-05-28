
- Создание таблицы автомобилей
create table cars (
    id Integer primary key,
    make TEXT,
    model varchar,
    price real
);

- Создание таблицы с пользователями автомобилей
create table persons (
    id Integer primary key,
    name text,
    age Integer,
    licence boolean,
    car_id Integer references cars (id)
);

