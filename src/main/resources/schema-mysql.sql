use carRentalDb;

# Creation of tables and users to support user login.
# ---------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;


create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    role varchar(50) not null);

insert into users(username, password, role)values('admin','admin','ADMINISTRATOR');
insert into users(username, password, role)values('clerk','clerk','USER');

# Creation of client table
#----------------------------------------------------------------------------------------------------------------------
 CREATE TABLE IF NOT EXISTS `carrentaldb`.`clientRecord` (
                    `driversLicenseNumber` varchar(60) Primary key,
                    `version` INT,
                    `firstname` VARCHAR(60),
                    `lastname` VARCHAR(50),
                    `phoneNumber` VARCHAR(50),
                    `expirationDate` date
                );

INSERT INTO `carrentaldb`.`clientrecord` (`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) VALUES ('A-1234-123456-45', '1.0', 'Bruce', 'Wayne', '4387281488', '2040-09-12');
INSERT INTO `carrentaldb`.`clientrecord` (`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) VALUES ('A-1234-123456-31', '1.0', 'Barry', 'Allen', '4387281488', '3022-09-12');
INSERT INTO `carrentaldb`.`clientrecord` (`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) VALUES ('A-1234-123456-23', '1.0', 'Tony','Stark', '4387281488', '2021-09-12');
INSERT INTO `carrentaldb`.`clientrecord` (`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) VALUES ('A-1234-123456-78', '1.0', 'Clark','Kent', '4387281488', '2025-09-12');
INSERT INTO `carrentaldb`.`clientrecord` (`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) VALUES ('A-1234-123456-63', '1.0', 'Walter','White', '4387281488', '20222-09-12');