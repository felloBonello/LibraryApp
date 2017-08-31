/**
 * Program Name: AuthorModel.java
 * Purpose: Has functionality to manipulate Author data.
 * 			Stores author data from the database for use by the
 * 			controller and to be displayed in a combobox.      
 * Coder: Justin Bonello
 * Date: Aug 2, 2016 
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


public class AuthorModel implements ComboBoxModel<String>{
	
	private AtomicInteger count;
	
	//CREATE INNER CLASS HERE for the Row objects
	private class Row
	{
		//data members
		public int authorId;
		public String last_name;
		public String first_name;
		
		public Row (String firstName, String lastName)
		{
			this.authorId = count.incrementAndGet();
			this.first_name = firstName;
			this.last_name = lastName;
		}
		
		public Row (String firstName, String lastName, int id)
		{
			this.authorId = id;
			this.first_name = firstName;
			this.last_name = lastName;
		}
	}//end class
	
	private ArrayList<Row> itemsArrayList = new ArrayList<Row>();
	//second array to hold data listeners
	private ArrayList<ListDataListener> dataListenerList = new ArrayList<ListDataListener> ();
	public String selection = null;
	
	
	public AuthorModel()
	{
		populateModel("SELECT first_name, last_name, authorID FROM author");
		count = new AtomicInteger( getIdAtIndex( this.getSize() -1 ) );
	}
	
	
	@Override
	public int getSize() {
		return itemsArrayList.size();
	}

	@Override
	public String getElementAt(int index) {
		// check that size is not beyond end of arrayList
		if(index < itemsArrayList.size() )
		{
			Row row = itemsArrayList.get(index);
			
			//now we need to APPEND each data element of the Row object
			// to a StringBuilder object
			StringBuilder itemsArrayListString = new StringBuilder();
			
			if( row.first_name.length() > 0 ) 
				itemsArrayListString.append(row.first_name);
			
			if( row.first_name.length() > 0 && row.last_name.length() > 0 )
				itemsArrayListString.append(" ");
			
			if( row.last_name.length() > 0 ) 
				itemsArrayListString.append(row.last_name);
			
			//now convert the StringBuilder object to a String and return
			return itemsArrayListString.toString();
			
		}
		
		return null;//leave this in if the outer if fails
	}
	
	public int getIdAtIndex ( int index )
	{
		if(index < itemsArrayList.size() )
		{
			Row row = itemsArrayList.get(index);
			return row.authorId;	
		}

		return -1;
	}

	@Override
	public void addListDataListener(ListDataListener listener)
	{
		// register it
		dataListenerList.add(listener);		
	}//end method

	@Override
	public void removeListDataListener(ListDataListener listener)
	{
		// if there is a listener,remove it
		if(dataListenerList.contains(listener) )
		{
			dataListenerList.remove(listener);
		}
	}//end method
	
	public synchronized void addElement(String firstName, String lastName)
	{
		//CREATE a new Row object
		Row row = new Row(firstName, lastName);
		
		//add the Row object to the ArrayList
		itemsArrayList.add(row);
		
		DBDriver.executeUpdate("INSERT INTO author (last_name, first_name) "
							+  "VALUES ('" + lastName + "', '" + firstName + "');");
		
		//call the processEvent() method and pass it a new ListDataEvent  
		//object indicating that the contents of the list have been changed.
		processEvent (new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED,0,0) );
		
	}//end method
	
	private void processEvent(ListDataEvent ev)
	{
		synchronized(this)//prevents data corruption if multiple threads try to access the arrayList
		{
			//cycle through the list of listeners so that they all get the message
			// and let them know that a list changed event has occured.
			for(int i = 0; i < dataListenerList.size(); i++)
			{
				dataListenerList.get(i).contentsChanged(ev);
			}//end for
		}//end synchronized block
	}//end method

	
	private void populateModel (String query)
	{
		try
		{
			DBDriver.openConnection();
			DBDriver.myRslt = DBDriver.myStmt.executeQuery( query );
			
			while (DBDriver.myRslt.next())
			{
				String firstName = DBDriver.myRslt.getObject(1).toString();
				String lastName = DBDriver.myRslt.getObject(2).toString();
				int id = Integer.parseInt(DBDriver.myRslt.getObject(3).toString());
				Row row = new Row(firstName, lastName, id);
				itemsArrayList.add(row);
			}		
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


	@Override
	public void setSelectedItem(Object anItem) {
		selection = (String) anItem;
	}
	
	
	@Override
	public Object getSelectedItem() {
		return selection;
	}
	
}
