package org.deluxegaming.dxgmenchantments.enchantment.custom;

import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantmentTarget;

public class AntiBlast extends DXGMEnchantment {

    public AntiBlast(String displayName) {
        super("anti_blast", displayName, 1, 3);
    }

    @Override
    public DXGMEnchantmentTarget getItemTarget() {
        return DXGMEnchantmentTarget.ARMOUR;
    }

}
