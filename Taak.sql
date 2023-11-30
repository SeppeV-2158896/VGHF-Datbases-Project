--Drop all tables
DROP TABLE IF EXISTS consoles;
DROP TABLE IF EXISTS dev_companies;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS loan_receips;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS users;


--CREATE Tables
CREATE TABLE "consoles" (
	"consoleID"	integer NOT NULL,
	"consoleName"	varchar(255) NOT NULL,
	"consoleType"	varchar(255) NOT NULL,
	"company"	varchar(255) NOT NULL,
	"releasedYear"	datetime NOT NULL,
	"discontinuationYear"	datetime,
	"unitsSoldMillion"	REAL,
	"remarks"	varchar(255)
);

CREATE TABLE "dev_companies" (
	"devID"	integer NOT NULL,
	"companyName"	varchar(255) NOT NULL,
	"website"	varchar(255),
	"supportEmail"	varchar(255),
	"establishedDate"	datetime,
	"streetname"	varchar(255),
	"houseNumber"	integer,
	"bus"	varchar(255),
	"postalCode"	varchar(255),
	"city"	varchar(255),
	"country"	varchar(255)
);

CREATE TABLE "games" (
	"gameID"	integer NOT NULL,
	"ownerID"	integer NOT NULL,
	"homeBaseID"	integer NOT NULL,
	"currentLocationId"	integer NOT NULL,
	"title"	varchar(255) NOT NULL,
	"releaseDate"	datetime NOT NULL
);

CREATE TABLE "genres" (
	"genreID"	integer NOT NULL,
	"name"	varchar(255) NOT NULL
);

CREATE TABLE "loan_receips" (
	"referenceID"	integer NOT NULL,
	"gameID"	INTEGER NOT NULL,
	"userID"	INTEGER NOT NULL,
	"loanedDate"	datetime NOT NULL,
	"returnDate"	datetime,
	"fine"	REAL,
	"state"	varchar(255)
);

CREATE TABLE "locations" (
	"locationID"	integer NOT NULL,
	"ownerID"	integer NOT NULL,
	"streetname"	varchar(255) NOT NULL,
	"houseNumber"	integer NOT NULL,
	"bus"	varchar(255),
	"postalCode"	varchar(255) NOT NULL,
	"city"	varchar(255) NOT NULL,
	"country"	varchar(255) NOT NULL,
	"locationType"	varchar(255)
);

CREATE TABLE "users" (
	"userID"	integer NOT NULL,
	"firstname"	varchar(255) NOT NULL,
	"lastname"	varchar(255) NOT NULL,
	"streetname"	varchar(255),
	"houseNumber"	integer,
	"bus"	varchar(255),
	"postalCode"	varchar(255),
	"city"	varchar(255),
	"country"	varchar(255),
	"telephone"	INTEGER,
	"email"	varchar(255),
	"password"	varchar(255),
	"userType"	varchar(255)
);