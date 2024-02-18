package com.example.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.example.dao.LibraryDAO;
import com.example.model.Book;
import com.example.model.Transaction;
import com.example.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class LibraryDAOImplementation implements LibraryDAO {

	private Configuration con = null;
	private SessionFactory sf = null;
	private Session session = null;

	@Override
	public void connect() {

		con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class)
				.addAnnotatedClass(User.class).addAnnotatedClass(Transaction.class);
		ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		sf = con.buildSessionFactory(reg);
		session = sf.openSession();
		session.beginTransaction();

	}

	@Override
	public void addBook(Book book) {

		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			session.save(book);
			session.getTransaction().commit();
			
			System.out.println("Book added successfully!");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void updateBook(int bookId, Book updatedBook) {

		try {

			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Book existingBook = session.get(Book.class, bookId);

			if (existingBook != null) {
				existingBook.setTitle(updatedBook.getTitle());
				existingBook.setAuthor(updatedBook.getAuthor());
				existingBook.setGenre(updatedBook.getGenre());
				existingBook.setQuantity(updatedBook.getQuantity());

				session.update(existingBook);

				session.getTransaction().commit();
				System.out.println("Book Updated Sucessfull!");
			} else {
				System.out.println("Book not found");
			}

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			System.out.println("Error updating book!");
		}
	}

	@Override
	public void deleteBook(int bookId) {

		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Book book = session.get(Book.class, bookId);

			if (book != null) {
				session.delete(book);

				session.getTransaction().commit();

				System.out.println("Book deleted sucessfully!");
			} else {
				System.out.println("Book not found!");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error occured while deleting book");
		}

	}

	@Override
	public List<Book> getAllBook() {
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Query<Book> query = session.createQuery("from Book", Book.class);
			List<Book> books = query.getResultList();

			return books;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void addUser(User user) {
		try {

			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			session.save(user);
			session.getTransaction().commit();
			
			System.out.println("User added successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

	}

	@Override
	public void updateUser(int userId, User updatedUser) {

		try {

			User existingUser = session.get(User.class, userId);

			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			if (existingUser != null) {
				existingUser.setUserName(updatedUser.getUserName());
				existingUser.setUserEmail(updatedUser.getUserEmail());
				existingUser.setUserAddress(updatedUser.getUserAddress());

				session.update(existingUser);
				session.getTransaction().commit();

				System.out.println("User updated sucessfully!");
			} else {
				System.out.println("User not foun!");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error occured while updating user!");
		}

	}

	@Override
	public void deleteUser(int userId) {

		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			User user = session.get(User.class, userId);
			session.delete(user);
			session.getTransaction().commit();
			
			System.out.println("User deleted successfully!");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public List<User> getAllUser() {
		try {

			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Query<User> query = session.createQuery("from User", User.class);
			List<User> users = query.getResultList();

			return users;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void addTransaction(Transaction transaction) {
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			session.save(transaction);
			session.getTransaction().commit();
			
			System.out.println("Transaction added successfully!");


		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void updateTransaction(int transactionId, Transaction updatedTransaction) {

		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Transaction existingTransaction = session.get(Transaction.class, transactionId);

			if (existingTransaction != null) {
				existingTransaction.setBorrowDate(updatedTransaction.getBorrowDate());
				existingTransaction.setDueDate(updatedTransaction.getDueDate());
				existingTransaction.setReturnDate(updatedTransaction.getReturnDate());

				if (updatedTransaction.getUser() != null && updatedTransaction.getUser().getUserId() != 0) {
					User user = session.get(User.class, updatedTransaction.getUser().getUserId());
					existingTransaction.setUser(user);
					System.out.println("User updated sucessfully!");
				}

				if (updatedTransaction.getBook() != null && updatedTransaction.getBook().getBookId() != 0) {
					Book book = session.get(Book.class, updatedTransaction.getBook().getBookId());
					existingTransaction.setBook(book);
					System.out.println("Book updated sucessfully!");
				}

				session.update(existingTransaction);
				session.getTransaction().commit();

				System.out.println("Transaction updated sucessfully!");
			} else {
				System.out.println("Transaction not foun!");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error occured while updating Transaction!");
		}

	}

	@Override
	public void deleteTransaction(int transactionId) {

		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Transaction deletionTransaction = session.get(Transaction.class, transactionId);

			if (deletionTransaction != null) {
				session.delete(deletionTransaction);
				session.getTransaction().commit();
				
				System.out.println("Transaction deleted successfully!");

			} else {
				System.out.println("Transaction not found!");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public List<Transaction> getAllTransaction() {

		try {

			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Query<Transaction> query = session.createQuery("from Transaction", Transaction.class);
			List<Transaction> transactions = query.getResultList();

			return transactions;

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	@Override
	public Book fetchBook(int bookId) {

		try {

			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}

			Book book = session.get(Book.class, bookId);

			return book;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	@Override
	public User fetchUser(int userId) {

		try {
			
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			
			User user = session.get(User.class, userId);

			return user;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	@Override
	public Transaction fetchTransaction(int transactionId) {
		try {
			
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			
			Transaction transaction = session.get(Transaction.class, transactionId);

			return transaction;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	@Override
	public void closeConnection() {

		session.close();
	}

}
