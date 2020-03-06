-- отображение списка пациентов
select * from patient;

-- добавление
insert into patient
(forename, patronymic, surname, phone)
values ('ИМЯ', 'ОТЧЕСТВО', 'ФАМИЛИЯ', '00000000000');

-- редактирование
update patient
set
    forename = 'Добрыня',
    patronymic = 'Никитич',
    surname = 'Богатырёв',
    phone = '0123456789'
where id = 0;

-- удаление
delete from patient where id = 0;

-- фильтрация списка пациентов по фамилии
select * from patient
where
lower(surname) like lower('%ОПИСАНИЕ%');