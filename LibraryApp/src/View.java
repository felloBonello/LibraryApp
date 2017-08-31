/**
 * Program Name: View.java
 * Purpose: Displays the interface for using the application as well as
 * 			the output display on the right. The controller class is contained
 * 			in this file that handles all of the action listeners to handle 
 * 			the user input.         
 * Coder: Justin Bonello
 * Date: Aug 2, 2016 
 */

import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class View extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static View view;
	
	//display components
	private		JTable 		table;
	private		JScrollPane scrollPane;
	
	//main containers
	private 	JPanel 		pnl_main;
	private		JPanel 		pnl_display;
	
	//listener related GUI components
	private		JButton		btn_search;
	private		JButton		btn_submit;
	
	//MainTabbed pane components
	private		JTabbedPane mainTabbedPane;
	private		JPanel		pnl_welcome;
	private		JPanel		pnl_retrieval;
	private		JPanel		pnl_input;
	
	//Retrieval Tabbed pane components
	private		JTabbedPane retrievalTabbedPane;
	private		JPanel		pnl_search_books;
	private		JPanel		pnl_search_borrowers;
	private		JPanel		pnl_search_loans;
	
	//Input Tabbed pane components
	private		JTabbedPane inputTabbedPane;
	private		JPanel		pnl_new_author = new JPanel();
	private		JPanel		pnl_new_book = new JPanel();
	private		JPanel		pnl_add_author = new JPanel();
	private		JPanel		pnl_new_borrower = new JPanel();
	private		JPanel		pnl_loan_book = new JPanel();
	private		JPanel		pnl_return_book = new JPanel();
	
	//Welcome Panel components
	private		JLabel		lbl_welcome;
	
	//search borrowers tab components
	private 	JRadioButton rbtn_all_borrowers;
	private		JRadioButton rbtn_all_active;
	
	//search loans tab components
	private JRadioButton rbtn_all_loans;
	
	//search books tab components
	private JComboBox<String> cbo_status;
	private JTextField txt_subject;
	
	//new author tab components
	private JTextField txt_fname;
	private JTextField txt_lname;
	
	//new borrower tab components
	private JTextField txt_b_fname;
	private JTextField txt_b_lname;
	private JTextField txt_email;
	
	//new book tab components
	private JComboBox<String> cbo_author;
	private JTextField txt_title;
	private JTextField txt_ISBN;
	private JTextField txt_edition;
	private JTextField txt_a_subject;
	
	//new loan tab components
	private JComboBox<String> cbo_borrower;
	private JTextField txt_date_loaned;
	private JTextArea txt_comment;
	private JTextField txt_l_ISBN;
	
	//return book tab components
	private JTextField txt_r_ISBN;
	private JTextField txt_r_date;
	
	//add author tab components
	private JTextField txt_a_ISBN;
	
	//models
	private 	TableModel	bookDisplayModel;
	private 	TableModel	borrowerDisplayModel;
	private 	TableModel	loanDisplayModel;
	private 	AuthorModel	authorModel = new AuthorModel();
	private 	BorrowerModel	borrowerModel = new BorrowerModel();
	private 	BookModel		bookModel = new BookModel();
	
	//Notification frame
	JFrame notif_frame = new JFrame();
	
	
	private final int FIELD_WIDTH = 15;
	
	
	
	public View() throws HeadlessException
	{	
		super( "Personal Library Loan App" );
		
		this.setSize( 1100, 360 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout( new BorderLayout() );
		this.setBackground( Color.gray );
		//this.setResizable(false);

		// Create the tab pages
		createWelcomeTab();
		createDataRetrievalTab();
		createDataInputTab();
		createDataDisplay();
		
		pnl_main = new JPanel();
		pnl_main.setLayout( new BorderLayout() );
		this.add( pnl_main, BorderLayout.CENTER );
		this.add( pnl_display, BorderLayout.EAST );

		// Create a tabbed pane
		mainTabbedPane = new JTabbedPane();
		mainTabbedPane.addTab( "Welcome", pnl_welcome );
		mainTabbedPane.addTab( "Data Retrieval", pnl_retrieval );
		mainTabbedPane.addTab( "Data Input", pnl_input );
		pnl_main.add( mainTabbedPane, BorderLayout.CENTER );
		
		//Action Listeners
		Controller controller = new Controller();
		btn_search.addActionListener(controller);
		btn_submit.addActionListener(controller);
		
		this.setVisible(true);
	}
	
	
	public void createDataDisplay()
	{
		JLabel lbl_title = new JLabel( "Query Results:" );
		lbl_title.setFont( new Font("Arial", Font.PLAIN, 17) );
		table = new JTable();
		scrollPane = new JScrollPane( table );
		pnl_display = new JPanel( new BorderLayout() );
		pnl_display.add(scrollPane, BorderLayout.CENTER );
		pnl_display.add(lbl_title, BorderLayout.NORTH );
	}
	
	public void createWelcomeTab()
	{	
		pnl_welcome = new JPanel();
		pnl_welcome.setLayout( new BorderLayout() );
		
		lbl_welcome = new JLabel("Welcome");
		lbl_welcome.setFont( new Font("Broadway", Font.PLAIN, 50));
		
		pnl_welcome.add( lbl_welcome, BorderLayout.CENTER );
	}
	
	
	public void createDataRetrievalTab()
	{	
		pnl_retrieval = new JPanel();
		pnl_retrieval.setLayout( new BorderLayout() );
		btn_search = new JButton( "Search" );

		// Create the tab pages
		createSearchBooksTab();		
		createSearchBorrowersTab();
		createSearchLoans();
		
		// Create a tabbed pane
		retrievalTabbedPane = new JTabbedPane();
		retrievalTabbedPane.addTab( "Search Books", pnl_search_books );
		retrievalTabbedPane.addTab( "Search Borrowers", pnl_search_borrowers );
		retrievalTabbedPane.addTab( "Search Loans", pnl_search_loans );
		
		pnl_retrieval.add( retrievalTabbedPane, BorderLayout.CENTER );
		pnl_retrieval.add( btn_search, BorderLayout.SOUTH );
	}
	
	
	public void createDataInputTab()
	{	
		pnl_input = new JPanel();
		pnl_input.setLayout( new BorderLayout() );
		btn_submit = new JButton( "Submit" );

		// Create the tab pages
		createNewAuthorTab();		
		createNewBookTab();
		createAddAuthorTab();
		createNewBorrowerTab();
		createBookLoanTab();
		createBookReturnTab();
		
		// Create a tabbed pane
		inputTabbedPane = new JTabbedPane();
		inputTabbedPane.addTab( "New Author", pnl_new_author );
		inputTabbedPane.addTab( "New Book", pnl_new_book );
		inputTabbedPane.addTab( "Add Author to Book", pnl_add_author );
		inputTabbedPane.addTab( "New Borrower", pnl_new_borrower );
		inputTabbedPane.addTab( "Loan Book", pnl_loan_book );
		inputTabbedPane.addTab( "Return Book", pnl_return_book );
		
		pnl_input.add( inputTabbedPane, BorderLayout.CENTER );
		pnl_input.add( btn_submit, BorderLayout.SOUTH );
	}
	
	
	public void createSearchBooksTab()
	{	
		pnl_search_books = new JPanel();
		pnl_search_books.setLayout( new BorderLayout() );
		pnl_search_books.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Query Options"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 2, 2, 20, 20) );
		
		//create components
		JLabel lbl_status = new JLabel("Book Status:");
		JLabel lbl_subject = new JLabel("Subject:");
		String[] cbo_status_options = {"none", "checked out", "available"};
		cbo_status = new JComboBox<String>( cbo_status_options );
		txt_subject = new JTextField(FIELD_WIDTH);
		
		pnl_fieldset.add(lbl_status);
		pnl_fieldset.add(cbo_status);
		pnl_fieldset.add(lbl_subject);
		pnl_fieldset.add(txt_subject);
		
		pnl_border.add(pnl_fieldset);
		pnl_search_books.add(pnl_border, BorderLayout.CENTER);	
	}
	
	
	public void createSearchBorrowersTab()
	{	
		pnl_search_borrowers = new JPanel();
		pnl_search_borrowers.setLayout( new BorderLayout() );
		pnl_search_borrowers.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Query Options"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 2, 1, 20, 20 ) );
		
		//create components
		rbtn_all_borrowers = new JRadioButton("All registered borrowers", true);
		rbtn_all_active = new JRadioButton("All active borrowers");
		ButtonGroup rbtn_group = new ButtonGroup();
		rbtn_group.add(rbtn_all_borrowers);
		rbtn_group.add(rbtn_all_active);
		
		pnl_fieldset.add(rbtn_all_borrowers);
		pnl_fieldset.add(rbtn_all_active);
		
		pnl_border.add(pnl_fieldset);
		pnl_search_borrowers.add(pnl_border, BorderLayout.CENTER);	
	}
	
	
	public void createSearchLoans()
	{	
		pnl_search_loans = new JPanel();
		pnl_search_loans.setLayout( new BorderLayout() );
		pnl_search_loans.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Query Options"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 1, 1, 20, 20 ) );
		
		rbtn_all_loans = new JRadioButton("All loans", true);
		ButtonGroup rbtn_group = new ButtonGroup();
		rbtn_group.add(rbtn_all_loans);
		
		pnl_fieldset.add(rbtn_all_loans);	
		pnl_border.add(pnl_fieldset);
		pnl_search_loans.add(pnl_border, BorderLayout.CENTER);
	}
	
	
	public void createNewAuthorTab()
	{
		pnl_new_author.removeAll();
		
		pnl_new_author.setLayout( new BorderLayout() );
		pnl_new_author.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Details"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 2, 2, 20, 20 ) );
		
		JLabel lbl_fname = new JLabel("First Name:");
		JLabel lbl_lname = new JLabel("Last Name:");
		txt_fname = new JTextField(FIELD_WIDTH);
		txt_lname = new JTextField(FIELD_WIDTH);
		
		pnl_fieldset.add(lbl_fname);
		pnl_fieldset.add(txt_fname);	
		pnl_fieldset.add(lbl_lname);	
		pnl_fieldset.add(txt_lname);	
		
		pnl_border.add(pnl_fieldset);
		pnl_new_author.add(pnl_border, BorderLayout.CENTER);
	}
	
	
	public void createNewBookTab()
	{
		pnl_new_book.removeAll();
		
		pnl_new_book.setLayout( new BorderLayout() );
		pnl_new_book.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Details"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 5, 2, 10, 10 ) );
		
		JLabel lbl_author = new JLabel("Author:");
		JLabel lbl_title = new JLabel("Title:");
		JLabel lbl_ISBN = new JLabel("ISBN:");
		JLabel lbl_edition = new JLabel("Edition:");
		JLabel lbl_subject = new JLabel("Subject:");
		String[] options = {};
		cbo_author = new JComboBox<String>(options);
		cbo_author.setModel( authorModel );
		cbo_author.setSelectedIndex(-1);
		txt_title = new JTextField(FIELD_WIDTH);
		txt_ISBN = new JTextField(FIELD_WIDTH);
		txt_edition = new JTextField(FIELD_WIDTH);
		txt_a_subject = new JTextField(FIELD_WIDTH);
		
		pnl_fieldset.add(lbl_author);
		pnl_fieldset.add(cbo_author);	
		pnl_fieldset.add(lbl_title);	
		pnl_fieldset.add(txt_title);
		pnl_fieldset.add(lbl_ISBN);
		pnl_fieldset.add(txt_ISBN);	
		pnl_fieldset.add(lbl_edition);	
		pnl_fieldset.add(txt_edition);
		pnl_fieldset.add(lbl_subject);	
		pnl_fieldset.add(txt_a_subject);	
		
		
		pnl_border.add(pnl_fieldset);
		pnl_new_book.add(pnl_border, BorderLayout.CENTER);	
	}
	
	
	public void createAddAuthorTab()
	{
		pnl_add_author.removeAll();
		
		pnl_add_author.setLayout( new BorderLayout() );
		pnl_add_author.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Details"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 2, 2, 10, 10 ) );
		
		JLabel lbl_author = new JLabel("Author:");
		JLabel lbl_ISBN = new JLabel("ISBN:");
		String[] options = {};
		cbo_author = new JComboBox<String>(options);
		cbo_author.setModel( authorModel );
		cbo_author.setSelectedIndex(-1);
		txt_a_ISBN = new JTextField(FIELD_WIDTH);
		
		pnl_fieldset.add(lbl_author);
		pnl_fieldset.add(cbo_author);	
		pnl_fieldset.add(lbl_ISBN);
		pnl_fieldset.add(txt_a_ISBN);	
			
		pnl_border.add(pnl_fieldset);
		pnl_add_author.add(pnl_border, BorderLayout.CENTER);	
	}
	
	
	public void createNewBorrowerTab()
	{
		pnl_new_borrower.removeAll();
		
		pnl_new_borrower.setLayout( new BorderLayout() );
		pnl_new_borrower.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Details"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 3, 2, 20, 20 ) );
		
		JLabel lbl_fname = new JLabel("First Name:");
		JLabel lbl_lname = new JLabel("Last Name:");
		JLabel lbl_email = new JLabel("Email:");
		txt_b_fname = new JTextField(FIELD_WIDTH);
		txt_b_lname = new JTextField(FIELD_WIDTH);
		txt_email = new JTextField(FIELD_WIDTH);
		
		pnl_fieldset.add(lbl_fname);
		pnl_fieldset.add(txt_b_fname);	
		pnl_fieldset.add(lbl_lname);	
		pnl_fieldset.add(txt_b_lname);
		pnl_fieldset.add(lbl_email);
		pnl_fieldset.add(txt_email);			
		
		pnl_border.add(pnl_fieldset);
		pnl_new_borrower.add(pnl_border, BorderLayout.CENTER);			
	}
	
	
	public void createBookLoanTab()
	{
		pnl_loan_book.removeAll();
		
		pnl_loan_book.setLayout( new BorderLayout() );
		pnl_loan_book.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Details"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 4, 2, 10, 10 ) );
		
		JLabel lbl_ISBN = new JLabel("ISBN:");
		JLabel lbl_borrower = new JLabel("Borrower:");
		JLabel lbl_date = new JLabel("Date:");
		JLabel lbl_comment = new JLabel("Comment:");
		txt_l_ISBN= new JTextField(FIELD_WIDTH);
		String[] options = {};
		cbo_borrower = new JComboBox<String>(options);
		cbo_borrower.setModel( borrowerModel );
		cbo_borrower.setSelectedIndex(-1);
		txt_date_loaned = new JTextField(FIELD_WIDTH);
		txt_comment = new JTextArea();
		JScrollPane scr_comment = new JScrollPane(txt_comment);
		txt_comment.setLineWrap(true); 
		
		pnl_fieldset.add(lbl_ISBN);
		pnl_fieldset.add(txt_l_ISBN);	
		pnl_fieldset.add(lbl_borrower);	
		pnl_fieldset.add(cbo_borrower);
		pnl_fieldset.add(lbl_date);
		pnl_fieldset.add(txt_date_loaned);
		pnl_fieldset.add(lbl_comment);
		pnl_fieldset.add(scr_comment);	
		
		pnl_border.add(pnl_fieldset);
		pnl_loan_book.add(pnl_border, BorderLayout.CENTER);			
	}
	
	
	public void createBookReturnTab()
	{
		pnl_return_book.removeAll();
		
		pnl_return_book.setLayout( new BorderLayout() );
		pnl_return_book.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) );
		
		JPanel pnl_border = new JPanel();
		pnl_border.setLayout( new FlowLayout(FlowLayout.CENTER) );
		pnl_border.setBorder(BorderFactory.createTitledBorder("Details"));
		
		JPanel pnl_fieldset = new JPanel();
		pnl_fieldset.setLayout( new GridLayout( 2, 2, 20, 20 ) );
		
		JLabel lbl_ISBN = new JLabel("ISBN:");
		JLabel lbl_date = new JLabel("Date:");
		txt_r_ISBN = new JTextField(FIELD_WIDTH);
		txt_r_date = new JTextField(FIELD_WIDTH);

		pnl_fieldset.add(lbl_ISBN);
		pnl_fieldset.add(txt_r_ISBN);	
		pnl_fieldset.add(lbl_date);
		pnl_fieldset.add(txt_r_date);	
		
		pnl_border.add(pnl_fieldset);
		pnl_return_book.add(pnl_border, BorderLayout.CENTER);		
	}	
	
	public class Controller implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if( e.getSource() == btn_search )
			{
				//search books controller
				if( retrievalTabbedPane.getSelectedComponent() == pnl_search_books )
				{
					searchBooks();
				}
				//search borrowers controller
				else if( retrievalTabbedPane.getSelectedComponent() == pnl_search_borrowers )
				{
					searchBorrowers();
				}
				//search loans controller
				else if( retrievalTabbedPane.getSelectedComponent() == pnl_search_loans )
				{
					searchLoans();
				}
			}
			if( e.getSource() == btn_submit )
			{
				//new author controller
				if( inputTabbedPane.getSelectedComponent() == pnl_new_author )
				{
					addNewAuthor();
				}
				//new book controller
				else if( inputTabbedPane.getSelectedComponent() == pnl_new_book )
				{
					addNewBook();
				}
				//new book controller
				else if( inputTabbedPane.getSelectedComponent() == pnl_add_author )
				{
					addAuthor();
				}
				//new borrower controller
				else if( inputTabbedPane.getSelectedComponent() == pnl_new_borrower )
				{
					addNewBorrower();
				}
				//new loan controller
				else if( inputTabbedPane.getSelectedComponent() == pnl_loan_book )
				{
					loanBook();
				}
				//return book controller
				else if( inputTabbedPane.getSelectedComponent() == pnl_return_book )
				{
					returnBook();
				}
			}
		}
		
		
		private void searchBooks()
		{
			String available = null;
			String subject = txt_subject.getText().trim();
			String where = "";
			
			if( cbo_status.getSelectedItem() == "checked out" )
				available = "0";
			else if( cbo_status.getSelectedItem() == "available" )
				available = "1";
			
			if( subject.length() != 0 && available != null)
				where = "where available = " + available + " and subject = '" + subject + "'";
			else if( subject.length() == 0 && available != null)
				where = "where available = '" + available + "'";
			else if( subject.length() != 0 && available == null)
				where = "where subject = '" + subject + "'";
			
			String query = "SELECT title, isbn, edition_number, subject, available "
						+  "FROM book " + where + ";";
			bookDisplayModel = DisplayModel.executeSQL( query );
			table.setModel( bookDisplayModel );
		}
		
		
		private void searchBorrowers()
		{
			if( rbtn_all_borrowers.isSelected() )
			{
				String query = "SELECT first_name, last_name, borrower_email FROM borrower;";
				borrowerDisplayModel = DisplayModel.executeSQL( query );
				table.setModel( borrowerDisplayModel );
			}
			else if( rbtn_all_active.isSelected() )
			{
				String query ="SELECT b.first_name, b.last_name, b.borrower_email FROM borrower b "
							+ "RIGHT JOIN book_loan bl ON b.Borrower_ID = bl.borrower_borrower_id "
							+ "GROUP BY b.first_name;";
				borrowerDisplayModel = DisplayModel.executeSQL( query );
				table.setModel( borrowerDisplayModel );
			}
		}
		
		
		private void searchLoans()
		{
			if( rbtn_all_loans.isSelected() )
			{
				String query =  "Select bk.Title, CONCAT(br.first_name, ' ', br.last_name) as borrower, bl.comment, bl.date_out, bl.date_returned "
							+	"FROM book_loan bl "
							+ 	"INNER JOIN borrower br ON bl.borrower_borrower_id = br.borrower_id "
							+ 	"INNER JOIN book bk ON bl.book_bookid = bk.bookid;";
				loanDisplayModel = DisplayModel.executeSQL( query );
				table.setModel( loanDisplayModel );
			}
		}
		
		
		private void addNewAuthor()
		{
			String firstName = txt_fname.getText().trim();
			String lastName = txt_lname.getText().trim();
			
			HashMap<String, JTextField> validationMap = new HashMap<String, JTextField>();
			validationMap.put("first name", txt_fname);
			validationMap.put("last name", txt_lname);
			
			if( validateBlankTextFields( validationMap ) )
			{
				authorModel.addElement(firstName, lastName);
				createNewAuthorTab();
				inputTabbedPane.repaint();
				success("The author has been successfully added to the database.");
			}
				
		}
		
		
		private void addNewBook()
		{
			String title = txt_title.getText().trim();
			String isbn = txt_ISBN.getText().trim();
			String edition_string = txt_edition.getText().trim();
			String edition = validateEdition( edition_string );
			String subject = txt_a_subject.getText().trim();
			int index = cbo_author.getSelectedIndex();
			int authorId;
			
			HashMap<String, JTextField> validationMap = new HashMap<String, JTextField>();
			validationMap.put("title", txt_title);
			validationMap.put("ISBN", txt_ISBN);
			validationMap.put("subject", txt_a_subject);

			if( validateBlankTextFields( validationMap ) )
			{
				if( index == -1 )
				{
					JOptionPane.showMessageDialog(notif_frame,
						    "You must select an author from the drop down list.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					cbo_author.requestFocus();
				}
				else if ( !validateISBN( isbn ) ){}
				else if ( edition != "-1")
				{
					authorId = authorModel.getIdAtIndex( index );
					bookModel.addElement(title, isbn, edition, subject, (short) 1);		
					int bookId = bookModel.getIdAtIndex( bookModel.getSize() - 1 );
					String query = "INSERT INTO book_author (book_bookId, author_authorId) "
								+  "VALUES ('" + bookId + "', '" + authorId + "');";
					DBDriver.executeUpdate( query );
					updateBookDisplayModel();
					createNewBookTab();
					inputTabbedPane.repaint();
					success("The book has been successfully added to the database.");
				}
				else
				{
					txt_edition.requestFocus();
				}
			}
		}
		
		
		private void addAuthor()
		{
			String isbn = txt_a_ISBN.getText().trim();
			int index = cbo_author.getSelectedIndex();
			
			int available = bookModel.getAvailableByISBN(isbn);
			
			if( index == -1 )
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "You must select an author from the drop down list.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				cbo_author.requestFocus();
			}
			else if( available == -1 )
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "The ISBN you entered was not found in the database.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				txt_l_ISBN.requestFocus();
			}
			else
			{
				int bookId = bookModel.getIdByISBN(isbn);
				int authorId = authorModel.getIdAtIndex( index );
				String query = "INSERT INTO book_author (book_bookId, author_authorId) "
							+  "VALUES ('" + bookId + "', '" + authorId + "');";
				DBDriver.executeUpdate( query );
				createAddAuthorTab();
				inputTabbedPane.repaint();
				success("You've successfully added a new author to the book.");
			}
		}

		
		private void addNewBorrower()
		{
			String firstName = txt_b_fname.getText().trim();
			String lastName = txt_b_lname.getText().trim();
			String email = txt_email.getText().trim();
			
			HashMap<String, JTextField> validationMap = new HashMap<String, JTextField>();
			validationMap.put("first name", txt_b_fname);
			validationMap.put("last name", txt_b_lname);
			validationMap.put("email", txt_email);

			if( validateBlankTextFields( validationMap ) )
			{
				borrowerModel.addElement(firstName, lastName, email);
				updateBorrowerDisplayModel();
				createNewBorrowerTab();
				inputTabbedPane.repaint();
				success("The borrower has been successfully added to the database.");
			}
		}

		
		private void loanBook()
		{
			String isbn = txt_l_ISBN.getText().trim();
			int index = cbo_borrower.getSelectedIndex();
			int borrowerId = -1;
			if( index != -1)
			{
				borrowerId = borrowerModel.getIdAtIndex(index);
			}
			else
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "You must select a borrower from the drop down list.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				cbo_borrower.requestFocus();
			}
			String date_string = txt_date_loaned.getText().trim();
			String comment = txt_comment.getText().trim();
			Date date = null;
			
			int available = bookModel.getAvailableByISBN( isbn );
			int bookId = bookModel.getIdByISBN( isbn );
			
			HashMap<String, JTextField> validationMap = new HashMap<String, JTextField>();
			validationMap.put("ISBN", txt_l_ISBN);
			validationMap.put("date", txt_date_loaned);

			if( validateDate(date_string, txt_date_loaned) )
				date = Date.valueOf(txt_date_loaned.getText().trim());
				
			if( validateBlankTextFields( validationMap ) )
			{
				if( available == 1 && date != null && borrowerId != -1)
				{
					String insertLoan = "INSERT INTO book_loan (book_bookId, borrower_borrower_Id, comment, date_out )"
							 + 			"VALUES (" + bookId + ", " + borrowerId + ", '" + comment + "', '" + date + "');";
					DBDriver.executeUpdate(insertLoan);
					bookModel.updateAvailablilityById( bookId, (short)0 );
					updateBookDisplayModel();
					updateLoanDisplayModel();
					createBookLoanTab();
					inputTabbedPane.repaint();
					success("The loan has been added to the database.");
				}
				else if( available == 0 )
				{
					JOptionPane.showMessageDialog(notif_frame,
						    "The book you entered is currently checked out.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					txt_l_ISBN.requestFocus();
				}
				else
				{
					JOptionPane.showMessageDialog(notif_frame,
						    "The ISBN you entered was not found in the database.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					txt_l_ISBN.requestFocus();
				}
			}
			
		}

		
		private void returnBook()
		{
			String isbn = txt_r_ISBN.getText().trim();
			String date_string = txt_r_date.getText().trim();
			Date date = null;
			
			int available = bookModel.getAvailableByISBN( isbn );
			int bookId = bookModel.getIdByISBN( isbn );
			
			
			if( validateDate(date_string, txt_r_date) )
				date = Date.valueOf(txt_r_date.getText().trim());
			
			if( available == 0 && date != null)
			{
				String updateLoan = "UPDATE book_loan SET date_returned = '" + date + "' WHERE book_bookId = " + bookId + " and date_returned IS NULL;";
				DBDriver.executeUpdate(updateLoan);
				bookModel.updateAvailablilityById( bookId, (short)1 );
				updateBookDisplayModel();
				updateLoanDisplayModel();
				createBookReturnTab();
				inputTabbedPane.repaint();
				success("The book has been successfully returned.");
			}
			else if( available == 1 )
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "The book you entered hasn't been checked out yet.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				txt_l_ISBN.requestFocus();
			}
			else
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "The ISBN you entered was not found in the database.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				txt_l_ISBN.requestFocus();
			}
		}
		
		
		private boolean validateBlankTextFields( Map<String, JTextField> m )
		{
			// key iterator
			Iterator<String> iterator = m.keySet().iterator();
			while( iterator.hasNext() )
			{
				String key =  iterator.next();
				JTextField field = m.get(key);
				
				if( field.getText().trim().length() == 0 )
				{
					JOptionPane.showMessageDialog(notif_frame,
						    "The " + key + " field cannot be blank.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					field.requestFocus();
					return false;
				}				
			}	
			return true;
		}
		
		
		private String validateEdition( String input )
		{
			if( input.length() > 0 )
			{
				try
				{
					Integer.parseInt(input);
					return input;
				}	
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(notif_frame,
						    "The edition field must be a number.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return "-1";
				}
			}
			JOptionPane.showMessageDialog(notif_frame,
				    "The edition field cannot be empty.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return "-1";			
		}
		
		
		private boolean validateISBN( String ISBN )
		{
			if( ISBN.length() != 13 )
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "The ISBN must be 13 digits long.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				txt_ISBN.requestFocus();
				return false;
			}
			else if( bookModel.getAvailableByISBN(ISBN) != -1 )
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "A book already exists with that ISBN.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				txt_ISBN.requestFocus();
				return false;
			}
			else
			{
				try
				{
					Long.parseLong(ISBN);
				}	
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(notif_frame,
						    "The ISBN field must be a number.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					txt_ISBN.requestFocus();
					return false;
				}
			}
			return true;
		}
		
		private boolean validateDate( String date, JTextField txt_date )
		{
			try
			{
				Date.valueOf(date);
				return true;
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(notif_frame,
					    "The date must be in the following format yyyy-mm-dd",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				txt_date.requestFocus();
				return false;
			}
		}
		
		
		private void success(String msg)
		{
			JOptionPane.showMessageDialog(notif_frame,msg);
		}
		
		private void updateBookDisplayModel( )
		{
			if( table.getModel() ==  bookDisplayModel && DisplayModel.lastQuery != null)
			{
				bookDisplayModel = DisplayModel.executeSQL(DisplayModel.lastQuery);
				table.setModel(bookDisplayModel);
			}
		}
		
		private void updateLoanDisplayModel( )
		{
			if( table.getModel() ==  loanDisplayModel && DisplayModel.lastQuery != null)
			{
				loanDisplayModel = DisplayModel.executeSQL(DisplayModel.lastQuery);
				table.setModel(loanDisplayModel);
			}
		}
		
		private void updateBorrowerDisplayModel( )
		{
			if( table.getModel() ==  borrowerDisplayModel && DisplayModel.lastQuery != null)
			{
				borrowerDisplayModel = DisplayModel.executeSQL(DisplayModel.lastQuery);
				table.setModel(borrowerDisplayModel);
			}
		}
		
	}
	

	
	
	public static void main(String[] args)
	{	
		// create the view
		view = new View();

	}//end main

}//end class
