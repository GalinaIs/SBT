package ru.sbt.Lesson8.socialNetwork.friends;

import java.util.List;

public interface Friends {
    boolean addFriend(long idFriend);
    boolean deleteFriend(long idFriend);
    List<Long> getAllFriends();
}