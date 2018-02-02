package net.kyuzi.customenchantments.event;

import net.kyuzi.customenchantments.enchantment.CustomEnchantment;

import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class AddEnchantmentEvent extends CustomEnchantmentsEvent implements Cancellable {

    private boolean cancelled;
    private long level;

    public AddEnchantmentEvent(CustomEnchantment customEnchantment, ItemStack itemStack, long level) {
        super(customEnchantment, itemStack);
        this.cancelled = false;
        this.level = level;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setCustomEnchantment(CustomEnchantment customEnchantment) {
        if (customEnchantment != null) {
            this.customEnchantment = customEnchantment;
        }
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

}
