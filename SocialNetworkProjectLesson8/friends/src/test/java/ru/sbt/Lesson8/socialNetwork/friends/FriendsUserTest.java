package ru.sbt.Lesson8.socialNetwork.friends;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendsUserTest {
    private static FriendsUser friendsUser;
    private static long idUser;
    private static long idFriend;
    private static long idFriend1;

    @BeforeClass
    public static void setUpClass() {
        idUser = 1;
        idFriend = 2;
        idFriend1 = 3;
    }

    @Before
    public void setUp() {
        friendsUser = new FriendsUser(idUser);
    }

    @Test
    public void testAddFriend() {
        assertTrue(friendsUser.addFriend(idFriend));
        assertFalse(friendsUser.addFriend(idFriend));
    }

    @Test
    public void testDeleteFriend() {
        assertTrue(friendsUser.addFriend(idFriend));
        assertTrue(friendsUser.deleteFriend(idFriend));
        assertFalse(friendsUser.deleteFriend(idFriend));
    }

    @Test
    public void testGetAllFriends() {
        assertTrue(friendsUser.addFriend(idFriend));
        assertTrue(friendsUser.addFriend(idFriend1));

        assertEquals((Long) idFriend, friendsUser.getAllFriends().get(0));
        assertEquals((Long) idFriend1, friendsUser.getAllFriends().get(1));
        assertEquals(2, friendsUser.getAllFriends().size());
    }

    @Test
    public void testWorkingWithFriends() {
        assertTrue(friendsUser.addFriend(idFriend));
        assertTrue(friendsUser.addFriend(idFriend1));
        assertFalse(friendsUser.addFriend(idFriend1));
        assertFalse(friendsUser.addFriend(idFriend));

        assertEquals((Long) idFriend, friendsUser.getAllFriends().get(0));
        assertNotEquals((Long) idFriend1, friendsUser.getAllFriends().get(0));
        assertEquals((Long) idFriend1, friendsUser.getAllFriends().get(1));
        assertNotEquals((Long) idFriend, friendsUser.getAllFriends().get(1));

        assertTrue(friendsUser.deleteFriend(idFriend1));
        assertFalse(friendsUser.deleteFriend(idFriend1));

        assertEquals((Long) idFriend, friendsUser.getAllFriends().get(0));
        assertEquals(1, friendsUser.getAllFriends().size());
    }
}