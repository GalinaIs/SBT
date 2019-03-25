package ru.sbt.Lesson8.socialNetwork.messanger;

import java.time.LocalDate;

public class Message {
    private final long idSender;
    private final String textMessage;
    private final LocalDate date;

    public Message(long idSender, String textMessage, LocalDate date) {
        this.idSender = idSender;
        this.textMessage = textMessage;
        this.date = date;
    }

    public long getIdSender() {
        return idSender;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public LocalDate getDate() {
        return date;
    }
}