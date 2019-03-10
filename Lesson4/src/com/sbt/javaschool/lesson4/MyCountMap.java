package com.sbt.javaschool.lesson4;

import java.util.*;

public class MyCountMap<T> implements CountMap<T> {
    private HashMap<T, Integer> map;

    public MyCountMap() {
        map = new HashMap<T, Integer>();
    }

    public MyCountMap(Map<T, Integer> map) {
        for (Map.Entry<T, Integer> item : map.entrySet()) {
            if (item.getValue() > 0)
                map.put(item.getKey(), item.getValue());
        }
    }

    public void add(T o) {
        map.put(o, map.getOrDefault(o, 0) + 1);
    }

    public int getCount(T o) {
        return map.getOrDefault(o, 0);
    }

    public int remove(T o) {
        int count = map.getOrDefault(o, 0);
        if (count == 1) map.remove(o);
        if (count != 0) map.put(o, count - 1);
        return count;
    }

    public int size() {
        return map.size();
    }

    public void addAll(CountMap<T> source) {
        Map<T, Integer> sourceMap = source.toMap();
        for (Map.Entry<T, Integer> item : sourceMap.entrySet()) {
            map.put(item.getKey(), map.getOrDefault(item.getKey(), 0) + item.getValue());
        }
    }

    public Map<T, Integer> toMap() {
        return (Map<T, Integer>) map.clone();
    }

    public void toMap(Map<T, Integer> destination) {
        destination.clear();
        for (Map.Entry<T, Integer> item : map.entrySet()) {
            destination.put(item.getKey(), item.getValue());
        }
    }

    public void addMap(Map<T, Integer> source) {
        for (Map.Entry<T, Integer> item : source.entrySet()) {
            if (item.getValue() > 0)
                map.put(item.getKey(), map.getOrDefault(item.getKey(), 0) + item.getValue());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<T, Integer> item : map.entrySet()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("element: ");
            sb.append(item.getKey());
            sb.append(" count: ");
            sb.append(item.getValue());
        }
        return sb.toString();
    }
}
