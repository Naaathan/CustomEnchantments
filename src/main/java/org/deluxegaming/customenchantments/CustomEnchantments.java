package org.deluxegaming.customenchantments;

import org.bukkit.plugin.java.JavaPlugin;

import org.deluxegaming.customenchantments.command.CustomEnchantmentsCommand;
import org.deluxegaming.customenchantments.enchantment.CustomEnchantment;
import org.deluxegaming.customenchantments.listener.CustomEnchantmentListener;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CustomEnchantments extends JavaPlugin {

    private static CustomEnchantments instance;
    private List<CustomEnchantment> enchantments;

    public static CustomEnchantments getInstance() {
        return instance;
    }

    public CustomEnchantment getEnchantmentByDisplayName(String displayName) {
        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (enchantment.getDisplayName().equals(displayName)) {
                    return enchantment;
                }
            }
        }

        return null;
    }

    public CustomEnchantment getEnchantmentByName(String name) {
        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (enchantment.getName().equalsIgnoreCase(name)) {
                    return enchantment;
                }
            }
        }

        return null;
    }

    public List<CustomEnchantment> getEnchantments() {
        return Arrays.asList(enchantments.toArray(new CustomEnchantment[enchantments.size()]));
    }

    public void addEnchantment(CustomEnchantment enchantment) {
        if (getEnchantmentByName(enchantment.getName()) == null) {
            enchantments.add(enchantment);
        }
    }

    public void removeEnchantment(String name) {
        CustomEnchantment enchantment = getEnchantmentByName(name);

        if (enchantment != null) {
            enchantments.remove(enchantment);
        }
    }

    @Override
    public void onEnable() {
        File defaultConfig = new File(getDataFolder(), "config.yml");

        if (!defaultConfig.exists()) {
            saveDefaultConfig();
        }

        instance = this;

        getCommand("customenchantments").setExecutor(new CustomEnchantmentsCommand());
        getServer().getPluginManager().registerEvents(new CustomEnchantmentListener(), this);
    }

}
