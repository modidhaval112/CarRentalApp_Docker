# Clear the database and start fresh.
# ---------------------------------------------------------------------------------------------------------------------

DROP DATABASE IF EXISTS carRentalDb;
CREATE DATABASE carRentalDb;
use carRentalDb;

# "Creation of tables and users to support user login."
# ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    role varchar(50) not null);
    
# "Creation of tables for the vehicles."
# ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS vehicleRecord   ( 
                    licensePlateNumber VARCHAR(50) PRIMARY KEY, 
                    id INT, 
                    version INT, 
                    carType VARCHAR(60), 
                    make VARCHAR(50), 
                    model VARCHAR(50), 
                    year INT ,
                    color VARCHAR(50)
				);

# "Creation table for clients."
#---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS clientRecord   ( 
                    driversLicenseNumber VARCHAR(50) PRIMARY KEY, 
                    version INT, 
                    firstName VARCHAR(60), 
                    lastName VARCHAR(50), 
                    phoneNumber VARCHAR(50), 
                    expirationDate Date 
                );

# "Creation table for transaction."
#---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS transaction   ( 
                    transactionId VARCHAR(100) PRIMARY KEY, 
                    version INT, 
                    status VARCHAR(50), 
                    startDate Date ,
                    endDate Date,
                    licensePlateNumber VARCHAR(50),
                    driversLicenseNumber VARCHAR(50),
					CONSTRAINT fk_vehcilerecord FOREIGN KEY (licensePlateNumber) references vehiclerecord(licensePlateNumber),
					CONSTRAINT fk_clientrecord FOREIGN KEY (driversLicenseNumber) references clientrecord(driversLicenseNumber)
                );

# "Creation table for transaction logs."
#---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS transactionHistory   ( 
                    transactionId VARCHAR(100) ,
                    vehicleType VARCHAR(60),
                    model VARCHAR(60),
                    lpr VARCHAR(60),
                    clientName VARCHAR(60),
                    startDate Date,
                    endDate Date,
                    status VARCHAR(60),
                    timestamp VARCHAR(60),
                    color VARCHAR(60),
                    make VARCHAR(60),
                    year INT
                    
                );
#----------------------------------------------------------------------------------------------------------------------
# Inserting default data in tables.
#----------------------------------------------------------------------------------------------------------------------                
                
# Users  
#----------------------------------------              
insert into users(username, password, role)values('admin','admin','ADMINISTRATOR');
insert into users(username, password, role)values('admin1','admin1','ADMINISTRATOR');
insert into users(username, password, role)values('clerk','clerk','USER');
insert into users(username, password, role)values('clerk1','clerk1','USER');

# Clients
#----------------------------------------

INSERT INTO clientRecord (`driversLicenseNumber`, `version`, `firstName`, `lastName`, `phoneNumber`, `expirationDate`) VALUES ("A-1234-123456-12", "1", "Dominick", "Cobb",    "(438) 566-9999", "2039-10-1");
INSERT INTO clientRecord (`driversLicenseNumber`, `version`, `firstName`, `lastName`, `phoneNumber`, `expirationDate`) VALUES ("A-1234-123456-13", "1", "Robert",   "Fischer", "(438) 566-9999", "2029-11-1");
INSERT INTO clientRecord (`driversLicenseNumber`, `version`, `firstName`, `lastName`, `phoneNumber`, `expirationDate`) VALUES ("A-1234-123456-14", "1", "Mal",      "Cobb",    "(438) 566-9999", "2029-12-1");
INSERT INTO clientRecord (`driversLicenseNumber`, `version`, `firstName`, `lastName`, `phoneNumber`, `expirationDate`) VALUES ("A-1234-123456-15", "1", "Stephen",  "Miles",   "(438) 566-9999", "2059-11-1");
INSERT INTO clientRecord (`driversLicenseNumber`, `version`, `firstName`, `lastName`, `phoneNumber`, `expirationDate`) VALUES ("A-1234-123456-16", "1", "Ariadne",  "Fischer", "(438) 566-9999", "2079-11-1");

# Vehicles
#----------------------------------------

INSERT INTO vehicleRecord (`licensePlateNumber`, `id`, `version`, `carType`, `make`, `model`, `year`, `color`) VALUES ("ABD_636", "1", "1", "SUV", "Jeep", "Mercedes Rover", 2019, "Gold");
INSERT INTO vehicleRecord (`licensePlateNumber`, `id`, `version`, `carType`, `make`, `model`, `year`, `color`) VALUES ("UDF_126", "2", "1", "SUV", "Jeep", "Hummer", 2019, "Yellow");
INSERT INTO vehicleRecord (`licensePlateNumber`, `id`, `version`, `carType`, `make`, `model`, `year`, `color`) VALUES ("ABE_636", "3", "1", "Sedan", "Audi", "A8", 2011, "Red");
INSERT INTO vehicleRecord (`licensePlateNumber`, `id`, `version`, `carType`, `make`, `model`, `year`, `color`) VALUES ("ABF_636", "4", "1", "Sedan", "Audi", "Q7", 2014, "Black");

# Transactions
#----------------------------------------
