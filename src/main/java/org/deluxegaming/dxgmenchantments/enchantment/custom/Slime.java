package org.deluxegaming.dxgmenchantments.enchantment.custom;

import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantmentTarget;

public class Slime extends DXGMEnchantment {

    public Slime(String displayName) {
        super("slime", displayName, 1, 3);
    }

    @Override
    public DXGMEnchantmentTarget getItemTarget() {
        return DXGMEnchantmentTarget.WEAPON;
    }

}
