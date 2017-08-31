
/**
 * Program Name: BookModel.java
 * Purpose: Store data about the books for use by the controller.
 * 			Has functionality to add and manipualte books in the DB.
 * 			Also of returning the availibity of a given book.     
 * Coder: Justin Bonello
 * Date: Aug 2, 2016 
 */


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


public class BookModel implements ListModel<String>{
	
	private AtomicInteger count;
	
	//CREATE INNER CLASS HERE for the Row objects
	private class Row
	{
		//data members
		public int bookId;
		public String title;
		public String isbn;
		public String edition_number;
		public String subject;
		public short available;
		
		public Row (String title, String isbn, String edition_number, String subject, short available)
		{
			this.bookId = count.incrementAndGet();
			this.title = title;
			this.isbn = isbn;
			this.edition_number = edition_number;
			this.subject = subject;
			this.available = available;
		}
		
		public Row (int id, String title, String isbn, String edition_number, String subject, short available)
		{
			this.bookId = id;
			this.title = title;
			this.isbn = isbn;
			this.edition_number = edition_number;
			this.subject = subject;
			this.available = available;
		}
	}//end class
	
	private ArrayList<Row> itemsArrayList = new ArrayList<Row>();
	//second array to hold data listeners
	private ArrayList<ListDataListener> dataListenerList = new ArrayList<ListDataListener> ();
	public String selection = null;
	
	
	public BookModel()
	{
		populateModel("SELECT bookId, title, isbn, edition_number, subject, available FROM book");
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
			
			//now convert the StringBuilder object to a String and return
			return row.title;
			
		}
		
		return null;//leave this in if the outer if fails
	}
	
	public int getIdAtIndex ( int index )
	{
		if(index < itemsArrayList.size() )
		{
			Row row = itemsArrayList.get(index);
			return row.bookId;	
		}

		return -1;
	}
	
	
	public int getAvailableByISBN ( String isbn )
	{
		Iterator<Row> it = itemsArrayList.iterator();
		
		while( it.hasNext() )
		{
			Row row = it.next();
			if( row.isbn.equals(isbn))
				return row.available;	
		}
		return -1;
	}
	
	public void updateAvailablilityById ( int id, short availability )
	{
		Iterator<Row> it = itemsArrayList.iterator();
		
		while( it.hasNext() )
		{
			Row row = it.next();
			if( row.bookId == id )
				row.available = availability;	
		}
		String updateAvailability = "UPDATE book SET available =" + availability + " WHERE bookId = " + id + ";";
		DBDriver.executeUpdate(updateAvailability);	
	}
	
	
	public int getIdByISBN ( String isbn )
	{
		Iterator<Row> it = itemsArrayList.iterator();
		
		while( it.hasNext() )
		{
			Row row = it.next();
			if( row.isbn.equals(isbn) )
				return row.bookId;	
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
	
	public synchronized void addElement(String title, String isbn, String edition_number, String subject, short available)
	{
		//CREATE a new Row object
		Row row = new Row(title, isbn, edition_number, subject, available);
		
		//add the Row object to the ArrayList
		itemsArrayList.add(row);
		
		DBDriver.executeUpdate("INSERT INTO book (title, isbn, edition_number, subject, available) "
							+  "VALUES ('" + subject + "', '" + isbn + "', '" + edition_number + "', '" + subject + "', '" + available + "');");
		
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
				int bookId = Integer.parseInt(DBDriver.myRslt.getObject(1).toString());
				String title = DBDriver.myRslt.getObject(2).toString();
				String isbn = DBDriver.myRslt.getObject(3).toString();
				String edition_number = DBDriver.myRslt.getObject(4).toString();
				String subject = DBDriver.myRslt.getObject(5).toString();
				short available = Short.parseShort(DBDriver.myRslt.getObject(6).toString());
				Row row = new Row(bookId, title, isbn, edition_number, subject, available);
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

	
}
