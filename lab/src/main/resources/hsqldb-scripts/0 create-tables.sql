CREATE TABLE doctor (
  id BIGINT NOT NULL IDENTITY,
  forename VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  specialization_id BIGINT NOT NULL
);

CREATE TABLE doctor_specialization (
  id BIGINT NOT NULL IDENTITY,
  name VARCHAR(100) NOT NULL
);

ALTER TABLE doctor ADD FOREIGN KEY (specialization_id) REFERENCES doctor_specialization (id);

CREATE trigger inserting_doctor
    BEFORE INSERT ON doctor
    REFERENCING NEW ROW AS new_doctor
    FOR EACH ROW
    BEGIN ATOMIC
        SET new_doctor.specialization_id = (SELECT id FROM doctor_specialization WHERE id = new_doctor.specialization_id);
    END

CREATE trigger updating_doctor
    BEFORE UPDATE ON doctor
    REFERENCING NEW ROW AS new_doctor
    FOR EACH ROW
    BEGIN ATOMIC
        SET new_doctor.specialization_id = (SELECT id FROM doctor_specialization WHERE id = new_doctor.specialization_id);
    END


CREATE trigger deleting_doctor_specialization
    BEFORE DELETE on doctor_specialization
    REFERENCING OLD ROW AS specialization_to_delete
    FOR EACH ROW
    BEGIN ATOMIC
        IF EXISTS(select specialization_id from doctor where specialization_id = specialization_to_delete.id)
            THEN SIGNAL SQLSTATE 'HY008' SET MESSAGE_TEXT = 'cannot delete specialization because there is a doctor with such one';
        END IF;
    END