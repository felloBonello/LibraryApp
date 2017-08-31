/*INSERTS for Author table
Copy and paste this script into the MySQL editor and run it to 
populate the tables with some sample data.
*/

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Deitel', 'Paul' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Deitel', 'Harvey' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Horton', 'Ivor' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Horstmann', 'Cay' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Gaddis', 'Tony' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Liang', 'Y.Daniel' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Lewis', 'John' );

INSERT INTO `info5051_books`.`author`
(`AuthorID`,`Last_Name`,`First_name`)
VALUES(authorID, 'Loftus', 'William' );

/*inserts for Book table*/

INSERT INTO `info5051_books`.`book`
(`BookID`,`Title`,`ISBN`,`Edition_Number`,`Subject`,`Available`)
VALUES(bookID,'Java:How to Program, Early Objects','9780133807806','10',"java programming",Available);

INSERT INTO `info5051_books`.`book`
(`BookID`,`Title`,`ISBN`,`Edition_Number`,`Subject`,`Available`)
VALUES(bookID,'Java:Software Solutions Foundations of Program Design','9780321245830','4',"java programming",Available);

INSERT INTO `info5051_books`.`book`
(`BookID`,`Title`,`ISBN`,`Edition_Number`,`Subject`,`Available`)
VALUES(bookID,'Java:From Control Structures Through Objects','9780321479273','3',"java programming",Available);

INSERT INTO `info5051_books`.`book`
(`BookID`,`Title`,`ISBN`,`Edition_Number`,`Subject`,`Available`)
VALUES(bookID,'Java Programming Comprehensive Edition','9780132936521','9',"java programming",Available);

INSERT INTO `info5051_books`.`book`
(`BookID`,`Title`,`ISBN`,`Edition_Number`,`Subject`,`Available`)
VALUES(bookID,'Ivor Horton''s Beginning Java 2 JDK5 Edition','9780764568749','5',"java programming",Available);

/*inserts for Book_Author table*/

INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(1,1);
INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(1,2);

INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(2,7);
INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(2,8);


INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(3,5);

INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(4,6);

INSERT INTO `info5051_books`.`book_author`
(`Book_BookID`,`Author_AuthorID`)VALUES(5,3);

/*Inserts for Borrowers*/
INSERT INTO `info5051_books`.`borrower`
(`Borrower_ID`,`Last_Name`,`First_Name`,`Borrower_email`)
VALUES(Borrower_ID, 'Doe', 'John','jdoe@fanshaweonline.ca');

INSERT INTO `info5051_books`.`borrower`
(`Borrower_ID`,`Last_Name`,`First_Name`,`Borrower_email`)
VALUES(Borrower_ID, 'Smith', 'Mary','msmith@fanshaweonline.ca');

/*Insert for Book_Loan*/

INSERT INTO `info5051_books`.`book_loan`(`Book_BookID`,`Borrower_Borrower_ID`,`comment`,`Date_out`)
VALUES(1,1,'Borrowed on June 26, 2016','2016-07-16');

/*Update the Book table to set the "available" column value to 0 */
UPDATE `book` SET `available` = 0 WHERE bookID = 1;