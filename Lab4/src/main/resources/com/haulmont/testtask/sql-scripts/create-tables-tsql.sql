CREATE DATABASE Pharmacy;

CREATE TABLE doctor (
  id BIGINT IDENTITY NOT NULL,
  forename VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  specialization_id BIGINT NOT NULL,
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

ALTER TABLE doctor
	ADD CONSTRAINT pk_doctor PRIMARY KEY  CLUSTERED (id ASC)
go

ALTER TABLE doctor_specialization
	ADD CONSTRAINT pk_doctor_specialization PRIMARY KEY  CLUSTERED (id ASC)
go

ALTER TABLE medical_prescription
	ADD CONSTRAINT pk_medical_prescription PRIMARY KEY  CLUSTERED (id ASC)
go

ALTER TABLE patient
	ADD CONSTRAINT pk_patient PRIMARY KEY  CLUSTERED (id ASC)
go

ALTER TABLE doctor
	ADD CONSTRAINT fk_specialization_id FOREIGN KEY (specialization_id) REFERENCES doctor_specialization (id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE  medical_prescription
    	ADD CONSTRAINT fk_patient_id FOREIGN KEY (patient_id) REFERENCES patient (id)
    		ON DELETE NO ACTION
    		ON UPDATE NO ACTION
go

ALTER TABLE  medical_prescription
    	ADD CONSTRAINT fk_doctor_id FOREIGN KEY (patient_id) REFERENCES doctor (id)
    		ON DELETE NO ACTION
    		ON UPDATE NO ACTION
go

CREATE TRIGGER inserting_doctor
    ON doctor
	FOR UPDATE
	AS
	BEGIN
		DECLARE
		@id	BIGINT,
		@specialization_id BIGINT;

		SELECT
		@id	= id,
		@specialization_id = specialization_id
		FROM INSERTED;

        UPDATE doctor SET specialization_id = (SELECT id FROM doctor_specialization WHERE id = @specialization_id) WHERE id = @id;
	END;
go

CREATE TRIGGER updating_doctor
    ON doctor
    FOR UPDATE
    AS
    BEGIN
        DECLARE
		@id	BIGINT,
		@specialization_id BIGINT;

		SELECT
		@id	= id,
		@specialization_id = specialization_id
		FROM INSERTED;

        UPDATE doctor SET specialization_id = (SELECT id FROM doctor_specialization WHERE id = @specialization_id) WHERE id = @id;
    END
go

CREATE TRIGGER deleting_doctor
    ON doctor
	FOR DELETE
    AS
    BEGIN
        DECLARE
		@id	BIGINT;

		SELECT
		@id	= id
		FROM DELETED;

        IF EXISTS(select doctor_id from medical_prescription where doctor_id = @id)
            THROW 00000, 'cannot delete doctor because he has a medical prescription', 0;
    END
go

CREATE TRIGGER deleting_doctor_specialization
    ON doctor_specialization
    FOR DELETE
    AS
    BEGIN
        DECLARE
		@id	BIGINT;

		SELECT
		@id	= id
		FROM DELETED;

        IF EXISTS(select specialization_id from doctor where specialization_id = @id)
            THROW 00000, 'cannot delete specialization because there is a doctor with such one', 0;
    END
go

CREATE TRIGGER inserting_medical_prescription
    ON medical_prescription
    FOR UPDATE
    AS
    BEGIN
        DECLARE
		@id	BIGINT,
		@patient_id	BIGINT,
		@doctor_id BIGINT;

		SELECT
		@id	= id,
		@patient_id	= patient_id,
		@doctor_id = doctor_id
		FROM INSERTED;

        UPDATE medical_prescription SET patient_id = (SELECT id FROM patient WHERE id = @patient_id) WHERE id = @id;
        UPDATE medical_prescription SET doctor_id = (SELECT id FROM doctor WHERE id = @doctor_id) WHERE id = @id;
    END
go

CREATE TRIGGER updating_medical_prescription
    ON medical_prescription
    FOR UPDATE
    AS
    BEGIN
        DECLARE
		@id	BIGINT,
		@patient_id	BIGINT,
		@doctor_id BIGINT;

		SELECT
		@id	= id,
		@patient_id	= patient_id,
		@doctor_id = doctor_id
		FROM INSERTED;

        UPDATE medical_prescription SET patient_id = (SELECT id FROM patient WHERE id = @patient_id) WHERE id = @id;
        UPDATE medical_prescription SET doctor_id = (SELECT id FROM doctor WHERE id = @doctor_id) WHERE id = @id;
    END
go

CREATE TRIGGER deleting_patient
    ON patient
    FOR DELETE
    AS
    BEGIN
        DECLARE
		@id	BIGINT;

		SELECT
		@id	= id
		FROM DELETED;

        IF EXISTS(select patient_id from medical_prescription where patient_id = @id)
            THROW 00000, 'cannot delete patient because he has a medical prescription', 0;
    END
go
