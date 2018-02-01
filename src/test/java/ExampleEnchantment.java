import net.kyuzi.customenchantments.enchantment.CustomEnchantment;
import net.kyuzi.customenchantments.enchantment.CustomEnchantmentTarget;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * ExampleEnchantment custom enchantment which makes a player take 1 less heart of damage each time they are attacked by another entity.
 *
 * @see net.kyuzi.customenchantments.enchantment.CustomEnchantment
 * @see net.kyuzi.customenchantments.enchantment.CustomEnchantmentTarget
 */
public class ExampleEnchantment extends CustomEnchantment {

    public ExampleEnchantment() {
        super("Example", ChatColor.AQUA + "Absorption", 1, 1, 2);
    }

    @Override
    public CustomEnchantmentTarget getItemTarget() {
        return CustomEnchantmentTarget.ARMOUR;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, Entity damaged, Entity damager, long level) {
        double damage = e.getFinalDamage();

        if (level == 1) {
            damage -= 2;
        } else if (level == 2) {
            damage -= 4;
        }

        damage = damage < 0 ? 0 : damage;

        e.setDamage(damage);
    }

}
