BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "dev_companies" (
	"devID"	INTEGER NOT NULL UNIQUE,
	"companyName"	TEXT NOT NULL,
	"website"	TEXT,
	"supportEmail"	TEXT,
	"establishedDate"	datetime,
	"streetname"	TEXT,
	"houseNumber"	INTEGER,
	"bus"	TEXT,
	"postalCode"	TEXT,
	"city"	TEXT,
	"country"	TEXT,
	PRIMARY KEY("devID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "games" (
	"gameID"	INTEGER NOT NULL UNIQUE,
	"ownerID"	integer NOT NULL,
	"homeBaseID"	integer NOT NULL,
	"currentLocationId"	integer NOT NULL,
	"title"	TEXT NOT NULL,
	"releaseDate"	datetime,
	"genre"	TEXT,
	PRIMARY KEY("gameID" AUTOINCREMENT),
	FOREIGN KEY("homeBaseID") REFERENCES "locations"("locationID"),
	FOREIGN KEY("ownerID") REFERENCES "users"("userID"),
	FOREIGN KEY("currentLocationId") REFERENCES "locations"("locationID")
);
CREATE TABLE IF NOT EXISTS "locations" (
	"locationID"	INTEGER NOT NULL UNIQUE,
	"ownerID"	integer NOT NULL,
	"streetname"	TEXT NOT NULL,
	"houseNumber"	integer NOT NULL,
	"bus"	TEXT,
	"postalCode"	TEXT NOT NULL,
	"city"	TEXT NOT NULL,
	"country"	TEXT NOT NULL,
	"locationType"	TEXT NOT NULL CHECK("locationType" IN ("PRIVATE", "MUSEUM", "EXPO", "LIBRARY", "STORAGE")),
	PRIMARY KEY("locationID" AUTOINCREMENT),
	FOREIGN KEY("ownerID") REFERENCES "users"("userID")
);
CREATE TABLE IF NOT EXISTS "users" (
	"userID"	INTEGER NOT NULL UNIQUE,
	"firstname"	TEXT NOT NULL,
	"lastname"	TEXT NOT NULL,
	"streetname"	TEXT NOT NULL,
	"houseNumber"	integer NOT NULL,
	"bus"	TEXT,
	"postalCode"	TEXT NOT NULL,
	"city"	TEXT NOT NULL,
	"country"	TEXT NOT NULL,
	"telephone"	INTEGER NOT NULL,
	"email"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"userType"	TEXT NOT NULL CHECK("userType" IN ("CUSTOMER", "VOLUNTEER")),
	PRIMARY KEY("userID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "bridge_games_consoles" (
	"gameID"	INTEGER NOT NULL,
	"consoleID"	INTEGER NOT NULL,
	FOREIGN KEY("gameID") REFERENCES "games"("gameID"),
	FOREIGN KEY("consoleID") REFERENCES "consoles"("consoleID")
);
CREATE TABLE IF NOT EXISTS "bridge_games_devcompanies" (
	"gameID"	INTEGER NOT NULL,
	"devCompanyID"	INTEGER NOT NULL,
	FOREIGN KEY("gameID") REFERENCES "games"("gameID"),
	FOREIGN KEY("devCompanyID") REFERENCES "dev_companies"("devID")
);
CREATE TABLE IF NOT EXISTS "bridge_locations_volunteers" (
	"locationID"	INTEGER NOT NULL,
	"volunteerID"	INTEGER NOT NULL,
	FOREIGN KEY("volunteerID") REFERENCES "users"("userID"),
	FOREIGN KEY("locationID") REFERENCES "locations"("locationID")
);
CREATE TABLE IF NOT EXISTS "consoles" (
	"consoleID"	INTEGER NOT NULL UNIQUE,
	"consoleName"	TEXT NOT NULL,
	"consoleType"	TEXT NOT NULL,
	"company"	integer NOT NULL,
	"releasedYear"	datetime NOT NULL,
	"discontinuationYear"	datetime,
	"unitsSoldMillion"	REAL,
	"remarks"	TEXT,
	PRIMARY KEY("consoleID" AUTOINCREMENT),
	CONSTRAINT "consoles_dev_companies_devID_fk" FOREIGN KEY("company") REFERENCES "dev_companies" deferrable
);
CREATE TABLE IF NOT EXISTS "bridge_games_loanreceipts" (
	"gameID"	integer NOT NULL,
	"loanID"	integer NOT NULL,
	CONSTRAINT "bridge_games_loanreceipts_games_gameID_fk" FOREIGN KEY("gameID") REFERENCES "games",
	CONSTRAINT "bridge_games_loanreceipts_loan_receipts_referenceID_fk" FOREIGN KEY("loanID") REFERENCES "loan_receipts"
);
CREATE TABLE IF NOT EXISTS "loan_receipts" (
	"referenceID"	INTEGER NOT NULL UNIQUE,
	"customerID"	INTEGER NOT NULL,
	"loanedDate"	datetime NOT NULL,
	"loanTermInDays"	INTEGER NOT NULL,
	"returnDate"	datetime,
	"fine"	REAL,
	PRIMARY KEY("referenceID" AUTOINCREMENT),
	FOREIGN KEY("customerID") REFERENCES "users"
);
INSERT INTO "dev_companies" VALUES (1,'Sony Interactive Entertainment','https://www.playstation.com/','support@sony.com','1993-11-16','PlayStation Street',123,'Building A','PS123','GameCity','Japan');
INSERT INTO "dev_companies" VALUES (2,'Microsoft Game Studios','https://www.xbox.com/','support@microsoft.com','2000-03-14','Xbox Avenue',456,'Suite B','XBX456','GamerTown','USA');
INSERT INTO "dev_companies" VALUES (3,'Nintendo Co., Ltd.','https://www.nintendo.com/','support@nintendo.com','1889-09-23','Mario Street',789,'Block C','NIN789','Mushroom Kingdom','Japan');
INSERT INTO "dev_companies" VALUES (4,'Sega Corporation','https://www.sega.com/','support@sega.com','1960-06-03','Sonic Lane',101,'Speed Building','SEG101','Emerald Hill','Japan');
INSERT INTO "dev_companies" VALUES (5,'Electronic Arts','https://www.ea.com/','support@ea.com','1982-05-28','Gaming Blvd',202,'Floor 5','EA202','Gameville','USA');
INSERT INTO "dev_companies" VALUES (6,'Ubisoft','https://www.ubisoft.com/','support@ubisoft.com','1986-03-28','Assassin''s Creed Street',303,'Tower D','UBI303','Montreal','Canada');
INSERT INTO "dev_companies" VALUES (7,'Activision','https://www.activision.com/','support@activision.com','1979-10-01','Call of Duty Avenue',404,'Penthouse','ACT404','Santa Monica','USA');
INSERT INTO "dev_companies" VALUES (8,'Square Enix','https://www.square-enix.com/','support@square-enix.com','1975-09-22','Final Fantasy Road',505,'Crystal Tower','SQE505','Tokyo','Japan');
INSERT INTO "dev_companies" VALUES (9,'Capcom','https://www.capcom.com/','support@capcom.com','1979-06-11','Resident Evil Street',606,'Lab Basement','CAP606','Osaka','Japan');
INSERT INTO "dev_companies" VALUES (10,'Bethesda Game Studios','https://bethesda.net/','support@bethesda.com','2001-06-28','Elder Scrolls Lane',707,'Skyrim Tower','BGS707','Rockville','USA');
INSERT INTO "games" VALUES (1,1,11,11,'The Legend of Zelda: Link''s Awakening','1993-06-06','Action-Adventure');
INSERT INTO "games" VALUES (2,2,12,12,'Pokémon Red/Blue','1996-09-28','Role-Playing');
INSERT INTO "games" VALUES (3,3,13,13,'Super Mario World','1990-11-21','Platformer');
INSERT INTO "games" VALUES (4,4,14,14,'Tetris','1989-06-06','Puzzle');
INSERT INTO "games" VALUES (5,5,11,15,'Metroid','1986-08-06','Action-Adventure');
INSERT INTO "games" VALUES (6,6,13,16,'Donkey Kong Country','1994-11-21','Platformer');
INSERT INTO "games" VALUES (7,7,15,15,'Final Fantasy Adventure','1991-06-28','Action RPG');
INSERT INTO "games" VALUES (8,8,14,14,'Castlevania: Symphony of the Night','1997-10-02','Action-Adventure');
INSERT INTO "games" VALUES (9,9,16,17,'Pac-Man','1980-05-22','Arcade');
INSERT INTO "games" VALUES (10,10,17,16,'Mega Man 2','1988-12-24','Platformer');
INSERT INTO "games" VALUES (11,11,13,13,'The Legend of Zelda: A Link to the Past','1991-04-13','Action-Adventure');
INSERT INTO "games" VALUES (12,2,1,1,'Super Mario Land','1989-04-21','Platformer');
INSERT INTO "games" VALUES (13,3,12,12,'Pokémon Yellow','1998-10-19','Role-Playing');
INSERT INTO "games" VALUES (14,4,15,15,'Chrono Trigger','1995-03-11','Role-Playing');
INSERT INTO "games" VALUES (15,5,1,8,'Kirby''s Dream Land','1992-04-27','Platformer');
INSERT INTO "games" VALUES (16,6,9,10,'Street Fighter II: The World Warrior','1991-02-06','Fighting');
INSERT INTO "games" VALUES (17,7,2,2,'Sonic the Hedgehog 2','1992-11-24','Platformer');
INSERT INTO "games" VALUES (18,8,12,12,'Final Fantasy Legends','1989-12-15','Role-Playing');
INSERT INTO "games" VALUES (19,9,13,13,'Super Mario Bros.','1985-09-13','Platformer');
INSERT INTO "games" VALUES (20,2,11,11,'Legend of Zelda: Oracle of Ages/Seasons','2001-05-14','Action-Adventure');
INSERT INTO "games" VALUES (21,1,12,12,'Pokémon Gold/Silver','1999-11-21','Role-Playing');
INSERT INTO "games" VALUES (22,2,13,13,'Super Mario Kart','1992-08-27','Racing');
INSERT INTO "games" VALUES (23,3,14,14,'Castlevania: Aria of Sorrow','2003-05-06','Action-Adventure');
INSERT INTO "games" VALUES (24,4,16,16,'Pac-Man Championship Edition','2007-06-06','Arcade');
INSERT INTO "games" VALUES (25,5,17,17,'Mega Man X','1993-12-17','Platformer');
INSERT INTO "games" VALUES (26,6,11,11,'The Legend of Zelda: Majora''s Mask','2000-04-27','Action-Adventure');
INSERT INTO "games" VALUES (27,7,18,18,'Super Mario Land 2: 6 Golden Coins','1992-10-21','Platformer');
INSERT INTO "games" VALUES (28,8,12,12,'Pokémon Crystal','2000-12-14','Role-Playing');
INSERT INTO "games" VALUES (29,9,15,15,'Final Fantasy VI','1994-04-02','Role-Playing');
INSERT INTO "games" VALUES (30,3,2,2,'Sonic the Hedgehog 3','1994-02-02','Platformer');
INSERT INTO "games" VALUES (31,1,13,13,'Donkey Kong','1981-07-09','Arcade');
INSERT INTO "games" VALUES (32,2,11,15,'Metroid: Zero Mission','2004-02-09','Action-Adventure');
INSERT INTO "games" VALUES (33,3,15,15,'Chrono Cross','1999-11-18','Role-Playing');
INSERT INTO "games" VALUES (34,4,8,8,'Kirby''s Adventure','1993-11-25','Platformer');
INSERT INTO "games" VALUES (35,5,9,9,'Street Fighter II Turbo: Hyper Fighting','1992-07-11','Fighting');
INSERT INTO "games" VALUES (36,3,12,15,'Sonic & Knuckles','1994-10-18','Platformer');
INSERT INTO "games" VALUES (37,3,12,12,'Final Fantasy Tactics Advance','2003-02-14','Tactical RPG');
INSERT INTO "games" VALUES (38,3,13,13,'Super Mario Bros. 3','1988-10-23','Platformer');
INSERT INTO "games" VALUES (39,3,11,11,'Legend of Zelda: The Minish Cap','2004-11-04','Action-Adventure');
INSERT INTO "games" VALUES (40,4,12,12,'Pokémon Ruby/Sapphire','2002-11-21','Role-Playing');
INSERT INTO "games" VALUES (41,1,11,15,'Super Metroid','1994-03-19','Action-Adventure');
INSERT INTO "games" VALUES (42,4,14,14,'Castlevania: Circle of the Moon','2001-06-11','Action-Adventure');
INSERT INTO "games" VALUES (43,4,16,17,'Pac-Mania','1987-12-01','Arcade');
INSERT INTO "games" VALUES (44,4,17,8,'Mega Man 3','1990-09-28','Platformer');
INSERT INTO "locations" VALUES (1,1,'Volunteer Street',111,'Suite V','11111','Volunteerville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (2,3,'Museum Avenue',222,NULL,'22222','Museumtown','Countryland','MUSEUM');
INSERT INTO "locations" VALUES (3,1,'Storage Lane',333,'Unit S','33333','Storagetown','Countryland','STORAGE');
INSERT INTO "locations" VALUES (4,4,'Liberty Road',444,'Floor L','44444','Libraryville','Countryland','LIBRARY');
INSERT INTO "locations" VALUES (5,2,'Expo Boulevard',555,'Booth E','55555','Expoville','Countryland','EXPO');
INSERT INTO "locations" VALUES (6,5,'Park Street',123,NULL,'67890','Parkville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (7,7,'Gaming Avenue',789,'Shop G','98765','Gamingville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (8,8,'Retro Street',987,NULL,'12345','Retroville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (9,9,'Player Lane',654,'Gaming Tower','56789','Playertown','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (10,1,'Console Boulevard',321,'Console Corner','13579','Consoletown','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (11,1,'Virtual Street',111,'VR Suite','24680','Virtualville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (12,1,'Pixel Avenue',222,'Pixel House','13579','Pixeltown','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (13,1,'Quest Lane',333,NULL,'98765','Questville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (14,1,'Strategy Road',444,'Strategic Tower','54321','Strategytown','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (15,1,'Simulation Street',555,'Sim House','24680','Simland','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (16,6,'Action Avenue',666,NULL,'11111','Actiontown','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (17,7,'Adventure Road',777,'Adventure House','22222','Adventureville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (18,8,'Fighting Lane',888,'Fighter Tower','33333','Fightingtown','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (19,9,'Racing Street',999,NULL,'44444','Racingville','Countryland','PRIVATE');
INSERT INTO "locations" VALUES (20,10,'Puzzle Avenue',111,'Puzzle Corner','55555','Puzzletown','Countryland','PRIVATE');
INSERT INTO "users" VALUES (1,'John','Doe','Main Street',123,NULL,'12345','Cityville','Countryland',123456789,'john.doe@email.com','password123','VOLUNTEER');
INSERT INTO "users" VALUES (2,'Jane','Smith','Maple Avenue',456,'Suite A','67890','Townsville','Countryland',987654321,'jane.smith@email.com','pass456','VOLUNTEER');
INSERT INTO "users" VALUES (3,'Alice','Johnson','Oak Street',789,NULL,'54321','Villagetown','Countryland',456789012,'alice.johnson@email.com','secure789','VOLUNTEER');
INSERT INTO "users" VALUES (4,'Bob','Miller','Pine Road',101,'Apt 5','13579','Hamletville','Countryland',789012345,'bob.miller@email.com','secret567','VOLUNTEER');
INSERT INTO "users" VALUES (5,'Eva','Wilson','Cedar Lane',202,NULL,'24680','Hillside','Countryland',234567890,'eva.wilson@email.com','hidden890','VOLUNTEER');
INSERT INTO "users" VALUES (6,'Chris','Brown','Rose Lane',303,'Suite B','97531','Gardentown','Countryland',345678901,'chris.brown@email.com','roses123','VOLUNTEER');
INSERT INTO "users" VALUES (7,'Maria','Garcia','Sunset Boulevard',404,NULL,'86420','Beachville','Countryland',567890123,'maria.garcia@email.com','sunset456','VOLUNTEER');
INSERT INTO "users" VALUES (8,'Michael','Anderson','Mountain View',505,'Cabin C','74123','Peakville','Countryland',678901234,'michael.anderson@email.com','mountain789','VOLUNTEER');
INSERT INTO "users" VALUES (9,'Emily','Clark','Ocean Drive',606,NULL,'36987','Wavetown','Countryland',789012345,'emily.clark@email.com','ocean567','VOLUNTEER');
INSERT INTO "users" VALUES (10,'Daniel','Moore','Valley Street',707,'Apartment D','25874','Meadowville','Countryland',890123456,'daniel.moore@email.com','valley890','VOLUNTEER');
INSERT INTO "users" VALUES (11,'Sarah','Wilson','Lakefront Road',808,NULL,'15963','Shoreville','Countryland',901234567,'sarah.wilson@email.com','lakefront123','CUSTOMER');
INSERT INTO "users" VALUES (12,'Ryan','Taylor','Park Lane',909,'Unit E','75319','Greentown','Countryland',123456789,'ryan.taylor@email.com','park456','CUSTOMER');
INSERT INTO "users" VALUES (13,'Olivia','Baker','Grove Street',1010,NULL,'64237','Woodville','Countryland',234567890,'olivia.baker@email.com','grove789','CUSTOMER');
INSERT INTO "users" VALUES (14,'James','Cooper','Harbor View',1111,'House F','52846','Portville','Countryland',345678901,'james.cooper@email.com','harbor123','CUSTOMER');
INSERT INTO "users" VALUES (15,'Sophia','Ward','Highland Avenue',1212,NULL,'91735','Hilltown','Countryland',456789012,'sophia.ward@email.com','highland456','CUSTOMER');
INSERT INTO "users" VALUES (16,'Liam','Fisher','River Road',1313,'Suite G','36928','Rivertown','Countryland',567890123,'liam.fisher@email.com','river789','CUSTOMER');
INSERT INTO "users" VALUES (17,'Ava','Martin','Meadow Lane',1414,NULL,'86420','Meadowtown','Countryland',678901234,'ava.martin@email.com','meadow567','CUSTOMER');
INSERT INTO "users" VALUES (18,'Noah','Bailey','Hillside Drive',1515,'Unit H','97531','Hillville','Countryland',789012345,'noah.bailey@email.com','hillside890','CUSTOMER');
INSERT INTO "users" VALUES (19,'Emma','Perez','Skyline Avenue',1616,NULL,'24680','Skytown','Countryland',890123456,'emma.perez@email.com','skyline123','CUSTOMER');
INSERT INTO "users" VALUES (20,'Jackson','Miller','Breeze Lane',1717,'Apartment I','75319','Breezetown','Countryland',901234567,'jackson.miller@email.com','breeze456','CUSTOMER');
INSERT INTO "bridge_games_consoles" VALUES (1,1);
INSERT INTO "bridge_games_consoles" VALUES (2,3);
INSERT INTO "bridge_games_consoles" VALUES (3,13);
INSERT INTO "bridge_games_consoles" VALUES (4,14);
INSERT INTO "bridge_games_consoles" VALUES (5,11);
INSERT INTO "bridge_games_consoles" VALUES (6,13);
INSERT INTO "bridge_games_consoles" VALUES (7,12);
INSERT INTO "bridge_games_consoles" VALUES (8,14);
INSERT INTO "bridge_games_consoles" VALUES (8,14);
INSERT INTO "bridge_games_consoles" VALUES (9,11);
INSERT INTO "bridge_games_consoles" VALUES (10,10);
INSERT INTO "bridge_games_consoles" VALUES (11,13);
INSERT INTO "bridge_games_consoles" VALUES (12,13);
INSERT INTO "bridge_games_consoles" VALUES (12,14);
INSERT INTO "bridge_games_consoles" VALUES (13,14);
INSERT INTO "bridge_games_consoles" VALUES (14,9);
INSERT INTO "bridge_games_consoles" VALUES (14,4);
INSERT INTO "bridge_games_consoles" VALUES (16,2);
INSERT INTO "bridge_games_consoles" VALUES (16,1);
INSERT INTO "bridge_games_consoles" VALUES (17,2);
INSERT INTO "bridge_games_consoles" VALUES (17,2);
INSERT INTO "bridge_games_consoles" VALUES (18,4);
INSERT INTO "bridge_games_consoles" VALUES (18,8);
INSERT INTO "bridge_games_consoles" VALUES (19,14);
INSERT INTO "bridge_games_consoles" VALUES (20,13);
INSERT INTO "bridge_games_consoles" VALUES (20,14);
INSERT INTO "bridge_games_consoles" VALUES (20,9);
INSERT INTO "bridge_games_consoles" VALUES (20,10);
INSERT INTO "bridge_games_consoles" VALUES (21,9);
INSERT INTO "bridge_games_consoles" VALUES (21,3);
INSERT INTO "bridge_games_consoles" VALUES (22,13);
INSERT INTO "bridge_games_consoles" VALUES (22,14);
INSERT INTO "bridge_games_consoles" VALUES (22,13);
INSERT INTO "bridge_games_consoles" VALUES (22,3);
INSERT INTO "bridge_games_consoles" VALUES (23,14);
INSERT INTO "bridge_games_consoles" VALUES (23,3);
INSERT INTO "bridge_games_consoles" VALUES (24,6);
INSERT INTO "bridge_games_consoles" VALUES (24,2);
INSERT INTO "bridge_games_consoles" VALUES (25,7);
INSERT INTO "bridge_games_consoles" VALUES (25,10);
INSERT INTO "bridge_games_consoles" VALUES (26,11);
INSERT INTO "bridge_games_consoles" VALUES (26,13);
INSERT INTO "bridge_games_consoles" VALUES (27,8);
INSERT INTO "bridge_games_consoles" VALUES (27,13);
INSERT INTO "bridge_games_consoles" VALUES (27,10);
INSERT INTO "bridge_games_consoles" VALUES (28,12);
INSERT INTO "bridge_games_consoles" VALUES (28,13);
INSERT INTO "bridge_games_consoles" VALUES (29,5);
INSERT INTO "bridge_games_consoles" VALUES (29,3);
INSERT INTO "bridge_games_consoles" VALUES (30,10);
INSERT INTO "bridge_games_consoles" VALUES (31,13);
INSERT INTO "bridge_games_consoles" VALUES (32,11);
INSERT INTO "bridge_games_consoles" VALUES (33,5);
INSERT INTO "bridge_games_consoles" VALUES (34,8);
INSERT INTO "bridge_games_consoles" VALUES (34,13);
INSERT INTO "bridge_games_consoles" VALUES (35,9);
INSERT INTO "bridge_games_consoles" VALUES (36,10);
INSERT INTO "bridge_games_consoles" VALUES (37,12);
INSERT INTO "bridge_games_consoles" VALUES (37,13);
INSERT INTO "bridge_games_consoles" VALUES (38,13);
INSERT INTO "bridge_games_consoles" VALUES (39,11);
INSERT INTO "bridge_games_consoles" VALUES (39,13);
INSERT INTO "bridge_games_consoles" VALUES (40,12);
INSERT INTO "bridge_games_consoles" VALUES (40,13);
INSERT INTO "bridge_games_consoles" VALUES (41,11);
INSERT INTO "bridge_games_consoles" VALUES (41,13);
INSERT INTO "bridge_games_consoles" VALUES (42,14);
INSERT INTO "bridge_games_consoles" VALUES (43,6);
INSERT INTO "bridge_games_consoles" VALUES (43,2);
INSERT INTO "bridge_games_consoles" VALUES (44,7);
INSERT INTO "bridge_games_consoles" VALUES (44,2);
INSERT INTO "bridge_games_devcompanies" VALUES (1,1);
INSERT INTO "bridge_games_devcompanies" VALUES (2,3);
INSERT INTO "bridge_games_devcompanies" VALUES (3,3);
INSERT INTO "bridge_games_devcompanies" VALUES (4,4);
INSERT INTO "bridge_games_devcompanies" VALUES (5,5);
INSERT INTO "bridge_games_devcompanies" VALUES (6,6);
INSERT INTO "bridge_games_devcompanies" VALUES (7,7);
INSERT INTO "bridge_games_devcompanies" VALUES (8,7);
INSERT INTO "bridge_games_devcompanies" VALUES (8,9);
INSERT INTO "bridge_games_devcompanies" VALUES (9,9);
INSERT INTO "bridge_games_devcompanies" VALUES (10,10);
INSERT INTO "bridge_games_devcompanies" VALUES (11,1);
INSERT INTO "bridge_games_devcompanies" VALUES (12,2);
INSERT INTO "bridge_games_devcompanies" VALUES (13,3);
INSERT INTO "bridge_games_devcompanies" VALUES (14,4);
INSERT INTO "bridge_games_devcompanies" VALUES (15,5);
INSERT INTO "bridge_games_devcompanies" VALUES (16,4);
INSERT INTO "bridge_games_devcompanies" VALUES (16,1);
INSERT INTO "bridge_games_devcompanies" VALUES (17,7);
INSERT INTO "bridge_games_devcompanies" VALUES (18,8);
INSERT INTO "bridge_games_devcompanies" VALUES (19,9);
INSERT INTO "bridge_games_devcompanies" VALUES (20,10);
INSERT INTO "bridge_games_devcompanies" VALUES (21,1);
INSERT INTO "bridge_games_devcompanies" VALUES (22,2);
INSERT INTO "bridge_games_devcompanies" VALUES (23,3);
INSERT INTO "bridge_games_devcompanies" VALUES (24,4);
INSERT INTO "bridge_games_devcompanies" VALUES (25,5);
INSERT INTO "bridge_games_devcompanies" VALUES (26,6);
INSERT INTO "bridge_games_devcompanies" VALUES (27,7);
INSERT INTO "bridge_games_devcompanies" VALUES (28,8);
INSERT INTO "bridge_games_devcompanies" VALUES (29,9);
INSERT INTO "bridge_games_devcompanies" VALUES (30,10);
INSERT INTO "bridge_games_devcompanies" VALUES (31,1);
INSERT INTO "bridge_games_devcompanies" VALUES (32,2);
INSERT INTO "bridge_games_devcompanies" VALUES (33,3);
INSERT INTO "bridge_games_devcompanies" VALUES (34,4);
INSERT INTO "bridge_games_devcompanies" VALUES (35,5);
INSERT INTO "bridge_games_devcompanies" VALUES (36,6);
INSERT INTO "bridge_games_devcompanies" VALUES (37,7);
INSERT INTO "bridge_games_devcompanies" VALUES (38,8);
INSERT INTO "bridge_games_devcompanies" VALUES (39,9);
INSERT INTO "bridge_games_devcompanies" VALUES (40,10);
INSERT INTO "bridge_games_devcompanies" VALUES (41,1);
INSERT INTO "bridge_games_devcompanies" VALUES (42,2);
INSERT INTO "bridge_games_devcompanies" VALUES (43,3);
INSERT INTO "bridge_games_devcompanies" VALUES (44,4);
INSERT INTO "bridge_locations_volunteers" VALUES (1,1);
INSERT INTO "bridge_locations_volunteers" VALUES (1,2);
INSERT INTO "bridge_locations_volunteers" VALUES (2,2);
INSERT INTO "bridge_locations_volunteers" VALUES (2,3);
INSERT INTO "bridge_locations_volunteers" VALUES (3,3);
INSERT INTO "bridge_locations_volunteers" VALUES (3,4);
INSERT INTO "bridge_locations_volunteers" VALUES (4,4);
INSERT INTO "bridge_locations_volunteers" VALUES (4,5);
INSERT INTO "bridge_locations_volunteers" VALUES (5,5);
INSERT INTO "bridge_locations_volunteers" VALUES (5,6);
INSERT INTO "bridge_locations_volunteers" VALUES (6,6);
INSERT INTO "bridge_locations_volunteers" VALUES (6,7);
INSERT INTO "bridge_locations_volunteers" VALUES (7,7);
INSERT INTO "bridge_locations_volunteers" VALUES (7,8);
INSERT INTO "bridge_locations_volunteers" VALUES (8,8);
INSERT INTO "bridge_locations_volunteers" VALUES (8,9);
INSERT INTO "bridge_locations_volunteers" VALUES (9,9);
INSERT INTO "bridge_locations_volunteers" VALUES (9,10);
INSERT INTO "bridge_locations_volunteers" VALUES (10,10);
INSERT INTO "bridge_locations_volunteers" VALUES (10,1);
INSERT INTO "consoles" VALUES (1,'PlayStation','Home Console',1,'1994-12-03','2006-03-23',102.49,'Iconic gaming console');
INSERT INTO "consoles" VALUES (2,'Xbox','Home Console',2,'2001-11-15',NULL,51.0,'Microsoft gaming console');
INSERT INTO "consoles" VALUES (3,'Nintendo Switch','Hybrid Console',3,'2017-03-03',NULL,89.04,'Versatile gaming console and handheld');
INSERT INTO "consoles" VALUES (4,'Sega Genesis','Home Console',4,'1988-08-14','1997-03-03',30.75,'Classic Sega gaming console');
INSERT INTO "consoles" VALUES (5,'PlayStation 2','Home Console',1,'2000-03-04','2013-01-04',155.0,'Best-selling console of all time');
INSERT INTO "consoles" VALUES (6,'Xbox 360','Home Console',2,'2005-11-22','2016-04-20',84.0,'Revolutionary online gaming');
INSERT INTO "consoles" VALUES (7,'Nintendo Wii','Home Console',3,'2006-11-19','2013-10-20',101.63,'Motion-sensing gaming experience');
INSERT INTO "consoles" VALUES (8,'Super Nintendo Entertainment System (SNES)','Home Console',3,'1990-08-23','2003-09-25',49.1,'16-bit classic');
INSERT INTO "consoles" VALUES (9,'Sega Dreamcast','Home Console',4,'1998-11-27','2001-03-31',9.13,'First console with built-in modem');
INSERT INTO "consoles" VALUES (10,'Xbox One','Home Console',2,'2013-11-22',NULL,51.0,'Multimedia gaming powerhouse');
INSERT INTO "consoles" VALUES (11,'Nintendo 3DS','Handheld Console',3,'2011-02-26','2020-09-16',75.94,'Dual-screen 3D gaming');
INSERT INTO "consoles" VALUES (12,'PlayStation Portable (PSP)','Handheld Console',1,'2004-12-12','2014-06-04',80.0,'Portable multimedia entertainment');
INSERT INTO "consoles" VALUES (13,'Game Boy','Handheld Console',3,'1989-04-21','2003-03-23',118.69,'Legendary handheld gaming');
INSERT INTO "consoles" VALUES (14,'Nintendo DS','Handheld Console',3,'2004-11-21','2013-03-31',154.02,'Dual-screen portable gaming revolution');
INSERT INTO "loan_receipts" VALUES (1,2,'2023-01-01',14,'2023-01-15',5.0);
INSERT INTO "loan_receipts" VALUES (2,14,'2023-02-01',7,'2023-02-08',NULL);
INSERT INTO "loan_receipts" VALUES (3,12,'2023-03-01',21,NULL,NULL);
INSERT INTO "loan_receipts" VALUES (4,15,'2023-04-01',30,NULL,NULL);
COMMIT;
