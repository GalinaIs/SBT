package ru.sbt.Lesson8;

import java.util.*;

public class MyIterator<T> implements Iterator<T> {
    private T[] array;
    private int position;
    private int size;
    private int lastReturnIndex;

    public MyIterator(T[] arr) throws IllegalArgumentException {
        array = arr;
        position = 0;
        size = array.length;
        lastReturnIndex = -1;
    }

    public boolean hasNext() {
        return position < size;
    }

    public T next() {
        if (position >= size) throw new NoSuchElementException("Достигнут конец массива");
        T element = array[position];
        lastReturnIndex = position;
        position++;

        return element;
    }

    public void remove() {
        if (lastReturnIndex == -1) throw new IllegalStateException("Невозможно удалить элемент");

        position = lastReturnIndex;
        Object[] newArray = new Object[size - 1];
        System.arraycopy(array, 0, newArray, 0, position);
        if (position + 1 < size)
            System.arraycopy(array, position + 1, newArray, position, size - position - 1);
        array = (T[]) newArray;
        size--;
        lastReturnIndex = -1;
    }
}
