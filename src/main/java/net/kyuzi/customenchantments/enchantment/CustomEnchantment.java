package net.kyuzi.customenchantments.enchantment;

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

    private boolean enabled;
    private String name;
    private String displayName;
    private int rank;
    private long startLevel;
    private long maxLevel;
    private boolean useNumerals;

    public CustomEnchantment(String name, String displayName, int rank, long startLevel, long maxLevel) {
        this(name, displayName, rank, startLevel, maxLevel, true);
    }

    public CustomEnchantment(String name, String displayName, int rank, long startLevel, long maxLevel, boolean useNumerals) {
        this.enabled = true;
        this.name = name;
        this.displayName = displayName;
        this.rank = rank;
        this.startLevel = startLevel;
        this.maxLevel = maxLevel;
        this.useNumerals = useNumerals;
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

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final String getName() {
        return name;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public final int getRank() {
        return rank;
    }

    public final long getStartLevel() {
        return startLevel;
    }

    public final long getMaxLevel() {
        return maxLevel;
    }

    public final boolean canUseNumerals() {
        return useNumerals;
    }

    public final boolean canEnchantItem(ItemStack itemStack) {
        CustomEnchantmentTarget itemTarget = getItemTarget();

        if (itemTarget == null || itemStack == null) {
            return false;
        }

        return itemTarget.includes(itemStack);
    }

    public abstract CustomEnchantmentTarget getItemTarget();

    public void onBlockBreak(BlockBreakEvent e, Player player, long level) {
    }

    public void onEntityDamage(EntityDamageEvent e, Player damaged, long level) {
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, Entity damaged, Entity damager, long level) {
    }

    public void onPlayerInteract(PlayerInteractEvent e, Player player, long level) {
    }

    public void onProjectileHit(ProjectileHitEvent e, Player shooter, Projectile projectile, long level) {
    }

    public void onProjectileLaunch(ProjectileLaunchEvent e, Player shooter, Projectile projectile, long level) {
    }

}
