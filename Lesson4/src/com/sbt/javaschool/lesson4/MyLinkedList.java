package com.sbt.javaschool.lesson4;

import java.util.*;

public class MyLinkedList<E> extends AbstractList<E> implements List<E> {
    private Entry<E> header;
    private int size;

    public MyLinkedList() {
        header = new Entry<E>();
        header.next = header.prev = header;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    @Override
    public boolean contains(Object o) {
        return findElement((E) o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Entry<E> nowEntry = header;

            @Override
            public boolean hasNext() {
                return nowEntry.next != header;
            }

            @Override
            public E next() {
                nowEntry = nowEntry.next;
                return nowEntry.element;
            }
        };
    }

    @Override
    public boolean add(E element) {
        Entry<E> newEntry = new Entry<>(element, header, header.prev);
        newEntry.prev.next = newEntry;
        newEntry.next.prev = newEntry;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E element = (E) o;
        Entry<E> entry = findElement(element);
        if (entry == null) return false;

        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        entry.next = entry.prev = null;
        entry.element = null;
        size--;
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        Entry<E> entry = entry(index);
        if (entry == null)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        return entry.element;
    }

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        Entry<E> entry = (index == size ? header : entry(index));
        if (entry == null)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        Entry<E> newEntry = new Entry<>(element, entry, entry.prev);
        entry.prev.next = newEntry;
        entry.prev = newEntry;
        size++;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        Entry<E> entry = entry(index);
        if (entry == null)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        E result = entry.element;
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        entry.next = entry.prev = null;
        entry.element = null;
        size--;
        return result;
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean result = true;
        for (E element : c) {
            if (!add(element) && result)
                result = false;
        }
        return result;
    }

    public boolean copy(Collection<? extends E> c) {
        clear();
        return addAll(c);
    }


    public void clear() {
        Entry<E> e = header;
        for (int i = 0; i < size; i++) {
            Entry<E> next = e.next;
            e.next = e.prev = null;
            e.element = null;
            e = next;
        }

        header.next = header.prev = header;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(iterator.next());
        }
        return sb.toString();
    }

    private static class Entry<E> {
        E element;
        Entry<E> next;
        Entry<E> prev;

        Entry() {
        }

        Entry(E element, Entry<E> next, Entry<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Entry<E> entry(int index) {
        if (index < 0 || index >= size) return null;

        Entry<E> e = header;
        for (int i = 0; i <= index; i++)
            e = e.next;

        return e;
    }

    private Entry<E> findElement(E element) {
        Entry<E> e = header;
        for (int i = 0; i < size; i++) {
            if (element.equals(e.element))
                return e;
            e = e.next;
        }
        return null;
    }
}
