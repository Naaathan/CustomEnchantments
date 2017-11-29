package org.deluxegaming.dxgmenchantments.enchantment.custom;

import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantment;
import org.deluxegaming.dxgmenchantments.enchantment.DXGMEnchantmentTarget;

public class ObsidianDestroyer extends DXGMEnchantment {

    public ObsidianDestroyer(String displayName) {
        super("obsidian_destroyer", displayName, 1, 1);
    }

    @Override
    public DXGMEnchantmentTarget getItemTarget() {
        return DXGMEnchantmentTarget.PICKAXE;
    }

}
