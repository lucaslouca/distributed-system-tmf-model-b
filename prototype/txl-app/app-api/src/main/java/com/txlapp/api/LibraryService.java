package com.txlapp.api;

import com.txlapp.domain.Book;

import java.util.List;

public interface LibraryService {
    /**
     * Adds a new Book to the library
     *
     * @return the newly added Book
     */
    Book addBook(Book book);

    /**
     * Retrieve Book by id
     *
     * @return the book with id
     */
    Book findBook(Long id) throws BookDoesNotExistException;

    /**
     * Updates an existing Book
     *
     * @return the updated book
     */
    Book updateBook(Book book);

    /**
     * Removes an existing Book
     *
     * @throws BookDoesNotExistException
     */
    void removeBook(Book book) throws BookDoesNotExistException;

    /**
     * Retrieves all Books
     *
     * @return List<Book> of all the Books in the Library
     */
    List<Book> listAllBooks();
}
