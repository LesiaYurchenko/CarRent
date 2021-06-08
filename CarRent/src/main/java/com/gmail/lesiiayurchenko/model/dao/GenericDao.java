package com.gmail.lesiiayurchenko.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
    void create (T entity) throws DBException;
    T findById(int id) throws DBException;
    List<T> findAll() throws DBException;
    void update(T entity) throws DBException;
    void delete(int id) throws DBException;
    void close() throws DBException;
    default void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                System.out.println("Cannot close " + ac);
            }
        }
    }
}
