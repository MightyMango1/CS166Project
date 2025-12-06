/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science  &  Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");
            System.out.println("18. List requests for a maintenance company");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
               	   case 18: requestsGivenCID(esql); break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB 
      // Your code goes here.
      // ...
      // ...
   }//end addCustomer

   public static void addRoom(DBProject esql){
	  // Given room details add the room in the DB
      // Your code goes here.
      // ...
      // ...
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB

      // ...
      // ...
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	  // Given repair details add repair in the DB
      // Your code goes here.
      // ...
      // ...
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      // ...
      // ...
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      // ...
      // ...
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
      // ...
      // ...
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      // ...
      // ...
   try {
   String query = "SELECT * FROM Room WHERE hotelID = ";
         System.out.print("\tEnter hotelID: $");
         String input = in.readLine();
         query += input;

         int rowCount = esql.executeQuery(query);
         System.out.println ("Number of rooms for hotel " + input + " is "  + rowCount);
      } catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      try{
         System.out.print("\tEnter hotel ID: ");
         String hid = in.readLine();

         System.out.print("\tEnter date (YYYY-MM-DD): ");
         String date = in.readLine();

         String query =
            "SELECT DISTINCT roomNo " +
            "FROM Booking " +
            "WHERE hotelID = " + hid + " AND bookingDate = '" + date + "'";

         int rowCount = esql.executeQuery(query);
         System.out.println("Number of booked rooms on " + date +
                            " for hotel " + hid + " is: " + rowCount);
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
      // ...
      // ...
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms booked for a week(including the input date) 
      // Your code goes here.
      try {
         //read user input
         System.out.print("\tEnter hotel ID: ");
         String hid = in.readLine();
         System.out.print("\tEnter start date (YYYY-MM-DD): ");
         String date = in.readLine();
         //add input to query skeleton
         String query =
            "SELECT hotelID, roomNo, bookingDate, customer, price " +
            "FROM Booking " +
            "WHERE hotelID = " + hid + " " +
            "AND bookingDate BETWEEN '" + date + "'::date AND ('" + date + "'::date + 6) " +
            "ORDER BY bookingDate, roomNo";
         //execute query, printing the results
         int rowCount = esql.executeQuery(query);
         System.out.println("Total bookings for the week: " + rowCount);
      } catch(Exception e){
         System.err.println(e.getMessage());
      }
      // ...
      // ...
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      try{
         //read user input
         System.out.print("\tEnter start date (YYYY-MM-DD): ");
         String startDate = in.readLine();
         System.out.print("\tEnter end date (YYYY-MM-DD): ");
         String endDate = in.readLine();
         System.out.print("\tEnter K: ");
         String k = in.readLine();
         //add input to query skeleton
         String query =
            "SELECT DISTINCT roomNo, hotelID, price " +
            "FROM Booking " +
            "WHERE bookingDate BETWEEN '" + startDate + "'::date AND '" + endDate + "'::date " +
            "ORDER BY price DESC " +
            "LIMIT " + k;
         //execute query, printing the results
         int rowCount = esql.executeQuery(query);
         System.out.println("Total rooms found: " + rowCount);
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
      // ...
      // ...
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      try {
      // Read user input
      System.out.print("\tEnter customer first name: ");
      String fName = in.readLine();

      System.out.print("\tEnter customer last name: ");
      String lName = in.readLine();

      System.out.print("\tEnter K (number of bookings): ");
      String k = in.readLine();

      // Build query using a JOIN between Booking and Customer
      String query =
         "SELECT B.hotelID, B.roomNo, B.bookingDate, B.price " +
         "FROM Booking B " +
         "JOIN Customer C ON B.customer = C.customerID " +
         "WHERE C.fName = '" + fName + "' " +
         "  AND C.lName = '" + lName + "' " +
         "ORDER BY B.price DESC " +
         "LIMIT " + k + ";";

      // Execute query and print results
      int rowCount = esql.executeQuery(query);
      System.out.println("Total bookings found: " + rowCount);

   } catch (Exception e) {
      System.err.println(e.getMessage());
   }
      // ...
      // ...
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      try {
      System.out.print("\tEnter hotel ID: ");
      String hid = in.readLine();

      System.out.print("\tEnter customer first name: ");
      String fName = in.readLine();

      System.out.print("\tEnter customer last name: ");
      String lName = in.readLine();

      System.out.print("\tEnter start date (YYYY-MM-DD): ");
      String startDate = in.readLine();

      System.out.print("\tEnter end date (YYYY-MM-DD): ");
      String endDate = in.readLine();

      String query =
         "SELECT SUM(B.price) AS total_cost " +
         "FROM Booking B " +
         "JOIN Customer C ON B.customer = C.customerID " +
         "WHERE B.hotelID = " + hid + " " +
         "AND C.fName = '" + fName + "' " +
         "AND C.lName = '" + lName + "' " +
         "AND B.bookingDate BETWEEN '" + startDate + "'::date " +
         "AND '" + endDate + "'::date;";

      System.out.println("\nTotal cost incurred by " + fName + " " + lName +
                         " at hotel " + hid + ":");
      esql.executeQuery(query);  

   } catch (Exception e) {
      System.err.println(e.getMessage());
   }

      // ...
      // ...
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      try {
         //read user input
         System.out.print("\tEnter maintenance company name: ");
         String company = in.readLine();
         //add input to query skeleton
         String query =
            "SELECT R.rID, R.repairType, R.hotelID, R.roomNo " +
            "FROM Repair R, MaintenanceCompany M " +
            "WHERE M.name = '" + company + "' " +
            "AND R.mCompany = M.cmpID";
         int rowCount = esql.executeQuery(query);
         System.out.println("Total repairs found: " + rowCount);
      //execute query, printing the results
      } catch(Exception e){
         System.err.println(e.getMessage());
      }
      // ...
      // ...
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      try{
         //read user input
         System.out.print("\tEnter number of companies desired: ");
         String k = in.readLine();
         //add input to query skeleton
         String query =
            "SELECT M.name, COUNT(R.rID) AS repairCount " +
            "FROM MaintenanceCompany M LEFT JOIN Repair R " +
            "ON M.cmpID = R.mCompany " +
            "GROUP BY M.name " +
            "ORDER BY repairCount DESC " +
            "LIMIT " + k;
         //execute query, printing the results
         int rowCount = esql.executeQuery(query);
         System.out.println("Total companies found: " + rowCount);
      }catch(Exception e){
         System.err.println(e.getMessage());
      }

      // ...
      // ...
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      try{
         //read user input
         System.out.print("\tEnter hotel ID: ");
         String hid = in.readLine();
         System.out.print("\tEnter room number: ");
         String roomNo = in.readLine();
         //add input to query skeleton
         String query =
            "SELECT R.repairDate AS repairYear, COUNT(R.rID) AS repairCount " +
            "FROM Request RQ, Repair R " +
            "WHERE R.hotelID = " + hid + " " +
            "AND R.roomNo = " + roomNo + " " +
            "AND RQ.repairID = R.rID " +
            "GROUP BY repairYear " +
            "ORDER BY repairYear";
         //execute query, printing the results
         int rowCount = esql.executeQuery(query);
         System.out.println("Total years found: " + rowCount);
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
      
      // ...
      // ...
   }//end listRepairsMade

   public static void requestsGivenCID(DBProject esql) {
    try {
        System.out.print("\tEnter maintenance company ID: ");
        String cmpID = in.readLine();

        String query =
            "SELECT R.reqID, R.managerID, R.repairID, R.requestDate, R.description " +
            "FROM Request R INNER JOIN Repair RP ON R.repairID = RP.rID " +
            "WHERE RP.mCompany = " + cmpID;

        esql.executeQuery(query);
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}//end listRequestsForCompany


}//end DBProject


