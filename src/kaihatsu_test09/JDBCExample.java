package kaihatsu_test09;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
 // JDBC driver name and database URL
 // static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
 static final String DB_URL = "jdbc:postgresql://localhost:5432/sampledb";

 //  Database credentials
 static final String USER = "postgres";
 static final String PASS = "password";

 public static void main(String[] args) {
 Connection conn = null;
 Statement stmt = null;

 try{
    //STEP 2: Register JDBC driver
     Class.forName("org.postgresql.Driver");

    //STEP 3: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL, USER, PASS);

    //STEP 4: Execute a query
    System.out.println("Creating tabele...");
    stmt = conn.createStatement();

    String sql = "CREATE TABLE CardInfo (\r\n"
            + "  CardID nchar(6),\r\n"
            + "  CustomerID nchar(5),\r\n"
            + "  EmployeeID int\r\n"
            + ")";
    stmt.executeUpdate(sql);
    System.out.println("Database created successfully...");
 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }finally{
    //finally block used to close resources
    try{
       if(stmt!=null)
          stmt.close();
    }catch(SQLException se2){
    }// nothing we can do
try{
   if(conn!=null)
      conn.close();
}catch(SQLException se){
   se.printStackTrace();
    }//end finally try
 }//end try
System.out.println("Goodbye!");
}//end main
}//end JDBCExample
