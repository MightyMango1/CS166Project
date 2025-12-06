
--customer by name lookup index queries 11 and 12
CREATE INDEX idx_customer_fname_lname 
ON Customer(fName, lName);

--maintenance company id lookup query 14
CREATE INDEX idx_repair_mcompany 
ON Repair(mCompany);

--hotela and room filter query 16
CREATE INDEX idx_repair_hotel_room_date 
ON Repair(hotelID, roomNo, repairDate);

-- Lookup mcomp name query 14
CREATE INDEX idx_maintenancecompany_name 
ON MaintenanceCompany(name);

-- Join with Repair and filter (queries 16, 18)
CREATE INDEX idx_request_repairid 
ON Request(repairID);
