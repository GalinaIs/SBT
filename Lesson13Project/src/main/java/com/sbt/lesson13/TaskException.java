package com.sbt.lesson13;

public class TaskException extends RuntimeException {
    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
