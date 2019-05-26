package com.sbt.exception;

public class NoUniqueEntityException extends DaoException {
    public NoUniqueEntityException(String message) {
        super(message);
    }

    public NoUniqueEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
