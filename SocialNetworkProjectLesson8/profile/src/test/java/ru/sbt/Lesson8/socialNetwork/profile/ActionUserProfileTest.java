package ru.sbt.Lesson8.socialNetwork.profile;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.sbt.Lesson8.socialNetwork.content.photo.Photo;
import ru.sbt.Lesson8.socialNetwork.messanger.Dialog;
import ru.sbt.Lesson8.socialNetwork.messanger.Message;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActionUserProfileTest {
    private ActionUserProfile actionUserProfile;

    private static UserProfile userProfile;
    @Mock
    private Photo photo;
    @Mock
    private Photo photo1;

    private static long idFriend1;
    private static long idFriend2;

    @Mock
    private Dialog dialog;
    @Mock
    private Message message;
    @Mock
    private Message message1;

    @BeforeClass
    public static void setUpClass() {
        userProfile = new UserProfile("John", 18, "john", City.Moscow);
        idFriend1 = 2;
        idFriend2 = 3;
    }

    @Before
    public void setUp() {
        actionUserProfile = new ActionUserProfile(userProfile);
    }

    @Test
    public void addPhoto() {
        assertTrue(actionUserProfile.addContent(photo));
        assertFalse(actionUserProfile.addContent(photo));
    }

    @Test
    public void deletePhoto() {
        assertTrue(actionUserProfile.addContent(photo));
        assertTrue(actionUserProfile.deleteContent(photo));
        assertFalse(actionUserProfile.deleteContent(photo));
    }

    @Test
    public void getAllPhoto() {
        actionUserProfile.addContent(photo);
        actionUserProfile.addContent(photo1);

        assertEquals(photo, actionUserProfile.getAllPhoto().get(0));
        assertEquals(photo1, actionUserProfile.getAllPhoto().get(1));
        assertEquals(2, actionUserProfile.getAllPhoto().size());
    }

    @Test
    public void getUserInfo() {
        assertEquals(userProfile.toString(), actionUserProfile.getUserInfo());
    }

    @Test
    public void addFriend() {
        assertTrue(actionUserProfile.addFriend(idFriend1));
        assertFalse(actionUserProfile.addFriend(idFriend1));
    }

    @Test
    public void deleteFriend() {
        assertTrue(actionUserProfile.addFriend(idFriend1));
        assertTrue(actionUserProfile.deleteFriend(idFriend1));
        assertFalse(actionUserProfile.deleteFriend(idFriend1));
    }

    @Test
    public void getAllFriends() {
        actionUserProfile.addFriend(idFriend1);
        actionUserProfile.addFriend(idFriend2);

        assertEquals((Long)idFriend1, actionUserProfile.getAllFriends().get(0));
        assertEquals((Long) idFriend2, actionUserProfile.getAllFriends().get(1));
        assertEquals(2, actionUserProfile.getAllFriends().size());
    }

    @Test
    public void beginNewDialog() {
        assertTrue(actionUserProfile.beginNewDialog(dialog));
        assertFalse(actionUserProfile.beginNewDialog(dialog));
    }

    @Test
    public void deleteDialog() {
        assertTrue(actionUserProfile.beginNewDialog(dialog));
        assertTrue(actionUserProfile.deleteDialog(dialog));
        assertFalse(actionUserProfile.deleteDialog(dialog));
    }

    @Test
    public void sendMessage() {
        actionUserProfile.beginNewDialog(dialog);
        when(dialog.addMessage(message)).thenReturn(true).thenReturn(false);

        assertTrue(actionUserProfile.sendMessage(dialog, message));
        assertFalse(actionUserProfile.sendMessage(dialog, message));
    }

    @Test
    public void deleteMessage() {
        actionUserProfile.beginNewDialog(dialog);
        when(dialog.addMessage(message)).thenReturn(true);
        when(dialog.deleteMessage(message)).thenReturn(true).thenReturn(false);

        assertTrue(actionUserProfile.sendMessage(dialog, message));
        assertTrue(actionUserProfile.deleteMessage(dialog, message));
        assertFalse(actionUserProfile.deleteMessage(dialog, message));
    }

    @Test
    public void getHistoryDialog() {
        actionUserProfile.beginNewDialog(dialog);
        when(dialog.getHistoryDialog()).thenReturn(Arrays.asList(message, message1));

        assertEquals(message, actionUserProfile.getHistoryDialog(dialog).get(0));
        assertNotEquals(message1, actionUserProfile.getHistoryDialog(dialog).get(0));
        assertEquals(2, actionUserProfile.getHistoryDialog(dialog).size());
    }
}