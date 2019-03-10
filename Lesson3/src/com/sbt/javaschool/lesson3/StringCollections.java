package com.sbt.javaschool.lesson3;

import java.io.*;
import java.util.*;

public class StringCollections {
    private File file;

    public StringCollections(String fileName) {
        file = new File(fileName);
    }

    public int countDifferentWords() {
        HashSet<String> differWords = new HashSet<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String string = in.nextLine().toLowerCase();
                String[] words = string.split("\\s+");

                for (String word : words) {
                    if (word.length() > 0) {
                        differWords.add(word);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return differWords.size();
    }

    private class CompareLengthString implements Comparator<String> {
        public int compare(String a, String b) {
            if (a == b) return 0;
            if (a == null) return -b.length();
            if (b == null) return a.length();
            return a.length() - b.length();
        }
    }

    private class CompareString implements Comparator<String> {
        public int compare(String a, String b) {
            if (a == b) return 0;
            if (a == null) return "".compareTo(b);
            if (b == null) return a.compareTo("");
            return a.compareTo(b);
        }
    }

    public String sortedDifferentWords() {
        Comparator<String> myStringCompare = new CompareLengthString().thenComparing(new CompareString());
        TreeSet<String> differWords = new TreeSet<>(myStringCompare);
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String string = in.nextLine().toLowerCase();
                String[] words = string.split("\\s+");

                for (String word : words) {
                    if (word.length() > 0) {
                        differWords.add(word);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return collectionToString(differWords, ", ");
    }

    private <T> String collectionToString(Collection<T> col, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            if (sb.length() > 0) sb.append(separator);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public String frequencyDifferentWords() {
        HashMap<String, Integer> dictionary = new HashMap<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String string = in.nextLine().toLowerCase();
                String[] words = string.split("\\s+");

                for (String word : words) {
                    if (word.length() > 0) {
                        if (!dictionary.containsKey(word))
                            dictionary.put(word, 1);
                        else
                            dictionary.put(word, dictionary.get(word) + 1);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return mapToString(dictionary);
    }

    private <T, E> String mapToString(Map<T, E> map) {
        Iterator<Map.Entry<T, E>> it = map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            if (sb.length() > 0) sb.append(',').append(' ');
            Map.Entry<T, E> e = it.next();
            T key = e.getKey();
            E value = e.getValue();
            sb.append(key);
            sb.append(" = ");
            sb.append(value);
        }
        return sb.toString();
    }

    public String reverseString()  {
        ArrayList<String> reverseWords = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String string = in.nextLine();
                //reverseWords.add(0, new StringBuffer(string).reverse().toString());
                reverseWords.add(new StringBuffer(string).reverse().toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ReverseList<String> reverseList = new ReverseList<>(reverseWords);
        //return collectionToString(reverseWords, "\n");
        return collectionToString(reverseList, "\n");
    }

    private class ReverseList<T> extends ArrayList<T> implements Iterable<T> {
        private ArrayList<T> arrayList;
        private int currentSize;

        public ReverseList(ArrayList<T> newArray) {
            this.arrayList = newArray;
            this.currentSize = arrayList.size();
        }

        @Override
        public Iterator<T> iterator() {
            Iterator<T> it = new Iterator<T>() {

                private int currentIndex = currentSize - 1;

                @Override
                public boolean hasNext() {
                    return currentIndex > 0;
                }

                @Override
                public T next() {
                    return arrayList.get(currentIndex--);
                }

                @Override
                public void remove() {
                    arrayList.remove(currentIndex);
                }
            };
            return it;
        }
    }

    public String stringFromFile() {
        StringBuilder result = new StringBuilder();
        try {
            Scanner in = new Scanner(file);
            HashMap<Integer, String> stringFile = new HashMap<>();
            int number = 1;
            while (in.hasNextLine()) {
                String string = in.nextLine();
                stringFile.put(number++, string);
            }

            Scanner inConsole = new Scanner(System.in);
            System.out.print("Input numbers of line file: ");
            String string = inConsole.nextLine();
            String[] words = string.split("\\s+");
            for (String word : words) {
                if (result.length() > 0) result.append("\n");
                int num = Integer.parseInt(word);
                String stringLine = stringFile.get(num);
                result.append(num);
                result.append(": ");
                if (stringLine != null)
                    result.append(stringLine);
                else
                    result.append("Line with such number doesn't exist in file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result.toString();
    }
}