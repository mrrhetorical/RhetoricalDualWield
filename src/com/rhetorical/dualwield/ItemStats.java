package com.rhetorical.dualwield;

import org.bukkit.Material;

class ItemStats {

    static double getAttackDamage(Material material) {
        switch(material) {
            case IRON_SWORD:
                return 6d;
            case IRON_SHOVEL:
                return 4.5d;
            case IRON_PICKAXE:
                return 4d;
            case IRON_AXE:
                return 9d;
            case IRON_HOE:
                return 1d;
            case WOODEN_SWORD:
                return 4d;
            case WOODEN_SHOVEL:
                return 2.5d;
            case WOODEN_PICKAXE:
                return 2d;
            case WOODEN_AXE:
                return 7d;
            case WOODEN_HOE:
                return 1d;
            case STONE_SWORD:
                return 5d;
            case STONE_SHOVEL:
                return 3.5d;
            case STONE_PICKAXE:
                return 3d;
            case STONE_AXE:
                return 9d;
            case STONE_HOE:
                return 1d;
            case GOLDEN_SWORD:
                return 4d;
            case GOLDEN_SHOVEL:
                return 2.5d;
            case GOLDEN_PICKAXE:
                return 2d;
            case GOLDEN_AXE:
                return 7d;
            case GOLDEN_HOE:
                return 1d;
            case DIAMOND_SWORD:
                return 7d;
            case DIAMOND_SHOVEL:
                return 5.5d;
            case DIAMOND_PICKAXE:
                return 5d;
            case DIAMOND_AXE:
                return 9d;
            case DIAMOND_HOE:
                return 1d;
            default:
                return 0.5d;
        }
    }

    static int getSwingDistance(Material material) {
        return 5;
    }
}