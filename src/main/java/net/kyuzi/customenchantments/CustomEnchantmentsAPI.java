package net.kyuzi.customenchantments;

import net.kyuzi.customenchantments.enchantment.CustomEnchantment;
import net.kyuzi.customenchantments.enchantment.CustomEnchantmentTarget;
import net.kyuzi.customenchantments.event.AddEnchantmentEvent;
import net.kyuzi.customenchantments.event.RemoveEnchantmentEvent;
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
     * @return True if the enchantment was added
     */
    public static boolean addEnchantment(ItemStack itemStack, CustomEnchantment enchantment, long level) {
        if (!canEnchantItem(itemStack, enchantment, level)) {
            return false;
        }

        AddEnchantmentEvent event = new AddEnchantmentEvent(enchantment, itemStack, level);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return false;
        }

        // Update if the enchantment or level has been changed
        enchantment = event.getCustomEnchantment();
        level = event.getLevel();

        Map<CustomEnchantment, Long> itemEnchantments = CustomEnchantmentsAPI.getEnchantments(itemStack);

        if (!itemEnchantments.isEmpty()) {
            for (Map.Entry<CustomEnchantment, Long> itemEnchantment : itemEnchantments.entrySet()) {
                if (itemEnchantment.getKey().equals(enchantment)) {
                    if (itemEnchantment.getValue() == level) {
                        return false;
                    }

                    removeEnchantment(itemStack, enchantment, false);
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

    /**
     * <p>
     * Checks if an item can be enchanted with a custom enchantment at certain level.
     * </p>
     *
     * @param itemStack   The item being enchanted
     * @param enchantment The enchantment being applied
     * @param level       The level of the enchantment
     * @return True if the item can be enchanted
     */
    public static boolean canEnchantItem(ItemStack itemStack, CustomEnchantment enchantment, long level) {
        return enchantment.canEnchantItem(itemStack) && level >= enchantment.getStartLevel() && level <= enchantment.getMaxLevel();
    }

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

    /**
     * <p>
     * Gets an enchantment from its display name (the string shown on the lore).
     * </p>
     *
     * @param displayName The enchantment's display name
     * @return The enchantment which has the passed display name
     */
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

    /**
     * <p>
     * Gets an enchantment from its unique name.
     * </p>
     *
     * @param name The enchantment's unique name
     * @return The enchantment which has the passed unique name
     */
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

    /**
     * <p>
     * Gets all custom enchantments that are on an item.
     * </p>
     *
     * @param itemStack The item with potential custom enchantments
     * @return A map of all custom enchantments found on the item passed with their levels
     */
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

    /**
     * <p>
     * Gets the level of a custom enchantment which is on an item.
     * </p>
     *
     * @param itemStack   The item with the potential custom enchantment
     * @param enchantment The custom enchantment which the level is being found for
     * @return The passed custom enchantment's level or -1 if no the item does not have the custom enchantment
     */
    public static long getLevel(ItemStack itemStack, CustomEnchantment enchantment) {
        if (!hasEnchantment(itemStack, enchantment)) {
            return -1;
        }

        List<String> lore = itemStack.getItemMeta().getLore();

        for (String line : lore) {
            if (!line.contains(enchantment.getDisplayName())) {
                continue;
            }

            String level = ChatColor.stripColor(line.replace(enchantment.getDisplayName() + " ", ""));

            if ((!enchantment.canUseNumerals() && !NumberUtils.isLong(level)) || (enchantment.canUseNumerals() && !NumberUtils.isRomanNumeral(level))) {
                return -1;
            }

            return enchantment.canUseNumerals() ? NumberUtils.convertFromRoman(level) : Long.parseLong(level);
        }

        return -1;
    }

    /**
     * <p>
     * Checks if an item holds a custom enchantment.
     * </p>
     *
     * @param itemStack   The item with the potential custom enchantment
     * @param enchantment The enchantment which the item may have
     * @return True if the item holds the custom enchantment
     */
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

    /**
     * <p>
     * Sorts the item's enchantments in regards to their rank on the item's lore.
     * </p>
     *
     * @param itemStack The item to be sorted
     */
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
    }

    /**
     * <p>
     * Removes a custom enchantment from an item.
     * </p>
     *
     * @param itemStack   The item that the enchantment should be removed from
     * @param enchantment The enchantment to be removed
     * @return True if the enchantment was removed
     */
    public static boolean removeEnchantment(ItemStack itemStack, CustomEnchantment enchantment) {
        return removeEnchantment(itemStack, enchantment, true);
    }

    /* Utility methods */

    private static boolean containsEnchantment(String loreLine) {
        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (!enchantments.isEmpty()) {
            for (CustomEnchantment enchantment : enchantments) {
                if (loreLine.contains(enchantment.getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static long getLevel(CustomEnchantment enchantment, String loreLine) {
        String level = ChatColor.stripColor(loreLine.replace(enchantment.getDisplayName() + " ", ""));

        if ((!enchantment.canUseNumerals() && !NumberUtils.isLong(level)) || (enchantment.canUseNumerals() && !NumberUtils.isRomanNumeral(level))) {
            return -1;
        }

        return enchantment.canUseNumerals() ? NumberUtils.convertFromRoman(level) : Long.parseLong(level);
    }

    private static boolean removeEnchantment(ItemStack itemStack, CustomEnchantment enchantment, boolean callEvent) {
        if (!CustomEnchantmentsAPI.hasEnchantment(itemStack, enchantment)) {
            return false;
        }

        if (callEvent) {
            RemoveEnchantmentEvent event = new RemoveEnchantmentEvent(enchantment, itemStack);
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }
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

}
