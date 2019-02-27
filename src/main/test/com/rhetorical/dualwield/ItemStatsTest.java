package com.rhetorical.dualwield;

import org.bukkit.Material;
import org.junit.Test;

public class ItemStatsTest {

	@Test
	public void testGetAttackDamage() {
		assert(ItemStats.getAttackDamage(Material.IRON_SWORD) == 6d);
		assert(ItemStats.getAttackDamage(Material.GOLDEN_AXE) == 7d);
		assert(ItemStats.getAttackDamage(Material.APPLE) == 0.5d);
		assert(!(ItemStats.getAttackDamage(Material.STONE_HOE) == 200d));
	}

	@Test
	public void testGetSwingDistance() {
		assert(ItemStats.getSwingDistance(Material.STONE_HOE) == 3);
		assert(ItemStats.getSwingDistance(Material.IRON_AXE) == 3);
		assert(ItemStats.getSwingDistance(Material.STONE) == 3);
		assert(ItemStats.getSwingDistance(Material.AIR) != 4);
	}


}