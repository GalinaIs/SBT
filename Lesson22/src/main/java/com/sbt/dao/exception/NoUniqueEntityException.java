package com.sbt.dao.exception;

public class NoUniqueEntityException extends DaoException {
    public NoUniqueEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUniqueEntityException(String message) {
        super(message);
    }
}
