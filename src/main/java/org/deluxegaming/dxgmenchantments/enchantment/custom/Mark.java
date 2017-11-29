package org.deluxegaming.dxgmenchantments.enchantment.custom;

import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantmentTarget;

public class Mark extends DXGMEnchantment {

    public Mark(String displayName) {
        super("mark", displayName, 1, 5);
    }

    @Override
    public DXGMEnchantmentTarget getItemTarget() {
        return DXGMEnchantmentTarget.WEAPON;
    }

}
