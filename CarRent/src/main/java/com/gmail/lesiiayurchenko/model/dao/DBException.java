package com.gmail.lesiiayurchenko.model.dao;

public class DBException extends Exception {

    public static final String DB_EXCEPTION = "DB exception";

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

}
