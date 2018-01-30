package net.kyuzi.customenchantments.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import net.kyuzi.customenchantments.CustomEnchantments;
import net.kyuzi.customenchantments.enchantment.CustomEnchantment;

public class CustomEnchantmentsPlugin extends JavaPlugin {

    public final void registerEnchantments(CustomEnchantment... enchantments) {
        if (enchantments == null || enchantments.length == 0) {
            return;
        }

        for (CustomEnchantment enchantment : enchantments) {
            CustomEnchantments.getInstance().registerEnchantment(enchantment);
        }
    }

    public final void unregisterEnchantments(CustomEnchantment... enchantments) {
        if (enchantments == null || enchantments.length == 0) {
            return;
        }

        for (CustomEnchantment enchantment : enchantments) {
            CustomEnchantments.getInstance().unregisterEnchantment(enchantment.getName());
        }
    }

}
