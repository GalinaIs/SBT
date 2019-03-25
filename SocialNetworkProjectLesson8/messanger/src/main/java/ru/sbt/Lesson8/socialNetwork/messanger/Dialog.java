package ru.sbt.Lesson8.socialNetwork.messanger;

import java.util.*;

public class Dialog {
    private static long count = 0;
    private final long idDialog;
    private final List<Message> historyDialog;

    public Dialog() {
        count++;
        this.idDialog = count;
        historyDialog = new ArrayList<>();
    }

    public long getIdDialog() {
        return idDialog;
    }

    public List<Message> getHistoryDialog() {
        return historyDialog;
    }

    public boolean addMessage(Message message) {
        if (historyDialog.contains(message)) return false;

        return historyDialog.add(message);
    }

    public boolean deleteMessage(Message message) {
        return historyDialog.remove(message);
    }
}
