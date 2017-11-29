package org.deluxegaming.dxgmenchantments.utility;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.deluxegaming.dxgmenchantments.DXGMEnchantments;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private File file;
    private FileConfiguration config;

    public Configuration(File file) {
        this.file = file;

        reloadConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        if (!file.exists()) {
            DXGMEnchantments.getInstance().saveResource(file.getName(), false);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Bukkit.getLogger().severe("Error creating " + file.getName() + ": " + e.getMessage());
                    return;
                }
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Error saving " + file.getName() + ": " + e.getMessage());
        }
    }

}
