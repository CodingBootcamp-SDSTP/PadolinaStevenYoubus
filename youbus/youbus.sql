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
	bustype int NOT NULL
);

CREATE TABLE IF NOT EXISTS tbbustypes(
	bustypeid int PRIMARY KEY AUTO_INCREMENT,
	bustypename text NOT NULL,
	aircon boolean NOT NULL,
	restroom boolean NOT NULL,
	capacity int NOT NULL
);

INSERT INTO tbbustypes ( bustypename, aircon, restroom, capacity ) values ( "Executive Coach 1", true, true, 26 );

INSERT INTO tbbustypes ( bustypename, aircon, restroom, capacity ) values ( "Executive Coach 2", true, true, 45 );

INSERT INTO tbbustypes ( bustypename, aircon, restroom, capacity ) values ( "Deluxe Coach", true, true, 55 );

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

INSERT INTO tbroutes ( routename, origin, destination, distance, duration, rate ) values ( "Sta. Rosa - Makati", 1, 2, 70, 60, 59 );

INSERT INTO tbroutes ( routename, origin, destination, distance, duration, rate ) values ( "Makati - Sta. Rosa", 2, 1, 70, 60, 59 );

CREATE TABLE IF NOT EXISTS tbtrips(
	tripid int PRIMARY KEY AUTO_INCREMENT,
	tripname VARCHAR(50) NOT NULL,
	routeid int NOT NULL,
	busid int NOT NULL,
	tripdate date NOT NULL,
	departure time NOT NULL,
	availableseats int NOT NULL,
	tripstatus VARCHAR(10) DEFAULT "pending"
);

-- INSERT INTO tbtrips ( routeid, tripname, busid, tripdate, departure, availableseats ) values ( 1, "Trip 1", 2, "2018-06-21", "17:45:00", ( SELECT capacity FROM viewbuses WHERE busid=2) );

-- INSERT INTO tbtrips ( routeid, tripname, busid, tripdate, departure, availableseats ) values ( 1, "Trip 2", 3, "2018-06-21", "18:25:00", ( SELECT capacity FROM viewbuses WHERE busid=3) );

-- INSERT INTO tbtrips ( routeid, tripname, busid, tripdate, departure, availableseats ) values ( 2, "Trip 3", 1, "2018-06-21", "17:45:00", ( SELECT capacity FROM viewbuses WHERE busid=1) );

CREATE TABLE IF NOT EXISTS tbbusseats(
	seatid int PRIMARY KEY AUTO_INCREMENT,
	busid int NOT NULL,
	seatno VARCHAR(20) NULL,
	occupied boolean DEFAULT false
);

CREATE TABLE IF NOT EXISTS tbbookings(
	bookingid int PRIMARY KEY AUTO_INCREMENT,
	tripid int NOT NULL,
	busid int NOT NULL,
	seatid int NOT NULL,
	userid int NOT NULL
);

CREATE TABLE IF NOT EXISTS tbuseraccounts(
	accountid int PRIMARY KEY AUTO_INCREMENT,
	userid int NOT NULL,
	email VARCHAR(255) NOT NULL,
	username VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(50) NOT NULL,
	type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbusers(
	userid int PRIMARY KEY AUTO_INCREMENT,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	sex VARCHAR(10) NOT NULL,
	birthdate date NOT NULL
);

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewbookings AS SELECT tbbookings.bookingid as bookingid, tbtrips.tripid as tripid, tbtrips.tripname AS tripname, tbbuses.busname AS busname, tbbusseats.seatno AS seatno, CONCAT(tbusers.firstname, " ", tbusers.lastname) AS passenger FROM ((((tbbookings join tbtrips on ( tbbookings.tripid = tbtrips.tripid )) join tbbuses on ( tbbookings.busid = tbbuses.busid )) join tbusers on ( tbusers.userid = tbbookings.userid )) join tbbusseats on (tbbookings.seatid = tbbusseats.seatid));

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewbusseats AS SELECT tbbusseats.seatid AS seatid, tbbuses.busname AS busname, tbbusseats.seatno AS seatno, tbbusseats.occupied AS occupied from (tbbusseats join tbbuses on (tbbusseats.busid = tbbuses.busid));

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewbuses AS SELECT tbbuses.busid AS busid, tbbuses.busname AS busname, tbbuses.plateno AS plateno, CONCAT(tbdrivers.firstname, " ", tbdrivers.lastname) AS driver, CONCAT(tbconductors.firstname, " ", tbconductors.lastname) AS conductor, tbbustypes.bustypename AS bustype, tbbustypes.aircon AS aircon, tbbustypes.restroom AS restroom, tbbustypes.capacity as capacity from (tbbuses join tbdrivers on (tbbuses.driverid = tbdrivers.driverid) join tbconductors on (tbbuses.conductorid = tbconductors.conductorid) join tbbustypes on (tbbustypes.bustypeid = tbbuses.bustype));

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewroutes AS SELECT a.routeid AS routeid, a.routename AS routename, a.distance AS distance, a.duration AS duration, a.rate AS rate, a.origin AS origin, b.destination AS destination from (SELECT tbroutes.routeid AS routeid, tbroutes.routename AS routename, CONCAT(tblocations.locationname, ", ", tblocations.city) AS origin, tbroutes.distance AS distance, tbroutes.duration AS duration, tbroutes.rate AS rate from (tbroutes join tblocations on (tbroutes.origin = tblocations.locationid))) as a JOIN (SELECT tbroutes.routeid AS routeid, CONCAT(tblocations.locationname, ", ", tblocations.city) AS destination from (tbroutes join tblocations on (tbroutes.destination = tblocations.locationid))) AS b on (a.routeid = b.routeid);
	
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewtrips AS SELECT tbtrips.tripid AS tripid, tbtrips.tripname AS tripname, viewbuses.busname AS busname, viewbuses.busid AS busid, viewbuses.driver AS driver, viewbuses.conductor AS conductor, viewroutes.origin AS origin, viewroutes.destination AS destination, tbtrips.tripdate as tripdate, tbtrips.departure AS departure, viewroutes.duration AS duration, viewroutes.rate AS rate, viewbuses.capacity AS capacity, tbtrips.availableseats AS availableseats, tbtrips.tripstatus as tripstatus from (tbtrips join viewbuses on (tbtrips.busid = viewbuses.busid) join viewroutes on (tbtrips.routeid = viewroutes.routeid));

