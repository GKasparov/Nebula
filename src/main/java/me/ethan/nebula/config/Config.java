package me.ethan.nebula.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration
{
    private File file;
    private String name;
    private String directory;

    public Config(final JavaPlugin plugin, final String name, final String directory) {
        this.setName(name);
        this.setDirectory(directory);
        this.file = new File(directory, name + ".yml");
        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }
        this.load();
        this.save();
    }

    public Config(JavaPlugin plugin, String timers) {

    }

    public void load() {
        try {
            this.load(this.file);
        }
        catch (IOException | InvalidConfigurationException ex2) {
            final Exception ex = new Exception();
            final Exception e = ex;
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return (Configuration)this;
    }

    public void reloadConfiguration() {
        YamlConfiguration.loadConfiguration(this.file);
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDirectory(final String directory) {
        this.directory = directory;
    }
}
