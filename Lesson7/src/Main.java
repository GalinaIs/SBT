import ru.sbt.*;
import ru.sbt.encrypt.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        //testPlugin();
        testCrypt();
    }

    private static void testPlugin() {
        PluginManager pluginManager = new PluginManager("D:/JavaSBTGit/SBT/Lesson7/src/plugins");
        pluginManager.loadPlugins();
        pluginManager.runPlugins();
    }

    private static void testCrypt() {
        String key = "11111";
        File dirClasses = new File("D:\\JavaSBTGit\\SBT\\Lesson7\\src\\encryptPlugins");
        try {
            EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader(key, dirClasses,
                    ClassLoader.getSystemClassLoader());
            File[] listFiles;
            if ((listFiles = dirClasses.listFiles()) != null) {
                for (File file : listFiles) {
                    Crypt.encryptFile(file, key);
                }

                for (File file : listFiles) {
                    if (file.isFile()) {
                        Class c1 = encryptedClassLoader.loadClass(file.getName());
                        System.out.println(c1.getName());
                    }
                }
            }
        } catch (IllegalArgumentException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}