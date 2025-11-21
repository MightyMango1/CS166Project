DROP TABLE IF EXISTS HOTEL, STAFF, MANAGER, HOUSE_CLEANING, ROOM, ASSIGNED, CUSTOMER, BOOKING, MAINTENANCE_COMPANY, CASCADE;


CREATE TABLE HOTEL (
    hotelID INT PRIMARY KEY,
    name TEXT,
    address TEXT,
    managerID INT
);

-- one (hotel) to many (staff) relationship 
CREATE TABLE STAFF (
    SSN INT PRIMARY KEY, -- is this a primary key (?) doesn't show up on the ER diagram
    fname TEXT,
    lname TEXT,
    position TEXT,
    address TEXT,
    hotelID INT,
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

CREATE TABLE ROOM (
    roomNo INT,
    roomType TEXT,

    hotelID INT NOT NULL,
    PRIMARY KEY (hotelID, roomNo),
    FOREIGN KEY (hotelID) REFERENCES HOTEL(hotelID) ON DELETE CASCADE,

    -- repair relationship attributes
    repairType TEXT,
    repairDate DATE,
    description TEXT,
    companyID INT,
    FOREIGN KEY (companyID) REFERENCES MAINTENANCE_COMPANY(companyID) ON DELETE CASCADE
); --not sure if a room would have multiple repairs, but if it does, we can just add a separate table for repairs and link it to the room with a foreign key. should discuss with professor!

-- assigned junction table for housecleaning and room
CREATE TABLE ASSIGNED (
    roomNo INT,
    hotelID INT,
    houseCleanID INT,
    PRIMARY KEY (hotelID, roomNo, houseCleanID),
    FOREIGN KEY (hotelID, roomNo) REFERENCES ROOM(hotelID, roomNo) ON DELETE CASCADE,
    FOREIGN KEY (houseCleanID) REFERENCES HOUSE_CLEANING(SSN) ON DELETE CASCADE
);

CREATE TABLE CUSTOMER (
    customerID INT PRIMARY KEY,
    fname TEXT,
    lname TEXT,
    gender TEXT,
    address TEXT,
    phNo TEXT,
    DOB DATE
);

-- many (customer) to many (room) relationship junction table
CREATE TABLE BOOKING (
    bookingDate DATE,
    numPeople INT,
    price INT,
    hotelID INT,

    customerID INT,
    roomNo INT,
    PRIMARY KEY (customerID, hotelID, roomNo, bookingDate),
    FOREIGN KEY (customerID) REFERENCES CUSTOMER(customerID) ON DELETE CASCADE,
    FOREIGN KEY (hotelID, roomNo) REFERENCES ROOM(hotelID, roomNo) ON DELETE CASCADE
); --updated the foreign keys here, since that room key is comprosied of roomno and hotelid
--any single maintenance company could be handling multiple requests. so i think we should have
--a separate table since if we make it part of the maintenance company table, we would
--have dupes of the company instance for each request they're handling if that makes sense