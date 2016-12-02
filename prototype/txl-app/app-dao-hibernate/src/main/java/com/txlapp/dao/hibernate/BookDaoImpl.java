package com.txlapp.dao.hibernate;

import com.txlapp.dao.BookDao;
import com.txlapp.domain.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDaoImpl implements BookDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Insert a new Book into the database.
     *
     * @throws RuntimeException if id is not null
     */
    @Override
    public Book save(Book book) {
        Session session = getSession();

        session.save(book);

        return book;
    }

    /**
     * Find a Book by id.
     *
     * @return Book if it exists, else it returns null.
     */
    @Override
    public Book findById(Long id) {
        Book book = (Book) getSession().get(Book.class, id);
        return book;
    }

    /**
     * Update a Book in the database. If book does not exist (id==null) the book
     * is inserted into the database, else it gets updated.
     *
     * @return Book the persisted book
     */
    @Override
    public Book update(Book book) {
        return null;
    }

    /**
     * Remove an existing book from the database.
     *
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public void remove(Book book) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> listAll() {
        List<Book> books = getSession().createCriteria(Book.class).list();
        return books;
    }
}
