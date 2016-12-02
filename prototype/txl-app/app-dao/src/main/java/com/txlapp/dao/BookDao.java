package com.txlapp.dao;

import com.txlapp.domain.Book;
import com.txlcommon.TXLGenericDao;

import java.util.List;

public interface BookDao extends TXLGenericDao<Book> {

    List<Book> listAll();

}
