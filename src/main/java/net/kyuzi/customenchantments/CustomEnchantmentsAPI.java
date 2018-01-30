package net.kyuzi.customenchantments;

import net.kyuzi.customenchantments.enchantment.CustomEnchantment;
import net.kyuzi.customenchantments.enchantment.CustomEnchantmentTarget;
import net.kyuzi.customenchantments.utility.NumberUtils;

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
     * @return True if the process was successful
     */
    public static boolean addEnchantment(ItemStack itemStack, CustomEnchantment enchantment, int level) {
        if (!enchantment.canEnchantItem(itemStack) && level >= enchantment.getStartLevel()) {
            return false;
        }

        Map<CustomEnchantment, Integer> itemEnchantments = CustomEnchantmentsAPI.getEnchantments(itemStack);

        if (!itemEnchantments.isEmpty()) {
            for (CustomEnchantment itemEnchantment : itemEnchantments.keySet()) {
                if (itemEnchantment.equals(enchantment)) {
                    return false;
                }
            }
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = (itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>());

        lore.add(0, enchantment.getDisplayName() + " " + NumberUtils.convertToRoman(level));

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return true;
    }

    /**
     * <p>
     * Removes a custom enchantment from an item.
     * </p>
     *
     * @param itemStack   The item that the enchantment should be removed from
     * @param enchantment The enchantment to be removed
     * @return True if the process was successful
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

    public static void orderEnchantments(ItemStack itemStack, Comparator<CustomEnchantment> comparator) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }

        Map<CustomEnchantment, Integer> enchantmentsWithLevels = getEnchantments(itemStack);

        if (enchantmentsWithLevels.isEmpty()) {
            return;
        }

        List<CustomEnchantment> enchantments = new ArrayList<>(Arrays.asList(enchantmentsWithLevels.keySet().toArray(new CustomEnchantment[enchantmentsWithLevels.size()])));
        List<String> lore = new ArrayList<>(itemStack.getItemMeta().getLore());

        if (!enchantments.isEmpty()) {
            int i;

            enchantments.sort(comparator);

            for (i = 0; i < lore.size(); i++) {
                String line = ChatColor.stripColor(lore.get(i));

                if (containsEnchantment(line)) {
                    lore.remove(i);
                    i--;
                }
            }

            i = 0;

            for (CustomEnchantment enchantment : enchantments) {
                lore.add(i, enchantment.getDisplayName() + " " + NumberUtils.convertToRoman(enchantmentsWithLevels.get(enchantment)));
                i++;
            }
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
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

    private static int getLevel(CustomEnchantment enchantment, String loreLine) {
        String levelNumerals = ChatColor.stripColor(loreLine.replace(enchantment.getDisplayName() + " ", ""));

        if (!NumberUtils.isRomanNumeral(levelNumerals)) {
            return 0;
        }

        return NumberUtils.convertFromRoman(levelNumerals);
    }

}
