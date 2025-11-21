DROP TABLE IF EXISTS ... CASCADE;


CREATE TABLE HOTEL (
    hotelID INT PRIMARY KEY,
    name TEXT,
    address TEXT,
    managerID INT,
);

-- one (hotel) to many (staff) relationship 
CREATE TABLE STAFF (
    SSN INT PRIMARY KEY, -- is this a primary key (?) doesn't show up on the ER diagram
    fname TEXT,
    lname TEXT,
    position TEXT,
    address TEXT,
    int hotelID,
    FOREIGN KEY (hotelID) REFERENCES HOTEL(hotelID) ON DELETE CASCADE
);

CREATE TABLE MANAGER (
    SSN INT PRIMARY KEY,
    FOREIGN KEY (SSN) REFERENCES STAFF(SSN) ON DELETE CASCADE
); -- just doing subtypes as a reference to staff entity

CREATE TABLE HOUSE_CLEANING (
    SSN INT PRIMARY KEY,
    FOREIGN KEY (SSN) REFERENCES STAFF(SSN) ON DELETE CASCADE
); -- just doing subtypes as a reference to staff entity

CREATE TABLE ROOM (
    roomNo INT,
    roomType TEXT,

    hotelID INT NOT NULL,
    PRIMARY KEY (roomNo, hotelId),
    FOREIGN KEY (hotelId) REFERENCES HOTEL(hotelID) ON DELETE CASCADE,

    -- repair relationship attributes
    repairType TEXT,
    repairDate DATE
    description TEXT,
    companyID INT,
    FOREIGN KEY (companyID) REFERENCES COMPANY(companyID) ON DELETE CASCADE,

    -- assigned relationship
    managerID INT
    FOREIGN KEY (managerID) REFERENCES MANAGER(SSN) ON DELETE CASCADE,
);

CREATE TABLE CUSTOMER (
    customerID INT PRIMARY KEY,
    fname TEXT,
    lname TEXT,
    gender TEXT,
    address TEXT,
    phNo TEXT,
    DOB DATE,
);

-- many (customer) to many (room) relationship junction table
CREATE TABLE BOOKING (
    bookingDate DATE,
    numPeople INT,
    price INT,

    customerID INT,
    roomID INT,
    PRIMARY KEY (customerID, hotelID, roomNo, bookingDate),
    FOREIGN KEY (customerID) REFERENCES CUSTOMER(customerID) ON DELETE CASCADE,
    FOREIGN KEY (hotelID, roomNo) REFERENCES ROOM(hotelID, roomNo) ON DELETE CASCADE
); --updated the foreign keys here, since that room key is comprosied of roomno and hotelid

CREATE TABLE MAINTENANCE_COMPANY (
    companyID INT PRIMARY KEY,
    name TEXT, 
    address TEXT,
    certified BOOLEAN,

    -- request relationship attributes
    requestDate DATE,
    requestDescription TEXT,
    managerID INT,
    FOREIGN KEY (managerID) REFERENCES MANAGER(SSN) ON DELETE CASCADE

);