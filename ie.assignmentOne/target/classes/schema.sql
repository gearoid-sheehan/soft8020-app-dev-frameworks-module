CREATE TABLE households (
	householdId int NOT NULL AUTO_INCREMENT, 
	eircode varchar(255) NOT NULL, 
	address varchar(255) NOT NULL,
	PRIMARY KEY (householdId)
);

CREATE TABLE occupants (
  occupantId int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  age int NOT NULL,
  occupation varchar(255) NOT NULL,
  occ_eircode varchar(255),
  PRIMARY KEY (occupantId),
  FOREIGN KEY (occ_eircode) REFERENCES households(eircode) ON DELETE CASCADE
);