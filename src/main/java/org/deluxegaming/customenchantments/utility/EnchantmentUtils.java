package org.deluxegaming.customenchantments.utility;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.deluxegaming.customenchantments.CustomEnchantments;
import org.deluxegaming.customenchantments.enchantment.CustomEnchantment;
import org.deluxegaming.customenchantments.enchantment.CustomEnchantmentTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentUtils {

    public static boolean addEnchantment(ItemStack itemStack, CustomEnchantment enchantment, int level) {
        if (!enchantment.canEnchantItem(itemStack)) {
            return false;
        }

        Map<CustomEnchantment, Integer> itemEnchantments = getEnchantments(itemStack);

        if (!itemEnchantments.isEmpty()) {
            for (CustomEnchantment itemEnchantment : itemEnchantments.keySet()) {
                if (itemEnchantment.equals(enchantment)) {
                    return false;
                }
            }
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = (itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>());

        // order item enchantments by tier and add to lore
        return true;
    }

    public static boolean removeEnchantment(ItemStack itemStack, CustomEnchantment enchantment) {
        if (!hasEnchantment(itemStack, enchantment)) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        for (String line : lore) {
            if (line.contains(enchantment.getDisplayName())) {
                lore.remove(line);
                break;
            }
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return true;
    }

    public static List<ItemStack> getEnchantableItems(Player player) {
        List<ItemStack> enchantableItems = new ArrayList<>();

        if (CustomEnchantmentTarget.isTarget(player.getItemInHand())) {
            enchantableItems.add(player.getItemInHand());
        }

        if (CustomEnchantmentTarget.isTarget(player.getInventory().getArmorContents()[0])) {
            enchantableItems.add(player.getInventory().getArmorContents()[0]);
        }

        if (CustomEnchantmentTarget.isTarget(player.getInventory().getArmorContents()[1])) {
            enchantableItems.add(player.getInventory().getArmorContents()[1]);
        }

        if (CustomEnchantmentTarget.isTarget(player.getInventory().getArmorContents()[2])) {
            enchantableItems.add(player.getInventory().getArmorContents()[2]);
        }

        if (CustomEnchantmentTarget.isTarget(player.getInventory().getArmorContents()[3])) {
            enchantableItems.add(player.getInventory().getArmorContents()[3]);
        }

        return enchantableItems;
    }

    public static Map<CustomEnchantment, Integer> getEnchantments(ItemStack itemStack) {
        Map<CustomEnchantment, Integer> enchantmentsWithLevels = new HashMap<>();

        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return enchantmentsWithLevels;
        }

        List<String> lore = itemStack.getItemMeta().getLore();

        if (!lore.isEmpty()) {
            List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

            for (String line : lore) {
                for (CustomEnchantment enchantment : enchantments) {
                    if (line.contains(enchantment.getDisplayName())) {
                        int level = getLevel(enchantment, line);

                        if (level >= enchantment.getStartLevel()) {
                            enchantmentsWithLevels.put(enchantment, level);
                        }
                    }
                }
            }
        }

        return enchantmentsWithLevels;
    }

    public static int getLevel(ItemStack itemStack, CustomEnchantment enchantment) {
        return getLevel(itemStack, enchantment, false);
    }

    public static int getLevel(ItemStack itemStack, CustomEnchantment enchantment, boolean hasEnchantment) {
        if (!hasEnchantment && (!itemStack.getItemMeta().hasLore() || itemStack.getItemMeta().getLore().isEmpty())) {
            return 0;
        }

        List<String> lore = itemStack.getItemMeta().getLore();

        for (String line : lore) {
            if (!line.contains(enchantment.getDisplayName())) {
                continue;
            }

            String levelNumerals = ChatColor.stripColor(line.replace(enchantment.getDisplayName() + " ", ""));

            if (!NumberUtils.isRomanNumeral(levelNumerals)) {
                return 0;
            }

            return NumberUtils.convertFromRoman(levelNumerals);
        }

        return 0;
    }

    public static boolean hasEnchantment(ItemStack itemStack, CustomEnchantment enchantment) {
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }

        List<String> lore = itemStack.getItemMeta().getLore();

        if (!lore.isEmpty()) {
            for (String line : lore) {
                line = ChatColor.stripColor(line);

                if (line.contains(enchantment.getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int getLevel(CustomEnchantment enchantment, String loreLine) {
        String levelNumerals = ChatColor.stripColor(loreLine.replace(enchantment.getDisplayName() + " ", ""));

        if (!NumberUtils.isRomanNumeral(levelNumerals)) {
            return 0;
        }

        return NumberUtils.convertFromRoman(levelNumerals);
    }

}
