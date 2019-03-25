package ru.sbt.Lesson8.socialNetwork.profile;

import ru.sbt.Lesson8.socialNetwork.content.ActionContent;
import ru.sbt.Lesson8.socialNetwork.content.photo.Photo;
import ru.sbt.Lesson8.socialNetwork.friends.Friends;
import ru.sbt.Lesson8.socialNetwork.messanger.Chat;

public interface ActionProfile extends Chat, Friends, ActionContent<Photo> {
    String getUserInfo();
}