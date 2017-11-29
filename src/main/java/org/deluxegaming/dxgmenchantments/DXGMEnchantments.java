package org.deluxegaming.dxgmenchantments;

import org.bukkit.plugin.java.JavaPlugin;
import org.deluxegaming.dxgmenchantments.command.DXGMEnchantmentsCommand;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.listener.EnchantmentListener;
import org.deluxegaming.dxgmenchantments.utility.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DXGMEnchantments extends JavaPlugin {

    private static DXGMEnchantments instance;
    private Configuration enchantConfiguration;
    private List<DXGMEnchantment> enchantments;

    public static DXGMEnchantments getInstance() {
        return instance;
    }

    public Configuration getEnchantConfiguration() {
        return enchantConfiguration;
    }

    public DXGMEnchantment getEnchantmentByDisplayName(String displayName) {
        if (!enchantments.isEmpty()) {
            for (DXGMEnchantment enchantment : enchantments) {
                if (enchantment.getDisplayName().equals(displayName)) {
                    return enchantment;
                }
            }
        }

        return null;
    }

    public DXGMEnchantment getEnchantmentByName(String name) {
        if (!enchantments.isEmpty()) {
            for (DXGMEnchantment enchantment : enchantments) {
                if (enchantment.getName().equalsIgnoreCase(name)) {
                    return enchantment;
                }
            }
        }

        return null;
    }

    public List<DXGMEnchantment> getEnchantments() {
        return Arrays.asList(enchantments.toArray(new DXGMEnchantment[enchantments.size()]));
    }

    public void addEnchantment(DXGMEnchantment enchantment) {
        if (getEnchantmentByName(enchantment.getName()) == null) {
            enchantments.add(enchantment);
        }
    }

    public void removeEnchantment(String name) {
        DXGMEnchantment enchantment = getEnchantmentByName(name);

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
        enchantConfiguration = new Configuration(new File(getDataFolder(), "enchantments.yml"));

        getCommand("dxgmenchantments").setExecutor(new DXGMEnchantmentsCommand());
        getServer().getPluginManager().registerEvents(new EnchantmentListener(), this);
    }

}
