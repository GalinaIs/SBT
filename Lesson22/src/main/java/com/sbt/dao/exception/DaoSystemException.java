package com.sbt.dao.exception;

public class DaoSystemException extends DaoException {
    public DaoSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoSystemException(String message) {
        super(message);
    }
}
