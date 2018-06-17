CREATE TABLE IF NOT EXISTS tbdrivers(
	driverid int PRIMARY KEY AUTO_INCREMENT,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	birthdate date NOT NULL,
	sex text NOT NULL,
	dlicense VARCHAR(50) NULL
);

INSERT INTO tbdrivers ( firstname, lastname, birthdate, sex, dlicense ) values ( "Bus", "Driver1", "1983-04-12", "male", "license 1" );

INSERT INTO tbdrivers ( firstname, lastname, birthdate, sex, dlicense ) values ( "Bus", "Driver2", "1980-06-27", "male", "license 2" );

INSERT INTO tbdrivers ( firstname, lastname, birthdate, sex, dlicense ) values ( "Bus", "Driver3", "1983-01-02", "male", "license 3" );

INSERT INTO tbdrivers ( firstname, lastname, birthdate, sex, dlicense ) values ( "Bus", "Driver4", "1979-04-22", "male", "license 4" );

INSERT INTO tbdrivers ( firstname, lastname, birthdate, sex, dlicense ) values ( "Bus", "Driver5", "1988-10-10", "male", "license 5" );

CREATE TABLE IF NOT EXISTS tbconductors(
	conductorid int PRIMARY KEY AUTO_INCREMENT,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	birthdate date NOT NULL,
	sex text NOT NULL
);

INSERT INTO tbconductors ( firstname, lastname, birthdate, sex ) values ( "Bus", "Conductor1", "1983-04-12", "male" );

INSERT INTO tbconductors ( firstname, lastname, birthdate, sex ) values ( "Bus", "Conductor2", "1980-06-27", "male" );

INSERT INTO tbconductors ( firstname, lastname, birthdate, sex ) values ( "Bus", "Conductor3", "1983-01-02", "male" );

INSERT INTO tbconductors ( firstname, lastname, birthdate, sex ) values ( "Bus", "Conductor4", "1979-04-22", "male" );

INSERT INTO tbconductors ( firstname, lastname, birthdate, sex ) values ( "Bus", "Conductor5", "1988-10-10", "male" );

CREATE TABLE IF NOT EXISTS tbbuses(
	busid int PRIMARY KEY AUTO_INCREMENT,
	busname text NOT NULL,
	plateno text NOT NULL,
	driverid int NOT NULL,
	conductorid int NOT NULL,
	capacity int NOT NULL
);

INSERT INTO tbbuses ( busname, plateno, driverid, conductorid, capacity ) values ( "Bus 1", "Plate No 1", 1, 1, 60 );

INSERT INTO tbbuses ( busname, plateno, driverid, conductorid, capacity ) values ( "Bus 2", "Plate No 2", 2, 3, 60 );

INSERT INTO tbbuses ( busname, plateno, driverid, conductorid, capacity ) values ( "Bus 3", "Plate No 3", 3, 2, 50 );

INSERT INTO tbbuses ( busname, plateno, driverid, conductorid, capacity ) values ( "Bus 4", "Plate No 4", 4, 4, 40 );

CREATE TABLE IF NOT EXISTS tblocations(
	locationid int PRIMARY KEY AUTO_INCREMENT,
	locationname VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL
);

INSERT INTO tblocations ( locationname, city ) values ( "JAC Terminal Balibago", "Sta. Rosa" );

INSERT INTO tblocations ( locationname, city ) values ( "JAC Terminal Buendia", "Makati" );

CREATE TABLE IF NOT EXISTS tbroutes(
	routeid int PRIMARY KEY AUTO_INCREMENT,
	routename VARCHAR(50),
	origin int NOT NULL,
	destination int NOT NULL,
	distance int NOT NULL,
	duration int NOT NULL,
	rate VARCHAR(10) NOT NULL
);

INSERT INTO tbroutes ( routename, origin, destination, distance, duration, rate ) values ( "Route 1", 1, 2, 70, 60, 59 );

INSERT INTO tbroutes ( routename, origin, destination, distance, duration, rate ) values ( "Route 2", 2, 1, 70, 60, 59 );

CREATE TABLE IF NOT EXISTS tbtrips(
	tripid int PRIMARY KEY AUTO_INCREMENT,
	routeid int NOT NULL,
	busid int NOT NULL,
	tripdate date NOT NULL,
	departure time NOT NULL,
	availableseats int NOT NULL,
	tripstatus VARCHAR(10) DEFAULT "pending"
);

INSERT INTO tbtrips ( routeid, busid, tripdate, departure, availableseats ) values ( 1, 2, "2018-06-21", "17:45:00", ( SELECT capacity FROM tbbuses WHERE busid=2) );

INSERT INTO tbtrips ( routeid, busid, tripdate, departure, availableseats ) values ( 1, 3, "2018-06-21", "18:25:00", ( SELECT capacity FROM tbbuses WHERE busid=3) );

INSERT INTO tbtrips ( routeid, busid, tripdate, departure, availableseats ) values ( 2, 1, "2018-06-21", "17:45:00", ( SELECT capacity FROM tbbuses WHERE busid=1) );

CREATE TABLE IF NOT EXISTS tbbookings(
	bookingid int PRIMARY KEY AUTO_INCREMENT,
	tripid int NOT NULL
);

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewbuses AS SELECT tbbuses.busid AS busid, tbbuses.busname AS busname, tbbuses.plateno AS plateno, CONCAT(tbdrivers.firstname, " ", tbdrivers.lastname) AS driver, CONCAT(tbconductors.firstname, " ", tbconductors.lastname) AS conductor, tbbuses.capacity AS capacity from (tbbuses join tbdrivers on (tbbuses.driverid = tbdrivers.driverid) join tbconductors on (tbbuses.conductorid = tbconductors.conductorid));

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewroutes AS SELECT a.routeid AS routeid, a.routename AS routename, a.distance AS distance, a.duration AS duration, a.rate AS rate, a.origin AS origin, b.destination AS destination from (SELECT tbroutes.routeid AS routeid, tbroutes.routename AS routename, CONCAT(tblocations.locationname, ", ", tblocations.city) AS origin, tbroutes.distance AS distance, tbroutes.duration AS duration, tbroutes.rate AS rate from (tbroutes join tblocations on (tbroutes.origin = tblocations.locationid))) as a JOIN (SELECT tbroutes.routeid AS routeid, CONCAT(tblocations.locationname, ", ", tblocations.city) AS destination from (tbroutes join tblocations on (tbroutes.destination = tblocations.locationid))) AS b on (a.routeid = b.routeid);
	
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewtrips AS SELECT tbtrips.tripid AS tripid, viewbuses.busname AS busname, viewbuses.driver AS driver, viewbuses.conductor AS conductor, viewroutes.origin AS origin, viewroutes.destination AS destination, tbtrips.tripdate as tripdate, tbtrips.departure AS departure, viewroutes.duration AS duration, viewroutes.rate AS rate, tbtrips.availableseats AS availableseats, tbtrips.tripstatus as tripstatus from (tbtrips join viewbuses on (tbtrips.busid = viewbuses.busid) join viewroutes on (tbtrips.routeid = viewroutes.routeid));

