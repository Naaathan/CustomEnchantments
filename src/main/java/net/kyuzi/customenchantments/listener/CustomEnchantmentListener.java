package net.kyuzi.customenchantments.listener;

import net.kyuzi.customenchantments.CustomEnchantments;
import net.kyuzi.customenchantments.CustomEnchantmentsAPI;
import net.kyuzi.customenchantments.enchantment.CustomEnchantment;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.*;

public class CustomEnchantmentListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }

        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = e.getPlayer();
        List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);
        Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onBlockBreak(e, player, level);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = (Player) e.getEntity();
        List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);

        items.remove(player.getItemInHand());

        Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onEntityDamage(e, player, level);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }

        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);

            items.remove(player.getItemInHand());

            Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

            if (itemsWithEnchantments.isEmpty()) {
                return;
            }

            for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
                for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                    CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                    int level = itemEnchantmentWithLevel.getValue();

                    enchantment.onEntityDamageByEntity(e, player, e.getDamager(), level);
                }
            }
        }

        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);
            Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

            if (itemsWithEnchantments.isEmpty()) {
                return;
            }

            for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
                for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                    CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                    int level = itemEnchantmentWithLevel.getValue();

                    enchantment.onEntityDamageByEntity(e, e.getEntity(), player, level);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }

        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = e.getPlayer();
        List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);
        Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onPlayerInteract(e, player, level);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        ProjectileSource shooter = projectile.getShooter();

        if (!(shooter instanceof Player)) {
            return;
        }

        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = (Player) shooter;
        List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);
        Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onProjectileHit(e, player, projectile, level);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Projectile projectile = e.getEntity();
        ProjectileSource shooter = projectile.getShooter();

        if (!(shooter instanceof Player)) {
            return;
        }

        List<CustomEnchantment> enchantments = CustomEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = (Player) shooter;
        List<ItemStack> items = CustomEnchantmentsAPI.getEnchantableItems(player);
        Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<CustomEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<CustomEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                CustomEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onProjectileLaunch(e, player, projectile, level);
            }
        }
    }

    private Map<ItemStack, Map<CustomEnchantment, Integer>> getItemsWithEnchantments(List<ItemStack> items) {
        Map<ItemStack, Map<CustomEnchantment, Integer>> itemsWithEnchantments = new HashMap<>();

        if (items.isEmpty()) {
            return itemsWithEnchantments;
        }

        for (ItemStack item : items) {
            Map<CustomEnchantment, Integer> itemEnchantments = CustomEnchantmentsAPI.getEnchantments(item);

            if (!itemEnchantments.isEmpty()) {
                itemsWithEnchantments.put(item, itemEnchantments);
            }
        }

        return itemsWithEnchantments;
    }

}
