--Drop all tables
DROP TABLE IF EXISTS bridge_games_consoles;
DROP TABLE IF EXISTS bridge_games_devcompanies;
DROP TABLE IF EXISTS bridge_locations_volunteers;
DROP TABLE IF EXISTS consoles;
DROP TABLE IF EXISTS dev_companies;
DROP TABLE IF EXISTS loan_receips;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS users;

--CREATE Tables
CREATE TABLE "consoles" (
	"consoleID"	INTEGER NOT NULL UNIQUE,
	"consoleName"	TEXT NOT NULL,
	"consoleType"	TEXT NOT NULL,
	"company"	TEXT NOT NULL,
	"releasedYear"	datetime NOT NULL,
	"discontinuationYear"	datetime,
	"unitsSoldMillion"	REAL,
	"remarks"	TEXT,
	PRIMARY KEY("consoleID" AUTOINCREMENT)
);

CREATE TABLE "dev_companies" (
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

CREATE TABLE "games" (
	"gameID"	INTEGER NOT NULL UNIQUE,
	"ownerID"	integer NOT NULL,
	"homeBaseID"	integer NOT NULL,
	"currentLocationId"	integer NOT NULL,
	"title"	TEXT NOT NULL,
	"releaseDate"	datetime,
	"genre" TEXT,
	FOREIGN KEY("homeBaseID") REFERENCES "locations"("locationID"),
	FOREIGN KEY("currentLocationId") REFERENCES "locations"("locationID"),
	FOREIGN KEY("ownerID") REFERENCES "users"("userID"),
	PRIMARY KEY("gameID" AUTOINCREMENT)
);

CREATE TABLE "loan_receips" (
	"referenceID"	INTEGER NOT NULL UNIQUE,
	"gameID"	INTEGER NOT NULL,
	"customerID"	INTEGER NOT NULL,
	"loanedDate"	datetime NOT NULL,
	"loanTermInDays"	INTEGER NOT NULL,
	"returnDate"	datetime,
	"fine"	REAL,
	FOREIGN KEY("gameID") REFERENCES "games"("gameID"),
	FOREIGN KEY("customerID") REFERENCES "users"("userID"),
	PRIMARY KEY("referenceID" AUTOINCREMENT)
);

CREATE TABLE "locations" (
	"locationID"	INTEGER NOT NULL UNIQUE,
	"ownerID"	integer NOT NULL,
	"streetname"	TEXT NOT NULL,
	"houseNumber"	integer NOT NULL,
	"bus"	TEXT,
	"postalCode"	TEXT NOT NULL,
	"city"	TEXT NOT NULL,
	"country"	TEXT NOT NULL,
	"locationType"	TEXT NOT NULL CHECK("locationType" IN ("PRIVATE", "MUSEUM", "EXPO", "LIBRARY", "STORAGE")),
	FOREIGN KEY("ownerID") REFERENCES "users"("userID"),
	PRIMARY KEY("locationID" AUTOINCREMENT)
);

CREATE TABLE "users" (
	"userID"	INTEGER NOT NULL UNIQUE,
	"username" TEXT NOT NULL UNIQUE,
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

CREATE TABLE "bridge_games_consoles" (
	"gameID"	INTEGER NOT NULL,
	"consoleID"	INTEGER NOT NULL,
	FOREIGN KEY("consoleID") REFERENCES "consoles"("consoleID"),
	FOREIGN KEY("gameID") REFERENCES "games"("gameID")
);

CREATE TABLE "bridge_games_devcompanies" (
	"gameID"	INTEGER NOT NULL,
	"devCompanyID"	INTEGER NOT NULL,
	FOREIGN KEY("devCompanyID") REFERENCES "dev_companies"("devID"),
	FOREIGN KEY("gameID") REFERENCES "games"("gameID")
);

CREATE TABLE "bridge_locations_volunteers" (
	"locationID"	INTEGER NOT NULL,
	"volunteerID"	INTEGER NOT NULL,
	FOREIGN KEY("volunteerID") REFERENCES "users"("userID"),
	FOREIGN KEY("locationID") REFERENCES "locations"("locationID")
);

--INSERT DATA (Generated with CHATPGT 3.5 on 30/11/2023)
INSERT INTO consoles (consoleName, consoleType, company, releasedYear, discontinuationYear, unitsSoldMillion, remarks) VALUES
  ("PlayStation", "Home Console", 1, "1994-12-03", "2006-03-23", 102.49, "Iconic gaming console"),
  ("Xbox", "Home Console", 2, "2001-11-15", NULL, 51, "Microsoft gaming console"),
  ("Nintendo Switch", "Hybrid Console", 3, "2017-03-03", NULL, 89.04, "Versatile gaming console and handheld"),
  ("Sega Genesis", "Home Console", 4, "1988-08-14", "1997-03-03", 30.75, "Classic Sega gaming console"),
  ("PlayStation 2", "Home Console", 1, "2000-03-04", "2013-01-04", 155, "Best-selling console of all time"),
  ("Xbox 360", "Home Console", 2, "2005-11-22", "2016-04-20", 84, "Revolutionary online gaming"),
  ("Nintendo Wii", "Home Console", 3, "2006-11-19", "2013-10-20", 101.63, "Motion-sensing gaming experience"),
  ("Super Nintendo Entertainment System (SNES)", "Home Console", 3, "1990-08-23", "2003-09-25", 49.1, "16-bit classic"),
  ("Sega Dreamcast", "Home Console", 4, "1998-11-27", "2001-03-31", 9.13, "First console with built-in modem"),
  ("Xbox One", "Home Console", 2, "2013-11-22", NULL, 51, "Multimedia gaming powerhouse"),
  ("Nintendo 3DS", "Handheld Console", 3, "2011-02-26", "2020-09-16", 75.94, "Dual-screen 3D gaming"),
  ("PlayStation Portable (PSP)", "Handheld Console", 1, "2004-12-12", "2014-06-04", 80, "Portable multimedia entertainment"),
  ("Game Boy", "Handheld Console", 3, "1989-04-21", "2003-03-23", 118.69, "Legendary handheld gaming"),
  ("Nintendo DS", "Handheld Console", 3, "2004-11-21", "2013-03-31", 154.02, "Dual-screen portable gaming revolution");
  
INSERT INTO dev_companies (companyName, website, supportEmail, establishedDate, streetname, houseNumber, bus, postalCode, city, country) VALUES
  ("Sony Interactive Entertainment", "https://www.playstation.com/", "support@sony.com", "1993-11-16", "PlayStation Street", 123, "Building A", "PS123", "GameCity", "Japan"),
  ("Microsoft Game Studios", "https://www.xbox.com/", "support@microsoft.com", "2000-03-14", "Xbox Avenue", 456, "Suite B", "XBX456", "GamerTown", "USA"),
  ("Nintendo Co., Ltd.", "https://www.nintendo.com/", "support@nintendo.com", "1889-09-23", "Mario Street", 789, "Block C", "NIN789", "Mushroom Kingdom", "Japan"),
  ("Sega Corporation", "https://www.sega.com/", "support@sega.com", "1960-06-03", "Sonic Lane", 101, "Speed Building", "SEG101", "Emerald Hill", "Japan"),
  ("Electronic Arts", "https://www.ea.com/", "support@ea.com", "1982-05-28", "Gaming Blvd", 202, "Floor 5", "EA202", "Gameville", "USA"),
  ("Ubisoft", "https://www.ubisoft.com/", "support@ubisoft.com", "1986-03-28", "Assassin's Creed Street", 303, "Tower D", "UBI303", "Montreal", "Canada"),
  ("Activision", "https://www.activision.com/", "support@activision.com", "1979-10-01", "Call of Duty Avenue", 404, "Penthouse", "ACT404", "Santa Monica", "USA"),
  ("Square Enix", "https://www.square-enix.com/", "support@square-enix.com", "1975-09-22", "Final Fantasy Road", 505, "Crystal Tower", "SQE505", "Tokyo", "Japan"),
  ("Capcom", "https://www.capcom.com/", "support@capcom.com", "1979-06-11", "Resident Evil Street", 606, "Lab Basement", "CAP606", "Osaka", "Japan"),
  ("Bethesda Game Studios", "https://bethesda.net/", "support@bethesda.com", "2001-06-28", "Elder Scrolls Lane", 707, "Skyrim Tower", "BGS707", "Rockville", "USA");

INSERT INTO "users" ("username", "firstname", "lastname", "streetname", "houseNumber", "bus", "postalCode", "city", "country", "telephone", "email", "password", "userType") VALUES
  ("JohnDoe", "John", "Doe", "Main Street", 123, NULL, "12345", "Cityville", "Countryland", 123456789, "john.doe@email.com", "password123", "VOLUNTEER"),
  ("JaneSmith", "Jane", "Smith", "Maple Avenue", 456, "Suite A", "67890", "Townsville", "Countryland", 987654321, "jane.smith@email.com", "pass456", "VOLUNTEER"),
  ("AliceJognson", "Alice", "Johnson", "Oak Street", 789, NULL, "54321", "Villagetown", "Countryland", 456789012, "alice.johnson@email.com", "secure789", "VOLUNTEER"),
  ("BobMiller5", "Bob", "Miller", "Pine Road", 101, "Apt 5", "13579", "Hamletville", "Countryland", 789012345, "bob.miller@email.com", "secret567", "VOLUNTEER"),
  ("EvaWilson123", "Eva", "Wilson", "Cedar Lane", 202, NULL, "24680", "Hillside", "Countryland", 234567890, "eva.wilson@email.com", "hidden890", "VOLUNTEER"),
  ("ChrisB", "Chris", "Brown", "Rose Lane", 303, "Suite B", "97531", "Gardentown", "Countryland", 345678901, "chris.brown@email.com", "roses123", "VOLUNTEER"),
  ("MariaGARCIA", "Maria", "Garcia", "Sunset Boulevard", 404, NULL, "86420", "Beachville", "Countryland", 567890123, "maria.garcia@email.com", "sunset456", "VOLUNTEER"),
  ("MichaelAnderson95", "Michael", "Anderson", "Mountain View", 505, "Cabin C", "74123", "Peakville", "Countryland", 678901234, "michael.anderson@email.com", "mountain789", "VOLUNTEER"),
  ("EmilyC", "Emily", "Clark", "Ocean Drive", 606, NULL, "36987", "Wavetown", "Countryland", 789012345, "emily.clark@email.com", "ocean567", "VOLUNTEER"),
  ("DanielMoore", "Daniel", "Moore", "Valley Street", 707, "Apartment D", "25874", "Meadowville", "Countryland", 890123456, "daniel.moore@email.com", "valley890", "VOLUNTEER"),
  ("SarahWilson", "Sarah", "Wilson", "Lakefront Road", 808, NULL, "15963", "Shoreville", "Countryland", 901234567, "sarah.wilson@email.com", "lakefront123", "CUSTOMER"),
  ("R-Taylor", "Ryan", "Taylor", "Park Lane", 909, "Unit E", "75319", "Greentown", "Countryland", 123456789, "ryan.taylor@email.com", "park456", "CUSTOMER"),
  ("OliviaBaker", "Olivia", "Baker", "Grove Street", 1010, NULL, "64237", "Woodville", "Countryland", 234567890, "olivia.baker@email.com", "grove789", "CUSTOMER"),
  ("JamesCooper", "James", "Cooper", "Harbor View", 1111, "House F", "52846", "Portville", "Countryland", 345678901, "james.cooper@email.com", "harbor123", "CUSTOMER"),
  ("Sophia69", "Sophia", "Ward", "Highland Avenue", 1212, NULL, "91735", "Hilltown", "Countryland", 456789012, "sophia.ward@email.com", "highland456", "CUSTOMER"),
  ("LiamFisher456", "Liam", "Fisher", "River Road", 1313, "Suite G", "36928", "Rivertown", "Countryland", 567890123, "liam.fisher@email.com", "river789", "CUSTOMER"),
  ("AvaMartin", "Ava", "Martin", "Meadow Lane", 1414, NULL, "86420", "Meadowtown", "Countryland", 678901234, "ava.martin@email.com", "meadow567", "CUSTOMER"),
  ("NoahBailey", "Noah", "Bailey", "Hillside Drive", 1515, "Unit H", "97531", "Hillville", "Countryland", 789012345, "noah.bailey@email.com", "hillside890", "CUSTOMER"),
  ("EmmaP", "Emma", "Perez", "Skyline Avenue", 1616, NULL, "24680", "Skytown", "Countryland", 890123456, "emma.perez@email.com", "skyline123", "CUSTOMER"),
  ("MillerJ", "Jackson", "Miller", "Breeze Lane", 1717, "Apartment I", "75319", "Breezetown", "Countryland", 901234567, "jackson.miller@email.com", "breeze456", "CUSTOMER");

INSERT INTO locations (ownerID, streetname, houseNumber, bus, postalCode, city, country, locationType) VALUES
  (1, "Volunteer Street", 111, "Suite V", "11111", "Volunteerville", "Countryland", "PRIVATE"),
  (3, "Museum Avenue", 222, NULL, "22222", "Museumtown", "Countryland", "MUSEUM"),
  (1, "Storage Lane", 333, "Unit S", "33333", "Storagetown", "Countryland", "STORAGE"),
  (4, "Liberty Road", 444, "Floor L", "44444", "Libraryville", "Countryland", "LIBRARY"),
  (2, "Expo Boulevard", 555, "Booth E", "55555", "Expoville", "Countryland", "EXPO"),
  (5, "Park Street", 123, NULL, "67890", "Parkville", "Countryland", "PRIVATE"),
  (7, "Gaming Avenue", 789, "Shop G", "98765", "Gamingville", "Countryland", "PRIVATE"),
  (8, "Retro Street", 987, NULL, "12345", "Retroville", "Countryland", "PRIVATE"),
  (9, "Player Lane", 654, "Gaming Tower", "56789", "Playertown", "Countryland", "PRIVATE"),
  (1, "Console Boulevard", 321, "Console Corner", "13579", "Consoletown", "Countryland", "PRIVATE"),
  (1, "Virtual Street", 111, "VR Suite", "24680", "Virtualville", "Countryland", "PRIVATE"),
  (1, "Pixel Avenue", 222, "Pixel House", "13579", "Pixeltown", "Countryland", "PRIVATE"),
  (1, "Quest Lane", 333, NULL, "98765", "Questville", "Countryland", "PRIVATE"),
  (1, "Strategy Road", 444, "Strategic Tower", "54321", "Strategytown", "Countryland", "PRIVATE"),
  (1, "Simulation Street", 555, "Sim House", "24680", "Simland", "Countryland", "PRIVATE"),
  (6, "Action Avenue", 666, NULL, "11111", "Actiontown", "Countryland", "PRIVATE"),
  (7, "Adventure Road", 777, "Adventure House", "22222", "Adventureville", "Countryland", "PRIVATE"),
  (8, "Fighting Lane", 888, "Fighter Tower", "33333", "Fightingtown", "Countryland", "PRIVATE"),
  (9, "Racing Street", 999, NULL, "44444", "Racingville", "Countryland", "PRIVATE"),
  (10, "Puzzle Avenue", 111, "Puzzle Corner", "55555", "Puzzletown", "Countryland", "PRIVATE");
  
INSERT INTO games (title, ownerID, homeBaseID, currentLocationId, releaseDate, genre) VALUES
  ("The Legend of Zelda: Link's Awakening", 1, 11, 11, "1993-06-06", "Action-Adventure"),
  ("Pokémon Red/Blue", 2, 12, 12, "1996-09-28", "Role-Playing"),
  ("Super Mario World", 3, 13, 13, "1990-11-21", "Platformer"),
  ("Tetris", 4, 14, 14, "1989-06-06", "Puzzle"),
  ("Metroid", 5, 11, 15, "1986-08-06", "Action-Adventure"),
  ("Donkey Kong Country", 6, 13, 16, "1994-11-21", "Platformer"),
  ("Final Fantasy Adventure", 7, 15, 15, "1991-06-28", "Action RPG"),
  ("Castlevania: Symphony of the Night", 8, 14, 14, "1997-10-02", "Action-Adventure"),
  ("Pac-Man", 9, 16, 17, "1980-05-22", "Arcade"),
  ("Mega Man 2", 10, 17, 16, "1988-12-24", "Platformer"),
  ("The Legend of Zelda: A Link to the Past", 11, 13, 13, "1991-04-13", "Action-Adventure"),
  ("Super Mario Land", 2, 1, 1, "1989-04-21", "Platformer"),
  ("Pokémon Yellow", 3, 12, 12, "1998-10-19", "Role-Playing"),
  ("Chrono Trigger", 4, 15, 15, "1995-03-11", "Role-Playing"),
  ("Kirby's Dream Land", 5, 1, 8, "1992-04-27", "Platformer"),
  ("Street Fighter II: The World Warrior", 6, 9, 10,"1991-02-06", "Fighting"),
  ("Sonic the Hedgehog 2", 7, 2, 2, "1992-11-24", "Platformer"),
  ("Final Fantasy Legends", 8, 12, 12, "1989-12-15", "Role-Playing"),
  ("Super Mario Bros.", 9, 13, 13, "1985-09-13", "Platformer"),
  ("Legend of Zelda: Oracle of Ages/Seasons", 2, 11, 11, "2001-05-14", "Action-Adventure"),
  ("Pokémon Gold/Silver", 1, 12, 12, "1999-11-21", "Role-Playing"),
  ("Super Mario Kart", 2, 13, 13, "1992-08-27", "Racing"),
  ("Castlevania: Aria of Sorrow", 3, 14, 14, "2003-05-06", "Action-Adventure"),
  ("Pac-Man Championship Edition", 4, 16, 16, "2007-06-06", "Arcade"),
  ("Mega Man X", 5, 17, 17, "1993-12-17", "Platformer"),
  ("The Legend of Zelda: Majora's Mask", 6, 11, 11, "2000-04-27", "Action-Adventure"),
  ("Super Mario Land 2: 6 Golden Coins", 7, 18, 18, "1992-10-21", "Platformer"),
  ("Pokémon Crystal", 8, 12, 12, "2000-12-14", "Role-Playing"),
  ("Final Fantasy VI", 9, 15, 15, "1994-04-02", "Role-Playing"),
  ("Sonic the Hedgehog 3", 3, 2, 2, "1994-02-02", "Platformer"),
  ("Donkey Kong", 1, 13, 13, "1981-07-09", "Arcade"),
  ("Metroid: Zero Mission", 2, 11, 15, "2004-02-09", "Action-Adventure"),
  ("Chrono Cross", 3, 15, 15, "1999-11-18", "Role-Playing"),
  ("Kirby's Adventure", 4, 8, 8, "1993-11-25", "Platformer"),
  ("Street Fighter II Turbo: Hyper Fighting", 5, 9, 9, "1992-07-11", "Fighting"),
  ("Sonic & Knuckles", 3, 12, 15, "1994-10-18", "Platformer"),
  ("Final Fantasy Tactics Advance", 3, 12, 12, "2003-02-14", "Tactical RPG"),
  ("Super Mario Bros. 3", 3, 13, 13, "1988-10-23", "Platformer"),
  ("Legend of Zelda: The Minish Cap", 3, 11, 11, "2004-11-04", "Action-Adventure"),
  ("Pokémon Ruby/Sapphire", 4, 12, 12, "2002-11-21", "Role-Playing"),
  ("Super Metroid", 1, 11, 15, "1994-03-19", "Action-Adventure"),
  ("Castlevania: Circle of the Moon", 4, 14, 14, "2001-06-11", "Action-Adventure"),
  ("Pac-Mania", 4, 16, 17, "1987-12-01", "Arcade"),
  ("Mega Man 3", 4, 17, 8, "1990-09-28", "Platformer");

INSERT INTO loan_receips (gameID, customerID, loanedDate, loanTermInDays, returnDate, fine) VALUES
  (1, 2, "2023-01-01", 14, "2023-01-15", 5.00),
  (3, 14, "2023-02-01", 7, "2023-02-08", NULL),
  (5, 12, "2023-03-01", 21, NULL, NULL),
  (7, 15, "2023-04-01", 30, NULL, NULL);
  
INSERT INTO "bridge_locations_volunteers" ("locationID", "volunteerID") VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (2, 3),
  (3, 3),
  (3, 4),
  (4, 4),
  (4, 5),
  (5, 5),
  (5, 6),
  (6, 6),
  (6, 7),
  (7, 7),
  (7, 8),
  (8, 8),
  (8, 9),
  (9, 9),
  (9, 10),
  (10, 10),
  (10, 1);
  
INSERT INTO "bridge_games_devcompanies" ("gameID", "devCompanyID") VALUES
  (1, 1),
  (2, 3),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 7),
  (8, 9),
  (9, 9),
  (10, 10),
  (11, 1),
  (12, 2),
  (13, 3),
  (14, 4),
  (15, 5),
  (16, 4),
  (16, 1),
  (17, 7),
  (18, 8),
  (19, 9),
  (20, 10),
  (21, 1),
  (22, 2),
  (23, 3),
  (24, 4),
  (25, 5),
  (26, 6),
  (27, 7),
  (28, 8),
  (29, 9),
  (30, 10),
  (31, 1),
  (32, 2),
  (33, 3),
  (34, 4),
  (35, 5),
  (36, 6),
  (37, 7),
  (38, 8),
  (39, 9),
  (40, 10),
  (41, 1),
  (42, 2),
  (43, 3),
  (44, 4);

INSERT INTO "bridge_games_consoles" ("gameID", "consoleID") VALUES
  (1, 1),  -- The Legend of Zelda: Link's Awakening - PlayStation
  (2, 3),  -- Pokémon Red/Blue - Nintendo Switch
  (3, 13),  -- Super Mario World - Game Boy
  (4, 14),  -- Tetris - Game Boy Color
  (5, 11),  -- Metroid - PlayStation
  (6, 13),  -- Donkey Kong Country - Nintendo Switch
  (7, 12),  -- Final Fantasy Adventure - Sega Genesis
  (8, 14),  -- Castlevania: Symphony of the Night - Game Boy Color
  (8, 14),  -- Castlevania: Symphony of the Night - Sega Genesis
  (9, 11),  -- Pac-Man - PlayStation
  (10, 10), -- Mega Man 2 - Xbox 360
  (11, 13), -- The Legend of Zelda: A Link to the Past - Nintendo Switch
  (12, 13), -- Super Mario Land - Game Boy
  (12, 14), -- Super Mario Land - Game Boy Color
  (13, 14), -- Pokémon Yellow - Game Boy Color
  (14, 9), -- Chrono Trigger - Super Nintendo Entertainment System (SNES)
  (14, 4), -- Chrono Trigger - Sega Genesis
  (16, 2), -- Street Fighter II: The World Warrior - Xbox 360
  (16, 1), -- Street Fighter II: The World Warrior - Super Nintendo Entertainment System (SNES)
  (17, 2), -- Sonic the Hedgehog 2 - Xbox 360
  (17, 2), -- Sonic the Hedgehog 2 - Super Nintendo Entertainment System (SNES)
  (18, 4), -- Final Fantasy Legends - Xbox 360
  (18, 8), -- Final Fantasy Legends - Super Nintendo Entertainment System (SNES)
  (19, 14), -- Super Mario Bros. - Super Nintendo Entertainment System (SNES)
  (20, 13), -- Super Mario Land 2: 6 Golden Coins - Game Boy
  (20, 14), -- Super Mario Land 2: 6 Golden Coins - Game Boy Color
  (20, 9), -- Super Mario Land 2: 6 Golden Coins - Xbox 360
  (20, 10), -- Super Mario Land 2: 6 Golden Coins - Super Nintendo Entertainment System (SNES)
  (21, 9), -- Pokémon Gold/Silver - Sega Dreamcast
  (21, 3),  -- Pokémon Gold/Silver - Nintendo Switch
  (22, 13), -- Super Mario Kart - Game Boy
  (22, 14), -- Super Mario Kart - Game Boy Color
  (22, 13), -- Super Mario Kart - Nintendo Switch
  (22, 3),  -- Super Mario Kart - Nintendo Switch
  (23, 14), -- Castlevania: Aria of Sorrow - Game Boy Color
  (23, 3),  -- Castlevania: Aria of Sorrow - Nintendo Switch
  (24, 6), -- Pac-Man Championship Edition - Xbox 360
  (24, 2), -- Pac-Man Championship Edition - Super Nintendo Entertainment System (SNES)
  (25, 7), -- Mega Man X - Xbox 360
  (25, 10), -- Mega Man X - Super Nintendo Entertainment System (SNES)
  (26, 11), -- The Legend of Zelda: Majora's Mask - PlayStation
  (26, 13), -- The Legend of Zelda: Majora's Mask - Nintendo Switch
  (27, 8), -- Super Mario Land 2: 6 Golden Coins - Game Boy Color
  (27, 13), -- Super Mario Land 2: 6 Golden Coins - Nintendo Switch
  (27, 10), -- Super Mario Land 2: 6 Golden Coins - Super Nintendo Entertainment System (SNES)
  (28, 12), -- Pokémon Crystal - Nintendo 3DS
  (28, 13), -- Pokémon Crystal - Nintendo Switch
  (29, 5), -- Final Fantasy VI - Sega Genesis
  (29, 3),  -- Final Fantasy VI - Nintendo Switch
  (30, 10), -- Sonic the Hedgehog 3 - Super Nintendo Entertainment System (SNES)
  (31, 13), -- Donkey Kong - Nintendo Switch
  (32, 11), -- Metroid: Zero Mission - PlayStation
  (33, 5), -- Chrono Cross - Sega Genesis
  (34, 8), -- Kirby's Adventure - Game Boy Color
  (34, 13), -- Kirby's Adventure - Nintendo Switch
  (35, 9), -- Street Fighter II Turbo: Hyper Fighting - Sega Dreamcast
  (36, 10), -- Sonic & Knuckles - Super Nintendo Entertainment System (SNES)
  (37, 12), -- Final Fantasy Tactics Advance - Nintendo 3DS
  (37, 13), -- Final Fantasy Tactics Advance - Nintendo Switch
  (38, 13), -- Super Mario Bros. 3 - Nintendo Switch
  (39, 11), -- Legend of Zelda: The Minish Cap - PlayStation
  (39, 13), -- Legend of Zelda: The Minish Cap - Nintendo Switch
  (40, 12), -- Pokémon Ruby/Sapphire - Nintendo 3DS
  (40, 13), -- Pokémon Ruby/Sapphire - Nintendo Switch
  (41, 11), -- Super Metroid - PlayStation
  (41, 13), -- Super Metroid - Nintendo Switch
  (42, 14), -- Castlevania: Circle of the Moon - Game Boy Color
  (43, 6), -- Pac-Mania - Xbox 360
  (43, 2), -- Pac-Mania - Super Nintendo Entertainment System (SNES)
  (44, 7), -- Mega Man 3 - Xbox 360
  (44, 2); -- Mega Man 3 - Super Nintendo Entertainment System (SNES)
