/**
 * Program: DisplayModel.java
 * Purpose:provides a static method that will	convert a ResultSet set object 
 *         passed in from a query into a TableModel object, 
 *         which can then be used to create a JTable to display the data.
 *         Also has functionality to execute an sql statement and ultimately
 *         return the results as a JTable
 *         
 * Coder: Charles, from Technojeeves.com/joomla/index.pho/free/59-resultset-to-tablemodel
 * Revised by Bill Pulling...typed the Vectors to <String> and <Object> 
 * with additions by Justin Bonello
 * Date: Aug 02, 2016
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
 

public class DisplayModel
{	
	public static String lastQuery;
	
	public static TableModel executeSQL (String query)
	{			
		try
		{
			DBDriver.openConnection();
			DBDriver.myRslt = DBDriver.myStmt.executeQuery( query );
			
			lastQuery = query;
			return resultSetToTableModel( DBDriver.myRslt );				
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

		return null;
			
	}
	
	/*
	 * Method Name: resultSetToTableModel
	 * Purpose: a class method that converts a ResultSet object to a TableModel object
	 *          for use in the construction of a JTable object.
	 * Accepts: a ReesultSet object from a SQL query.
	 * 
	 * REturns: a TableModel object which can then be used
	 *          as an argument in the constructor method of JTable
	 *          to construct a JTable to display the data.
	 *          
	 *          NOTE: TableModel is an interface in Java (see the JDK DOcs)
	 *          so we actually return a DefaultTableModel object, which implements
	 *          all of the abstract methods specified in TableModel.
	 */
   public static TableModel resultSetToTableModel(ResultSet rs)
   {
       try
       {
      	 //get the metadata for number of columns and column names
         ResultSetMetaData metaData = rs.getMetaData();
         
         //get the number of columns using the getColumnCount of the ResultSetMetaData object
         int numberOfColumns = metaData.getColumnCount();
         
         //create a Vector of type String to hold the column names 
         Vector<String> columnNames = new Vector<String>();
 
         // Now, get the column names and store in the vector
         for (int column = 0; column < numberOfColumns; column++)
         {
        	 //Get the name of each column using the getColumnLabel() method of metaData object
        	 //NOTE: need to do the (column + 1) here because metaData columns indexing starts at 1
           //but JTable column indexing starts at 0 (SHEESH! Couldn't they stick to 
           // one indexing scheme here ?!?)
        	 
           columnNames.addElement(metaData.getColumnLabel(column + 1));
           
         }//end for
         
         //We are going to use a "vector of vectors" to store the rows of data in
         //the ResultSet object or CURSOR.
         
         //First we create a vector to store all of the rows
         // We'll type the Vector to type Object
         Vector<Object> rows = new Vector<Object>();
 
         while (rs.next())
         {
           // NOw Get each row of data and store each row in its own vector Vector.
           Vector<Object> newRow = new Vector<Object>();
 
           for (int i = 1; i <= numberOfColumns; i++)//column indexing STARTS AT 1!
           {
          	 //go through each row and add each column's data to the newRow vector
             newRow.addElement(rs.getObject(i));
           }//end for
          
          //now add this newRow vector to the rows vector
          rows.addElement(newRow);
         }//end while

         //Create and return a DefaultTableModel object to the line that called it. 
         // We pass in the two vectors as arguments to its constructor  
         return new DefaultTableModel(rows, columnNames);
           
       }//end try
       catch (Exception ex) 
       {
      	 System.out.println("Exception in DbUtils method resultSetToTableModel()...");
      	 System.out.println("Message is " + ex.getMessage() );
         ex.printStackTrace();
         return null;
       }//end catch
         
    }//end method
   
 }//end class
