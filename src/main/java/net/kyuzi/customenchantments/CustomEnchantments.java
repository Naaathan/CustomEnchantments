package net.kyuzi.customenchantments;

import org.bukkit.plugin.java.JavaPlugin;

import net.kyuzi.customenchantments.command.CustomEnchantmentsCommand;
import net.kyuzi.customenchantments.enchantment.CustomEnchantment;
import net.kyuzi.customenchantments.listener.CustomEnchantmentListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomEnchantments extends JavaPlugin {

    private static CustomEnchantments instance;
    private List<CustomEnchantment> enchantments;

    public static CustomEnchantments getInstance() {
        return instance;
    }

    public boolean disableEnchantment(String name) {
        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (enchantment.getName().equalsIgnoreCase(name)) {
                    enchantment.setEnabled(false);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean enableEnchantment(String name) {
        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (enchantment.getName().equalsIgnoreCase(name)) {
                    enchantment.setEnabled(true);
                    return true;
                }
            }
        }

        return false;
    }

    public List<CustomEnchantment> getEnchantments() {
        return Arrays.asList(enchantments.toArray(new CustomEnchantment[enchantments.size()]));
    }

    @Override
    public void onEnable() {
        File defaultConfig = new File(getDataFolder(), "config.yml");

        if (!defaultConfig.exists()) {
            saveDefaultConfig();
        }

        instance = this;
        enchantments = new ArrayList<>();

        getCommand("customenchantments").setExecutor(new CustomEnchantmentsCommand());
        getServer().getPluginManager().registerEvents(new CustomEnchantmentListener(), this);
    }

    public void registerEnchantment(CustomEnchantment enchantment) {
        if (CustomEnchantmentsAPI.getEnchantmentByName(enchantment.getName()) == null) {
            enchantments.add(enchantment);
        }
    }

    public void unregisterEnchantment(String name) {
        CustomEnchantment enchantment = CustomEnchantmentsAPI.getEnchantmentByName(name);

        if (enchantment != null) {
            enchantments.remove(enchantment);
        }
    }

}
