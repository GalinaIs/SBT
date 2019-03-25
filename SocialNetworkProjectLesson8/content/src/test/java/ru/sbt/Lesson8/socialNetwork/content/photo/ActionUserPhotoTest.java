package ru.sbt.Lesson8.socialNetwork.content.photo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ActionUserPhotoTest {
    private static ActionUserPhoto actionUserPhoto;
    private static long idUser;
    @Mock
    private static Photo photo;
    @Mock
    private static Photo photo1;

    @BeforeClass
    public static void setUpClass() {
        idUser = 1;
    }

    @Before
    public void setUp() {
        actionUserPhoto = new ActionUserPhoto(idUser);
    }

    @Test
    public void testAddPhoto() {
        assertTrue(actionUserPhoto.addContent(photo));
        assertFalse(actionUserPhoto.addContent(photo));
    }

    @Test
    public void testDeletePhoto() {
        assertTrue(actionUserPhoto.addContent(photo));
        assertTrue(actionUserPhoto.deleteContent(photo));
        assertFalse(actionUserPhoto.deleteContent(photo));
    }

    @Test
    public void testGetAllPhoto() {
        actionUserPhoto.addContent(photo);
        actionUserPhoto.addContent(photo1);

        assertEquals(photo, actionUserPhoto.getAllPhoto().get(0));
        assertNotEquals(photo1, actionUserPhoto.getAllPhoto().get(0));
        assertEquals(2, actionUserPhoto.getAllPhoto().size());
    }

    @Test
    public void testWorkingWithPhoto() {
        assertTrue(actionUserPhoto.addContent(photo));
        assertTrue(actionUserPhoto.addContent(photo1));
        assertFalse(actionUserPhoto.addContent(photo1));
        assertFalse(actionUserPhoto.addContent(photo));

        assertEquals(photo, actionUserPhoto.getAllPhoto().get(0));
        assertNotEquals(photo1, actionUserPhoto.getAllPhoto().get(0));
        assertEquals(photo1, actionUserPhoto.getAllPhoto().get(1));
        assertNotEquals(photo, actionUserPhoto.getAllPhoto().get(1));

        assertTrue(actionUserPhoto.deleteContent(photo1));
        assertFalse(actionUserPhoto.deleteContent(photo1));
        assertEquals(photo, actionUserPhoto.getAllPhoto().get(0));
        assertEquals(1, actionUserPhoto.getAllPhoto().size());
    }
}