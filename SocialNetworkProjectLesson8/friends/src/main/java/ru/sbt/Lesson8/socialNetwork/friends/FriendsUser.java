package ru.sbt.Lesson8.socialNetwork.friends;

import java.util.*;

public class FriendsUser implements Friends {
    private final long idUser;
    private final List<Long> friendsUser;

    public FriendsUser(long idUser) {
        this.idUser = idUser;
        this.friendsUser = new ArrayList<>();
    }

    @Override
    public boolean addFriend(long idFriend) {
        if (friendsUser.contains(idFriend)) return false;

        return friendsUser.add(idFriend);
    }

    @Override
    public boolean deleteFriend(long idFriend) {
        return friendsUser.remove(idFriend);
    }

    @Override
    public List<Long> getAllFriends() {
        return friendsUser;
    }
}