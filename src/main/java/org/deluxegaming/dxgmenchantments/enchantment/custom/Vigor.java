package org.deluxegaming.dxgmenchantments.enchantment.custom;

import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantmentTarget;

public class Vigor extends DXGMEnchantment {

    public Vigor(String displayName) {
        super("vigor", displayName, 1, 2);
    }

    @Override
    public DXGMEnchantmentTarget getItemTarget() {
        return DXGMEnchantmentTarget.ARMOUR;
    }

}
