package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Book;
import com.example.model.Transaction;
import com.example.model.User;

public interface LibraryDAO {
	void connect();

	
	void addBook(Book book);
	void updateBook(int bookId, Book updatedBook);
	void deleteBook(int bookId);
	List<Book> getAllBook();

	
	void addUser(User user);
	void updateUser(int userId, User updatedUser);
	void deleteUser(int userId);
	List<User> getAllUser();

	
	void addTransaction(Transaction transaction);
	void updateTransaction(int transactionId, Transaction updatedTransaction);
	void deleteTransaction(int transactionId);
	List<Transaction> getAllTransaction();

	
	Book fetchBook(int bookId);
	User fetchUser(int userId);
	Transaction fetchTransaction(int transactionId);

	
	void closeConnection();
}
