package org.deluxegaming.customenchantments.enchantment;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class CustomEnchantment {

    private String name;
    private String displayName;
    private int tier;
    private int startLevel;
    private int maxLevel;

    public CustomEnchantment(String name, String displayName, int tier, int startLevel, int maxLevel) {
        this.name = name;
        this.displayName = displayName;
        this.tier = tier;
        this.startLevel = startLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    public final boolean equals(Object obj) {
        CustomEnchantment enchantment;

        if (obj instanceof CustomEnchantment) {
            enchantment = (CustomEnchantment) obj;
        } else {
            return false;
        }

        return enchantment.getName().equals(name);
    }

    public final String getName() {
        return name;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public final int getTier() {
        return tier;
    }

    public final int getStartLevel() {
        return startLevel;
    }

    public final int getMaxLevel() {
        return maxLevel;
    }

    public final boolean canEnchantItem(ItemStack itemStack) {
        CustomEnchantmentTarget itemTarget = getItemTarget();

        if (itemTarget == null || itemStack == null) {
            return false;
        }

        return itemTarget.includes(itemStack);
    }

    public abstract CustomEnchantmentTarget getItemTarget();

    public void onBlockBreak(BlockBreakEvent e, Player player, int level) {
    }

    public void onEntityDamage(EntityDamageEvent e, Player damaged, int level) {
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, Entity damaged, Entity damager, int level) {
    }

    public void onPlayerInteract(PlayerInteractEvent e, Player player, int level) {
    }

    public void onProjectileHit(ProjectileHitEvent e, Player shooter, Projectile projectile, int level) {
    }

    public void onProjectileLaunch(ProjectileLaunchEvent e, Player shooter, Projectile projectile, int level) {
    }

}
