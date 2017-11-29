package org.deluxegaming.customenchantments.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import org.deluxegaming.customenchantments.CustomEnchantments;
import org.deluxegaming.customenchantments.enchantment.CustomEnchantment;

public class CustomEnchantmentsPlugin extends JavaPlugin {

    public final void registerEnchantments(CustomEnchantment... enchantments) {
        if (enchantments == null || enchantments.length == 0) {
            return;
        }

        for (CustomEnchantment enchantment : enchantments) {
            CustomEnchantments.getInstance().addEnchantment(enchantment);
        }
    }

    public final void unregisterEnchantments(CustomEnchantment... enchantments) {
        if (enchantments == null || enchantments.length == 0) {
            return;
        }

        for (CustomEnchantment enchantment : enchantments) {
            CustomEnchantments.getInstance().removeEnchantment(enchantment.getName());
        }
    }

}
