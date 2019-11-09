use carRentalDb;

# "Creation of tables and users to support user login."
# ---------------------------------------------------------------------------------------------------------------------






# "Creation of tables for the vehicles."
# ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS carrentaldb.  vehicleRecord   ( 
                    licensePlateNumber VARCHAR(50) PRIMARY KEY, 
                    id INT, 
                    version INT, 
                    carType VARCHAR(60), 
                    make VARCHAR(50), 
                    model VARCHAR(50), 
                    year INT ,
                    color VARCHAR(50));



# "Creation table for clients."
#---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS carrentaldb. clientRecord   ( 
                    driversLicenseNumber VARCHAR(50) PRIMARY KEY, 
                    version INT, 
                    firstname VARCHAR(60), 
                    lastname VARCHAR(50), 
                    phoneNumber VARCHAR(50), 
                    expirationDate Date 
                );




# "Creation table for transaction."
#---------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS carrentaldb.  transaction   ( 
                    transactionId VARCHAR(50) PRIMARY KEY, 
                    version INT, 
                    status VARCHAR(50), 
                    startDate Date ,
                    endDate Date,
                    licensePlateNumber VARCHAR(50),
                    driversLicenseNumber VARCHAR(50),
					CONSTRAINT fk_vehcilerecord FOREIGN KEY (licensePlateNumber) references vehiclerecord(licensePlateNumber),
					CONSTRAINT fk_clientrecord FOREIGN KEY (driversLicenseNumber) references clientrecord(driversLicenseNumber)


                );