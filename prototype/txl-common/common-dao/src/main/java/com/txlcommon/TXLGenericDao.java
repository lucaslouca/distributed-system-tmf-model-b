package com.txlcommon;


public interface TXLGenericDao<T> {

    T save(T o);

    T findById(Long id);

    T update(T o);

    void remove(T o);


}
