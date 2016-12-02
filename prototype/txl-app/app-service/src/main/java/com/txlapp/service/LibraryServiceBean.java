package com.txlapp.service;

import com.txlapp.api.BookDoesNotExistException;
import com.txlapp.api.LibraryService;
import com.txlapp.dao.BookDao;
import com.txlapp.domain.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LibraryServiceBean implements LibraryService {

    BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        bookDao.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book findBook(Long id) throws BookDoesNotExistException {
        Book book = bookDao.findById(id);
        if (book == null) {
            throw new BookDoesNotExistException("The requested Book with id:" + id + " does not exist.");
        }
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        Book mergedBook = bookDao.update(book);
        return mergedBook;
    }

    @Override
    @Transactional
    public void removeBook(Book book) throws BookDoesNotExistException {
        Book mergedBook = this.findBook(book.getId());

        if (mergedBook == null) {
            throw new BookDoesNotExistException("Attempt to remove Book: " + book + " failed because it does not Exist.");
        } else {
            bookDao.remove(mergedBook);
        }
    }

    @Override
    @Transactional
    public List<Book> listAllBooks() {
        return bookDao.listAll();
    }

}
