/**
 * Program Name: DBDriver.java
 * Purpose: Connects to the DB with a connection string.
 * 			COntains functionality toconnect to the DB,
 * 			run an update statement given the SQL string and
 * 			close the connection afterwards.
 * Coder: Justin Bonello
 * Date: Aug 2, 2016 
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBDriver {
	
	// standard JDBC boilerplate
	private static final String URL = "jdbc:mysql://localhost:3306/info5051_books";//Note changed database here to 'world'
	private static final String USER = "root";
	private static final String PASSWORD = "password";
					
	// standard boilerplate in a JDBC app
	public static Connection myConn = null;
	public static Statement myStmt = null;
	public static ResultSet myRslt = null;
	
	
	public static void openConnection () throws SQLException
   	{			
		// standard boilerplate in a JDBC app
		myConn = null;
		myStmt = null;
		myRslt = null;
		
		//make the connection
		myConn = DriverManager.getConnection(URL, USER, PASSWORD);
		myStmt = myConn.createStatement();
   	}
	
	public static void executeUpdate(String query)
	{		
		try
		{	
			DBDriver.openConnection();
			DBDriver.myStmt.executeUpdate( query );		
		}//end try
		catch(SQLException ex)
		{
			System.out.println("SQLException caught, message is " + ex.getMessage());
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			System.out.println("Exception caught, message is " + ex.getMessage());
			ex.printStackTrace();
		}		
		finally 
		{
			DBDriver.closeConnection();
		}//end finally			
	}
	
	
	public static void closeConnection ()
	{
		try
		{
			if(myRslt != null)
			{
				myRslt.close();
			}
			
			if(myStmt != null)
			{
				myStmt.close();
			}
				
			if(myConn != null)
			{
				myConn.close();
			}
		}//end try
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
