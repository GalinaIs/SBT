package ru.sbt.Lesson8.socialNetwork.messanger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DialogTest {
    private Dialog dialog;

    @Mock
    private Message message;
    @Mock
    private Message message1;

    @Before
    public void setUp() {
        dialog = new Dialog();
    }

    @Test
    public void testAddMessage(){
        assertTrue(dialog.addMessage(message));
        assertFalse(dialog.addMessage(message));
    }

    @Test
    public void testDeleteMessage(){
        assertTrue(dialog.addMessage(message));
        assertTrue(dialog.deleteMessage(message));
        assertFalse(dialog.deleteMessage(message));
    }

    @Test
    public void testGetHistoryDialog(){
        dialog.addMessage(message);
        dialog.addMessage(message1);

        assertEquals(message, dialog.getHistoryDialog().get(0));
        assertNotEquals(message1, dialog.getHistoryDialog().get(0));
        assertEquals(2, dialog.getHistoryDialog().size());
    }

    @Test
    public void testWorkingWithDialog() {
        assertTrue(dialog.addMessage(message));
        assertTrue(dialog.addMessage(message1));
        assertFalse(dialog.addMessage(message1));
        assertFalse(dialog.addMessage(message));

        assertEquals(message, dialog.getHistoryDialog().get(0));
        assertNotEquals(message1, dialog.getHistoryDialog().get(0));
        assertEquals(message1, dialog.getHistoryDialog().get(1));
        assertNotEquals(message, dialog.getHistoryDialog().get(1));

        assertTrue(dialog.deleteMessage(message1));
        assertFalse(dialog.deleteMessage(message1));
        assertEquals(message, dialog.getHistoryDialog().get(0));
        assertEquals(1, dialog.getHistoryDialog().size());
    }
}