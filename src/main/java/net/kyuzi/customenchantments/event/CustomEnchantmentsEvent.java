package net.kyuzi.customenchantments.event;

import net.kyuzi.customenchantments.enchantment.CustomEnchantment;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract event for custom enchantments
 */
public class CustomEnchantmentsEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    protected CustomEnchantment customEnchantment;
    protected ItemStack itemStack;

    public CustomEnchantmentsEvent(CustomEnchantment customEnchantment, ItemStack itemStack) {
        this.customEnchantment = customEnchantment;
        this.itemStack = itemStack;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CustomEnchantment getCustomEnchantment() {
        return customEnchantment;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
