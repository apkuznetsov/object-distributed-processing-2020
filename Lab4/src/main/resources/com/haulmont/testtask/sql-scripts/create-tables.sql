create database bookstore;

use bookstore;

CREATE TABLE book (
  id BIGINT IDENTITY NOT NULL,
  description VARCHAR(1000) NOT NULL,,
  author_id BIGINT NOT NULL,
);

CREATE TABLE author (
  id BIGINT NOT NULL IDENTITY,
  forename VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NULL
);

ALTER TABLE book
	ADD CONSTRAINT pk_book PRIMARY KEY CLUSTERED (id ASC)
go

ALTER TABLE author
	ADD CONSTRAINT pk_author PRIMARY KEY CLUSTERED (id ASC)
go
