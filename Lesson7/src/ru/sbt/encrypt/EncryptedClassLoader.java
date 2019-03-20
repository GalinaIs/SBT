package ru.sbt.encrypt;

import java.io.*;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) throws IllegalArgumentException {
        super(parent);
        if (!dir.isDirectory()) throw new IllegalArgumentException(dir + " must be a directory");
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(dir + "/" + name);
        if (!file.exists()) return findSystemClass(name);

        Crypt.decryptFile(file, key);

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream ins = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[(int) file.length()];
            ins.read(buffer);

            String nameClass = file.getName().substring(0, file.getName().length() - 6);
            return defineClass(nameClass, buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name + " class couldn't be loaded");
        }
    }
}
