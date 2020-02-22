CREATE TABLE doctor (
  id BIGINT NOT NULL IDENTITY,
  forename VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NULL,
  specialization_id BIGINT NOT NULL
);

CREATE TABLE doctor_specialization (
  id BIGINT NOT NULL IDENTITY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE medical_prescription (
  id BIGINT NOT NULL IDENTITY,
  description VARCHAR(1000) NOT NULL,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  creation_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  validity_date DATETIME NOT NULL,
  priority TINYINT DEFAULT 3 NOT NULL CHECK(priority > 0 AND priority < 4)
);

CREATE TABLE patient (
  id BIGINT NOT NULL IDENTITY,
  forename VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  phone CHAR(11) NOT NULL
);

ALTER TABLE doctor ADD FOREIGN KEY (specialization_id) REFERENCES doctor_specialization (id);
ALTER TABLE medical_prescription ADD FOREIGN KEY (patient_id) REFERENCES patient (id);
ALTER TABLE medical_prescription ADD FOREIGN KEY (doctor_id) REFERENCES doctor (id);

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

CREATE trigger deleting_doctor
    BEFORE DELETE on doctor
    REFERENCING OLD ROW AS doctor_to_delete
    FOR EACH ROW
    BEGIN ATOMIC
        IF EXISTS(select doctor_id from medical_prescription where doctor_id = doctor_to_delete.id)
            THEN SIGNAL SQLSTATE 'HY008' SET MESSAGE_TEXT = 'cannot delete doctor because he has a medical prescription';
        END IF;
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

CREATE trigger inserting_medical_prescription
    BEFORE INSERT ON medical_prescription
    REFERENCING NEW ROW AS new_medical_prescription
    FOR EACH ROW
    BEGIN ATOMIC
        SET new_medical_prescription.patient_id = (SELECT id FROM patient WHERE id = new_medical_prescription.patient_id);
        SET new_medical_prescription.doctor_id = (SELECT id FROM doctor WHERE id = new_medical_prescription.doctor_id);
    END

CREATE trigger updating_medical_prescription
    BEFORE UPDATE ON medical_prescription
    REFERENCING NEW ROW AS new_medical_prescription
    FOR EACH ROW
    BEGIN ATOMIC
        SET new_medical_prescription.patient_id = (SELECT id FROM patient WHERE id = new_medical_prescription.patient_id);
        SET new_medical_prescription.doctor_id = (SELECT id FROM doctor WHERE id = new_medical_prescription.doctor_id);
    END

CREATE trigger inserting_patient
    BEFORE INSERT ON patient
    REFERENCING NEW ROW AS new_patient
    FOR EACH ROW
    BEGIN ATOMIC
        IF NOT (REGEXP_MATCHES(new_patient.phone, '[0-9]{1,11}'))
            THEN SIGNAL SQLSTATE 'HY008' SET MESSAGE_TEXT = 'wrong phone number';
        END IF;
    END

CREATE trigger deleting_patient
    BEFORE DELETE on patient
    REFERENCING OLD ROW AS patient_to_delete
    FOR EACH ROW
    BEGIN ATOMIC
        IF EXISTS(select patient_id from medical_prescription where patient_id = patient_to_delete.id)
            THEN SIGNAL SQLSTATE 'HY008' SET MESSAGE_TEXT = 'cannot delete patient because he has a medical prescription';
        END IF;
    END