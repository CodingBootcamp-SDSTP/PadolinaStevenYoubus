CREATE TABLE IF NOT EXISTS tbdrivers(
	driverid int PRIMARY KEY AUTO_INCREMENT,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	birthdate date NOT NULL,
	sex text NOT NULL,
	dlicense VARCHAR(50) NULL
);

CREATE TABLE IF NOT EXISTS tbconductors(
	conductorid int PRIMARY KEY AUTO_INCREMENT,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	birthdate date NOT NULL,
	sex text NOT NULL
);

CREATE TABLE IF NOT EXISTS tbbuses(
	busid int PRIMARY KEY AUTO_INCREMENT,
	busname text NOT NULL,
	plateno text NOT NULL,
	driverid int NOT NULL,
	conductorid int NOT NULL,
	capacity int NOT NULL
);

CREATE TABLE IF NOT EXISTS tblocations(
	locationid int PRIMARY KEY AUTO_INCREMENT,
	locationname VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbroutes(
	routeid int PRIMARY KEY AUTO_INCREMENT,
	routename VARCHAR(50),
	origin int NOT NULL,
	destination int NOT NULL,
	distance int NOT NULL,
	duration int NOT NULL,
	rate VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbtrips(
	tripid int PRIMARY KEY AUTO_INCREMENT,
	routeid int NOT NULL,
	busid int NOT NULL,
	tripdate date NOT NULL,
	departure time NOT NULL,
	tripstatus VARCHAR(10) DEFAULT "pending"
);

CREATE TABLE IF NOT EXISTS tbbookings(
	bookingid int PRIMARY KEY AUTO_INCREMENT,
	tripid int NOT NULL
);

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewbuses AS SELECT tbbuses.busid AS busid, tbbuses.busname AS busname, tbbuses.plateno AS plateno, CONCAT(tbdrivers.firstname, " ", tbdrivers.lastname) AS driver, CONCAT(tbconductors.firstname, " ", tbconductors.lastname) AS conductor, tbbuses.capacity AS capacity from (tbbuses join tbdrivers on (tbbuses.driverid = tbdrivers.driverid) join tbconductors on (tbbuses.conductorid = tbconductors.conductorid));

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewroutes AS SELECT a.routeid AS routeid, a.routename AS routename, a.distance AS distance, a.duration AS duration, a.rate AS rate, a.origin AS origin, b.destination AS destination from (SELECT tbroutes.routeid AS routeid, tbroutes.routename AS routename, CONCAT(tblocations.locationname, ", ", tblocations.city) AS origin, tbroutes.distance AS distance, tbroutes.duration AS duration, tbroutes.rate AS rate from (tbroutes join tblocations on (tbroutes.origin = tblocations.locationid))) as a JOIN (SELECT tbroutes.routeid AS routeid, CONCAT(tblocations.locationname, ", ", tblocations.city) AS destination from (tbroutes join tblocations on (tbroutes.destination = tblocations.locationid))) AS b on (a.routeid = b.routeid);
	
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=rufe05@localhost
	SQL SECURITY DEFINER VIEW viewtrips AS SELECT tbtrips.tripid AS tripid, viewbuses.busname AS busname, viewbuses.driver AS driver, viewbuses.conductor AS conductor, viewroutes.origin AS origin, viewroutes.destination AS destination, tbtrips.tripdate as tripdate, tbtrips.departure AS departure, viewroutes.duration AS duration, viewroutes.rate AS rate, tbtrips.tripstatus as tripstatus from (tbtrips join viewbuses on (tbtrips.busid = viewbuses.busid) join viewroutes on (tbtrips.routeid = viewroutes.routeid));
