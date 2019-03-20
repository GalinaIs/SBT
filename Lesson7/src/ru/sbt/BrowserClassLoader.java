package ru.sbt;

import java.io.*;

public class BrowserClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(name);
        if (!file.exists()) return findSystemClass(name);

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream ins = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[ins.available()];
            ins.read(buffer);

            String nameClass = file.getName().substring(0, file.getName().length() - 6);
            return defineClass(nameClass, buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Class couldn't be loaded");
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findClass(name);

        if (resolve) {
            resolveClass(c);
        }
        return c;
    }
}