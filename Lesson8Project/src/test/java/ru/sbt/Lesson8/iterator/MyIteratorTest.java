package ru.sbt.Lesson8.iterator;

import org.junit.*;
import ru.sbt.Lesson8.MyIterator;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MyIteratorTest {
    private MyIterator<Integer> myIterator;
    private static Integer[] array;

    @BeforeClass
    public static void setUpClass() {
        array = new Integer[]{-1, 10, -15, 0};
    }

    @Before
    public void setUp() {
        myIterator = new MyIterator<>(array);
    }

    @Test(expected = NoSuchElementException.class)
    public void testShouldThrowNSEE() {
        for (int i = 0; i <= array.length; i++)
            myIterator.next();
    }

    @Test
    public void testNext() {
        assertEquals(array[0], myIterator.next());
        myIterator.next();
        myIterator.next();
        assertEquals(array[3], myIterator.next());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemove() {
        myIterator.remove();
    }

    @Test
    public void testHasNext() {
        assertTrue(myIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextRemoveOperation() {
        assertEquals(array[0], myIterator.next());
        myIterator.remove();
        assertEquals(array[1], myIterator.next());
        assertEquals(array[2], myIterator.next());
        assertEquals(array[3], myIterator.next());
        myIterator.remove();
        myIterator.next();
    }
}