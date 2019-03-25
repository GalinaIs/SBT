package ru.sbt.Lesson8.socialNetwork.messanger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatUserTest {
    private ChatUser chatUser;
    private static long idUser;

    @Mock
    private Dialog dialog;
    @Mock
    private Message message;
    @Mock
    private Message message1;

    @BeforeClass
    public static void setUpClass() {
        idUser = 1;
    }

    @Before
    public void setUp() {
        chatUser = new ChatUser(idUser);
    }

    @Test
    public void beginNewDialog() {
        assertTrue(chatUser.beginNewDialog(dialog));
        assertFalse(chatUser.beginNewDialog(dialog));
    }

    @Test
    public void deleteDialog() {
        assertTrue(chatUser.beginNewDialog(dialog));
        assertTrue(chatUser.deleteDialog(dialog));
        assertFalse(chatUser.deleteDialog(dialog));
    }

    @Test
    public void sendMessage() {
        chatUser.beginNewDialog(dialog);
        when(dialog.addMessage(message)).thenReturn(true).thenReturn(false);

        assertTrue(chatUser.sendMessage(dialog, message));
        assertFalse(chatUser.sendMessage(dialog, message));
    }

    @Test
    public void deleteMessage() {
        chatUser.beginNewDialog(dialog);
        when(dialog.addMessage(message)).thenReturn(true);
        when(dialog.deleteMessage(message)).thenReturn(true).thenReturn(false);

        assertTrue(chatUser.sendMessage(dialog, message));
        assertTrue(chatUser.deleteMessage(dialog, message));
        assertFalse(chatUser.deleteMessage(dialog, message));
    }

    @Test
    public void getHistoryDialog() {
        chatUser.beginNewDialog(dialog);
        when(dialog.getHistoryDialog()).thenReturn(Arrays.asList(message, message1));

        assertEquals(message, chatUser.getHistoryDialog(dialog).get(0));
        assertNotEquals(message1, chatUser.getHistoryDialog(dialog).get(0));
        assertEquals(2, chatUser.getHistoryDialog(dialog).size());
    }

    @Test
    public void testWorkingWithChat() {
        chatUser.beginNewDialog(dialog);

        when(dialog.addMessage(message)).thenReturn(true).thenReturn(false);
        when(dialog.addMessage(message1)).thenReturn(true).thenReturn(false);

        assertTrue(chatUser.sendMessage(dialog, message));
        assertTrue(chatUser.sendMessage(dialog, message1));
        assertFalse(chatUser.sendMessage(dialog, message));
        assertFalse(chatUser.sendMessage(dialog, message1));

        when(dialog.getHistoryDialog()).thenReturn(Arrays.asList(message, message1));

        assertEquals(message, chatUser.getHistoryDialog(dialog).get(0));
        assertNotEquals(message1, chatUser.getHistoryDialog(dialog).get(0));
        assertEquals(message1, chatUser.getHistoryDialog(dialog).get(1));
        assertNotEquals(message, chatUser.getHistoryDialog(dialog).get(1));

        when(dialog.deleteMessage(message1)).thenReturn(true).thenReturn(false);

        assertTrue(chatUser.deleteMessage(dialog, message1));
        assertFalse(chatUser.deleteMessage(dialog, message1));

        when(dialog.getHistoryDialog()).thenReturn(Arrays.asList(message));

        assertEquals(message, chatUser.getHistoryDialog(dialog).get(0));
        assertEquals(1, chatUser.getHistoryDialog(dialog).size());
    }
}