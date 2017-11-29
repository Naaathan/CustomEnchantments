package org.deluxegaming.dxgmenchantments.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.projectiles.ProjectileSource;
import org.deluxegaming.dxgmenchantments.DXGMEnchantments;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.utility.EnchantmentUtils;

import java.util.*;

public class EnchantmentListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        List<DXGMEnchantment> enchantments = DXGMEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = e.getPlayer();
        List<ItemStack> items = new ArrayList<>(Arrays.asList(player.getItemInHand()));
        Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<DXGMEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<DXGMEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                DXGMEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onBlockBreak(e, player, level);
                break;
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        List<DXGMEnchantment> enchantments = DXGMEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = (Player) e.getEntity();
        List<ItemStack> items = EnchantmentUtils.getEnchantableItems(player);

        items.remove(player.getItemInHand());

        Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<DXGMEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<DXGMEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                DXGMEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onEntityDamage(e, player, level);
                break;
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        List<DXGMEnchantment> enchantments = DXGMEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            List<ItemStack> items = EnchantmentUtils.getEnchantableItems(player);

            items.remove(player.getItemInHand());

            Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

            if (itemsWithEnchantments.isEmpty()) {
                return;
            }

            for (Map<DXGMEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
                for (Map.Entry<DXGMEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                    DXGMEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                    int level = itemEnchantmentWithLevel.getValue();

                    enchantment.onEntityDamageByEntity(e, player, e.getDamager(), level);
                    break;
                }
            }
        }

        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            List<ItemStack> items = EnchantmentUtils.getEnchantableItems(player);
            Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

            if (itemsWithEnchantments.isEmpty()) {
                return;
            }

            for (Map<DXGMEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
                for (Map.Entry<DXGMEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                    DXGMEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                    int level = itemEnchantmentWithLevel.getValue();

                    enchantment.onEntityDamageByEntity(e, e.getEntity(), player, level);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        ProjectileSource shooter = projectile.getShooter();

        if (!(shooter instanceof Player)) {
            return;
        }

        List<DXGMEnchantment> enchantments = DXGMEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = (Player) shooter;
        List<ItemStack> items = EnchantmentUtils.getEnchantableItems(player);
        Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<DXGMEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<DXGMEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                DXGMEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onProjectileHit(e, player, projectile, level);
                break;
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        ProjectileSource shooter = projectile.getShooter();

        if (!(shooter instanceof Player)) {
            return;
        }

        List<DXGMEnchantment> enchantments = DXGMEnchantments.getInstance().getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        Player player = (Player) shooter;
        List<ItemStack> items = EnchantmentUtils.getEnchantableItems(player);
        Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = getItemsWithEnchantments(items);

        if (itemsWithEnchantments.isEmpty()) {
            return;
        }

        for (Map<DXGMEnchantment, Integer> itemEnchantmentsWithLevels : itemsWithEnchantments.values()) {
            for (Map.Entry<DXGMEnchantment, Integer> itemEnchantmentWithLevel : itemEnchantmentsWithLevels.entrySet()) {
                DXGMEnchantment enchantment = itemEnchantmentWithLevel.getKey();
                int level = itemEnchantmentWithLevel.getValue();

                enchantment.onProjectileLaunch(e, player, projectile, level);
                break;
            }
        }
    }

    private Map<ItemStack, Map<DXGMEnchantment, Integer>> getItemsWithEnchantments(List<ItemStack> items) {
        Map<ItemStack, Map<DXGMEnchantment, Integer>> itemsWithEnchantments = new HashMap<>();

        if (items.isEmpty()) {
            return itemsWithEnchantments;
        }

        for (ItemStack item : items) {
            Map<DXGMEnchantment, Integer> itemEnchantments = EnchantmentUtils.getEnchantments(item);

            if (!itemEnchantments.isEmpty()) {
                itemsWithEnchantments.put(item, itemEnchantments);
            }
        }

        return itemsWithEnchantments;
    }

}
