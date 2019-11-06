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
