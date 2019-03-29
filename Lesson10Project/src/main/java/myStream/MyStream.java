package myStream;

import java.util.*;
import java.util.function.*;

public class MyStream<T> {
    private Collection<T> collection;

    private MyStream(Collection<T> collection) {
        this.collection = collection;
    }

    public static <T> MyStream<T> of(Collection<T> collection) {
        return new MyStream<T>(collection);
    }

    public MyStream<T> filter(Predicate<? super T> predicate) {
        try {
            Collection<T> newCollection = collection.getClass().newInstance();
            for (T element : collection) {
                if (predicate.test(element))
                    newCollection.add(element);
            }
            return new MyStream<T>(newCollection);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public <E> MyStream<E> transform(Function<? super T, ? extends E> transformer) {
        try {
            Collection<E> newCollection = collection.getClass().newInstance();
            for (T element : collection) {
                newCollection.add(transformer.apply(element));
            }
            return new MyStream<E>(newCollection);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value) {
        Map <K, V> result = new HashMap<>();

        for (T element:collection) {
            result.put(key.apply(element), value.apply(element));
        }
        return result;
    }

    public void forEach(Consumer<? super T> action) {
        for (T element : collection) {
            action.accept(element);
        }
    }
}