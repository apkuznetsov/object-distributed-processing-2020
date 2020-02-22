-- отображения списка рецептов
select * from medical_prescription;

-- добавление
insert into medical_prescription
(description, patient_id, doctor_id, creation_date, validity_date, priority)
VALUES ('ОПИСАНИЕ', 0, 0, CURRENT_TIMESTAMP, TIMESTAMP '2020-04-30 23:59:59', 3);

-- редактирование
update medical_prescription
set
    description = 'ОПИСАНИЕ',
    patient_id = 0,
    doctor_id = 0,
    creation_date = TIMESTAMP '2020-01-01 00:00:00',
    validity_date = TIMESTAMP '2020-04-30 23:59:59',
    priority = 3
where id = 0;

-- удаление
delete from medical_prescription where id = 0;

-- фильтрация списка рецептов по описанию, приоритету и пациенту
select * from medical_prescription
where
lower(description) like lower('%ОПИСАНИЕ%') and
patient_id = 0 and
priority = 3;
