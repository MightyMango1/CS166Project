DROP TABLE IF EXISTS ... CASCADE;


CREATE TABLE HOTEL (
    int hotelid PRIMARY KEY,
    TEXT name,
    TEXT address,

);

-- one (hotel) to many (staff) relationship 
CREATE TABLE STAFF (
    int SSN PRIMARY KEY, -- is this a primary key (?) doesn't show up on the ER diagram
    TEXT fname,
    TEXT lname,
    TEXT position,
    TEXT address
    
    int hotelid
    FOREIGN KEY (hotelid) REFERENCES HOTEL(hotelid) ON DELETE CASCADE
);

CREATE TABLE ROOM (


);
