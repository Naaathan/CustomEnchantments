package net.kyuzi.customenchantments;

import net.kyuzi.customenchantments.enchantment.CustomEnchantment;
import net.kyuzi.customenchantments.enchantment.CustomEnchantmentTarget;
import net.kyuzi.customenchantments.utility.NumberUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CustomEnchantmentsAPI {

    /**
     * <p>
     * Adds a custom enchantment with a specific level to the item.
     * </p>
     *
     * @param itemStack   The item to apply the enchantment to
     * @param enchantment The enchantment to be applied
     * @param level       The level of the enchantment
     * @return The item with the new enchantment
     */
    public static boolean addEnchantment(ItemStack itemStack, CustomEnchantment enchantment, long level) {
        if (!canEnchantItem(itemStack, enchantment, level)) {
            return false;
        }

        Map<CustomEnchantment, Long> itemEnchantments = CustomEnchantmentsAPI.getEnchantments(itemStack);

        if (!itemEnchantments.isEmpty()) {
            for (Map.Entry<CustomEnchantment, Long> itemEnchantment : itemEnchantments.entrySet()) {
                if (itemEnchantment.getKey().equals(enchantment)) {
                    if (itemEnchantment.getValue() == level) {
                        return false;
                    }

                    removeEnchantment(itemStack, enchantment);
                    break;
                }
            }
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = (itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>());

        lore.add(0, enchantment.getDisplayName() + " " + (enchantment.canUseNumerals() ? NumberUtils.convertToRoman(level) : level));

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        orderEnchantments(itemStack);

        return true;
    }

    public static boolean canEnchantItem(ItemStack itemStack, CustomEnchantment enchantment, long level) {
        return enchantment.canEnchantItem(itemStack) && level >= enchantment.getStartLevel() && level <= enchantment.getMaxLevel();
    }

    /**
     * <p>
     * Removes a custom enchantment from an item.
     * </p>
     *
     * @param itemStack   The item that the enchantment should be removed from
     * @param enchantment The enchantment to be removed
     * @return The item without the enchantment
     */
    public static boolean removeEnchantment(ItemStack itemStack, CustomEnchantment enchantment) {
        if (!CustomEnchantmentsAPI.hasEnchantment(itemStack, enchantment)) {
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

    /*
    Enchantment utilities
     */

    /**
     * <p>
     * Gets all items which effects could be applied at the current time from a player.
     * </p>
     *
     * @param player The player which has the items
     * @return A list of items that a player
     */
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

    public static CustomEnchantment getEnchantmentByDisplayName(String displayName) {
        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (enchantment.getDisplayName().equals(displayName)) {
                    return enchantment;
                }
            }
        }

        return null;
    }

    public static CustomEnchantment getEnchantmentByName(String name) {
        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (enchantment.getName().equalsIgnoreCase(name)) {
                    return enchantment;
                }
            }
        }

        return null;
    }

    public static Map<CustomEnchantment, Long> getEnchantments(ItemStack itemStack) {
        Map<CustomEnchantment, Long> enchantmentsWithLevels = new HashMap<>();

        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return enchantmentsWithLevels;
        }

        List<String> lore = itemStack.getItemMeta().getLore();

        if (!lore.isEmpty()) {
            List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

            for (String line : lore) {
                for (CustomEnchantment enchantment : enchantments) {
                    if (line.contains(enchantment.getDisplayName())) {
                        long level = getLevel(enchantment, line);

                        if (level >= enchantment.getStartLevel()) {
                            enchantmentsWithLevels.put(enchantment, level);
                        }
                    }
                }
            }
        }

        return enchantmentsWithLevels;
    }

    public static long getLevel(ItemStack itemStack, CustomEnchantment enchantment) {
        return getLevel(itemStack, enchantment, false);
    }

    public static long getLevel(ItemStack itemStack, CustomEnchantment enchantment, boolean hasEnchantment) {
        if (!hasEnchantment && (!itemStack.getItemMeta().hasLore() || itemStack.getItemMeta().getLore().isEmpty())) {
            return 0;
        }

        List<String> lore = itemStack.getItemMeta().getLore();

        for (String line : lore) {
            if (!line.contains(enchantment.getDisplayName())) {
                continue;
            }

            String level = ChatColor.stripColor(line.replace(enchantment.getDisplayName() + " ", ""));

            if ((!enchantment.canUseNumerals() && !NumberUtils.isLong(level)) || (enchantment.canUseNumerals() && !NumberUtils.isRomanNumeral(level))) {
                return 0;
            }

            return enchantment.canUseNumerals() ? NumberUtils.convertFromRoman(level) : Long.parseLong(level);
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
                if (line.contains(enchantment.getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void orderEnchantments(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }

        Map<CustomEnchantment, Long> enchantmentsWithLevels = getEnchantments(itemStack);

        if (enchantmentsWithLevels.isEmpty()) {
            return;
        }

        List<CustomEnchantment> enchantments = new ArrayList<>(Arrays.asList(enchantmentsWithLevels.keySet().toArray(new CustomEnchantment[enchantmentsWithLevels.size()])));
        List<String> lore = new ArrayList<>(itemStack.getItemMeta().getLore());

        if (!enchantments.isEmpty()) {
            int i;

            enchantments.sort((enchantment1, enchantment2) -> {
                if (enchantment1.getRank() < enchantment2.getRank()) {
                    return -1;
                } else if (enchantment1.getRank() > enchantment2.getRank()) {
                    return 1;
                }

                return 0;
            });

            for (i = 0; i < lore.size(); i++) {
                if (containsEnchantment(lore.get(i))) {
                    lore.remove(i);
                    i--;
                }
            }

            i = 0;

            for (CustomEnchantment enchantment : enchantments) {
                lore.add(i, enchantment.getDisplayName() + " " + (enchantment.canUseNumerals() ? NumberUtils.convertToRoman(enchantmentsWithLevels.get(enchantment)) : enchantmentsWithLevels.get(enchantment)));
                i++;
            }
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return;
    }

    private static boolean containsEnchantment(String line) {
        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (line.contains(enchantment.getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static long getLevel(CustomEnchantment enchantment, String loreLine) {
        String level = ChatColor.stripColor(loreLine.replace(enchantment.getDisplayName() + " ", ""));

        if ((!enchantment.canUseNumerals() && !NumberUtils.isLong(level)) || (enchantment.canUseNumerals() && !NumberUtils.isRomanNumeral(level))) {
            return 0;
        }

        return enchantment.canUseNumerals() ? NumberUtils.convertFromRoman(level) : Long.parseLong(level);
    }

}
