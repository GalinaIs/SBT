package ru.sbt.Lesson8.socialNetwork.profile;

import ru.sbt.Lesson8.socialNetwork.content.photo.ActionUserPhoto;
import ru.sbt.Lesson8.socialNetwork.content.photo.Photo;
import ru.sbt.Lesson8.socialNetwork.friends.FriendsUser;
import ru.sbt.Lesson8.socialNetwork.messanger.ChatUser;
import ru.sbt.Lesson8.socialNetwork.messanger.Dialog;
import ru.sbt.Lesson8.socialNetwork.messanger.Message;

import java.util.List;

public class ActionUserProfile implements ActionProfile {
    private UserProfile user;
    private ActionUserPhoto actionPhoto;
    private FriendsUser friendsUser;
    private ChatUser chatUser;

    public ActionUserProfile(UserProfile user) {
        this.user = user;
        actionPhoto = new ActionUserPhoto(user.getIdUser());
        friendsUser = new FriendsUser(user.getIdUser());
        chatUser = new ChatUser(user.getIdUser());
    }

    @Override
    public boolean addContent(Photo photo) {
        return actionPhoto.addContent(photo);
    }

    @Override
    public boolean deleteContent(Photo photo) {
        return actionPhoto.deleteContent(photo);
    }

    @Override
    public List<Photo> getAllPhoto() {
        return actionPhoto.getAllPhoto();
    }

    @Override
    public String getUserInfo() {
        return user.toString();
    }

    @Override
    public boolean addFriend(long idFriend) {
        return friendsUser.addFriend(idFriend);
    }

    @Override
    public boolean deleteFriend(long idFriend) {
        return friendsUser.deleteFriend(idFriend);
    }

    @Override
    public List<Long> getAllFriends() {
        return friendsUser.getAllFriends();
    }

    @Override
    public boolean sendMessage(Dialog dialog, Message message) {
        return chatUser.sendMessage(dialog, message);
    }

    @Override
    public boolean deleteMessage(Dialog dialog, Message message) {
        return chatUser.deleteMessage(dialog, message);
    }

    @Override
    public List<Message> getHistoryDialog(Dialog dialog) {
        return chatUser.getHistoryDialog(dialog);
    }

    @Override
    public boolean beginNewDialog(Dialog dialog) {
        return chatUser.beginNewDialog(dialog);
    }

    @Override
    public boolean deleteDialog(Dialog dialog) {
        return chatUser.deleteDialog(dialog);
    }
}
