
--customer by name lookup index
-- Fast lookup by first and last name queries 11 and 12
CREATE INDEX idx_customer_fname_lname 
ON Customer(fName, lName);

-- Filter by maintenance company (query 14)
CREATE INDEX idx_repair_mcompany 
ON Repair(mCompany);

-- Filter by hotel and room (query 16)
CREATE INDEX idx_repair_hotel_room_date 
ON Repair(hotelID, roomNo, repairDate);

-- Lookup by name (query 14)
CREATE INDEX idx_maintenancecompany_name 
ON MaintenanceCompany(name);

-- Join with Repair and filter (queries 16, 18)
CREATE INDEX idx_request_repairid 
ON Request(repairID);
