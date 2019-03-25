package ru.sbt.Lesson8.socialNetwork.messanger;

import java.util.*;

public class ChatUser implements Chat {
    private final long idUser;
    private final List<Dialog> dialogsUser;

    public ChatUser(long idUser) {
        this.idUser = idUser;
        dialogsUser = new ArrayList<>();
    }

    public long getIdUser() {
        return idUser;
    }

    public boolean sendMessage(Dialog dialog, Message message) {
        if (dialogsUser.contains(dialog))
            return dialog.addMessage(message);
        return false;
    }

    public boolean deleteMessage(Dialog dialog, Message message) {
        if (dialogsUser.contains(dialog)) return dialog.deleteMessage(message);

        return false;
    }

    public List<Message> getHistoryDialog(Dialog dialog) {
        return dialog.getHistoryDialog();
    }

    public boolean beginNewDialog(Dialog dialog) {
        if (dialogsUser.contains(dialog)) return false;
        return dialogsUser.add(dialog);
    }

    public boolean deleteDialog(Dialog dialog) {
        return dialogsUser.remove(dialog);
    }
}
