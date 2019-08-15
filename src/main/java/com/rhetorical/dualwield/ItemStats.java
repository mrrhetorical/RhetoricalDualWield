package com.rhetorical.dualwield;

import org.bukkit.Material;

class ItemStats {

    enum CombatItem {
        WOODEN_SWORD(4d, 3), STONE_SWORD(5d, 3), GOLD_SWORD(4d, 3), IRON_SWORD(6d, 3), DIAMOND_SWORD(7d, 3);

        private double damage;
        int distance;

        CombatItem (double dmg, int dis) {
            damage = dmg;
            distance = dis;
        }

        public double getDamage() {
            return damage;
        }

        public int getDistance() {
            return distance;
        }
    }

    /** Hacky temporary solution. I plan to fix this up with NBT tags with reflection, but likely will take longer to handle. Upside of using reflection is that it'll work across versions.
     * Downside to using reflection is it'll be slower, which for attacking you don't want. If I store the values in a map or in a final enum, it'll be a little more ram that's used, but I'll have to check on it's impact.
	 * @param material The material to get the attack damage for.
	 * @return The attack damage for the given item.
	 * */
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

    /** I intend to use the unused 1.14 combat change where the swing distance for different items was changed.
	 * @param material The material to get the swing distance for.
	 * @return The swing distance (in blocks) that the given material can reach.
	 * */
    static int getSwingDistance(Material material) {
        //Returns 3 by default, no need for changes yet.
        return 3;
    }
}