package com.sbt.lesson14.chat;

public class Message {
    private final String loginUserTo;
    private final String loginUserFrom;
    private final String message;

    public Message(String loginUserTo, String loginUserFrom, String message) {
        this.loginUserTo = loginUserTo;
        this.loginUserFrom = loginUserFrom;
        this.message = message;
    }

    public String getLoginUserTo() {
        return loginUserTo;
    }

    public String getLoginUserFrom() {
        return loginUserFrom;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message: " +
                "userTo:'" + loginUserTo + '\'' +
                ", userFrom:'" + loginUserFrom + '\'' +
                ", message:'" + message + '\'' +
                '.';
    }
}
