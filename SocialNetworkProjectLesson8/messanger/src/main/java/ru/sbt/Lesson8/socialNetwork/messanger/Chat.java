package ru.sbt.Lesson8.socialNetwork.messanger;

import java.util.List;

public interface Chat {
    boolean sendMessage(Dialog dialog, Message message);
    boolean deleteMessage(Dialog dialog, Message message);
    List<Message> getHistoryDialog(Dialog dialog);
    boolean beginNewDialog(Dialog dialog);
    boolean deleteDialog(Dialog dialog);
}