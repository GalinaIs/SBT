package ru.sbt.encrypt;

import java.io.*;

public class Crypt {
    public static void encryptFile(File file, String key) {
        cryptFile(file, key);
    }

    public static void decryptFile(File file, String key) {
        cryptFile(file, key);
    }

    private static void cryptFile(File file, String keyString) {
        if (file.isFile() && file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                byte key = (byte) (keyString.length() / 2);
                byte[] source = new byte[bis.available()];
                bis.read(source);

                for (int i = 0; i < source.length; i++) {
                    source[i] = (byte) (source[i] ^ key);
                }

                writeNewContentFile(file, source);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void writeNewContentFile(File file, byte[] info) {
        try (FileOutputStream fous = new FileOutputStream(file);
             BufferedOutputStream bous = new BufferedOutputStream(fous)) {
            bous.write(info, 0, info.length);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
