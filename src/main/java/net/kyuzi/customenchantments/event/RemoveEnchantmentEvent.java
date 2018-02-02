package net.kyuzi.customenchantments.event;

import net.kyuzi.customenchantments.enchantment.CustomEnchantment;

import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class RemoveEnchantmentEvent extends CustomEnchantmentsEvent implements Cancellable {

    private boolean cancelled;

    public RemoveEnchantmentEvent(CustomEnchantment customEnchantment, ItemStack itemStack) {
        super(customEnchantment, itemStack);
        this.cancelled = false;
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

}
