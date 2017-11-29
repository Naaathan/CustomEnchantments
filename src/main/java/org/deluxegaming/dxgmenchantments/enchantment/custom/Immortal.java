package org.deluxegaming.dxgmenchantments.enchantment.custom;

import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantmentTarget;

public class Immortal extends DXGMEnchantment {

    public Immortal(String displayName) {
        super("immortal", displayName, 1, 3);
    }

    @Override
    public DXGMEnchantmentTarget getItemTarget() {
        return DXGMEnchantmentTarget.ARMOUR;
    }

}
