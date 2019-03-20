package ru.sbt;

import java.io.File;
import java.util.ArrayList;

public class PluginManager {
    private final String pluginRootDirectory;
    private ArrayList<Plugin> allPlugins = new ArrayList<>();

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public void loadPlugins() {
        File dir = new File(pluginRootDirectory);
        File[] listFiles;
        try {
            if (dir.isDirectory() && (listFiles = dir.listFiles()) != null) {
                for (File item : listFiles) {
                    loadPluginsFromDirectory(item);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    private void loadPluginsFromDirectory(File dir)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        File[] listDir;
        if (dir.isDirectory() && (listDir = dir.listFiles()) != null) {
            for (File item : listDir) {
                if (item.isFile()) {
                    Plugin plugin = load(dir.getName(), item.getName());
                    if (plugin != null) allPlugins.add(plugin);
                }
            }
        }
    }

    private Plugin load(String pluginName, String pluginClassName)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!pluginClassName.endsWith(".class")) return null;

        BrowserClassLoader browserClassLoader = new BrowserClassLoader();
        return (Plugin) browserClassLoader.
                loadClass(pluginRootDirectory + "/" + pluginName + "/" + pluginClassName).newInstance();
    }

    public void runPlugins() {
        for (Plugin plugin : allPlugins) {
            plugin.doUsefull();
        }
    }
}