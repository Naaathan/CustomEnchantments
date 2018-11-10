package net.kyuzi.customenchantments.enchantment;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CustomEnchantmentTarget {

    ALL {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null;
        }
    },
    ARMOUR {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && (itemStack.getType().name().contains("HELMET") || itemStack.getType().name().contains("CHESTPLATE") || itemStack.getType().name().contains("LEGGINGS") || itemStack.getType().name().contains("BOOTS"));
        }
    },
    ARMOUR_HELMET {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType().name().contains("HELMET");
        }
    },
    ARMOUR_CHESTPLATE {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType().name().contains("CHESTPLATE");
        }
    },
    ARMOUR_LEGGINGS {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType().name().contains("LEGGINGS");
        }
    },
    ARMOUR_BOOTS {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType().name().contains("BOOTS");
        }
    },
    BOW {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType() == Material.BOW;
        }
    },
    PICKAXE {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType().name().contains("PICKAXE");
        }
    },
    TOOL {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && (itemStack.getType().name().contains("AXE") || itemStack.getType().name().contains("HOE") || itemStack.getType().name().contains("PICKAXE") || itemStack.getType().name().contains("SPADE"));
        }
    },
    WEAPON {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && itemStack.getType().name().contains("SWORD");
        }
    },
    WEAPON_WITH_AXE {
        public boolean includes(ItemStack itemStack) {
            return itemStack != null && (itemStack.getType().name().contains("AXE") || itemStack.getType().name().contains("SWORD"));
        }
    };

    public abstract boolean includes(ItemStack itemStack);

}
