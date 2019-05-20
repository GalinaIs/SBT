package com.sbt.dao.exception;

public class NoSuchEntityException extends DaoException {
    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(String message) {
        super(message);
    }
}
