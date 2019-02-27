package com.rhetorical.dualwield;

import org.bukkit.Material;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class MainTest {

	@Test
	public void getCraftPlayerTest() {
		assert(Main.getCraftPlayer() != null);
	}

	@Test
	public void getMaterialsTest() {
		List<String> names = new ArrayList<>();
		names.add("Stone");
		names.add("IRON_HOE");
		names.add("IRON_SWORD");
		names.add("DIAMOND_SHOVEL");
		names.add("COOKED_BEEF");

		Method method;

		try {
			method = Main.class.getDeclaredMethod("getMaterials", List.class);
		} catch(Exception e) {
			e.printStackTrace();
			fail();
			return;
		}

		method.setAccessible(true);

		List<Material> ret;

		try {
			//noinspection unchecked
			ret = (List<Material>) method.invoke(new Main(), names);
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error when invoking getMaterials(...)");
			return;
		}


		assert(ret.contains(Material.IRON_HOE));
		assert(ret.contains(Material.IRON_SWORD));
		assert(ret.contains(Material.DIAMOND_SHOVEL));
		assert(ret.contains(Material.COOKED_BEEF));
		assert(!ret.contains(Material.STONE));
	}

	@Test
	public void getNMSVersionTest() {
		assert(Main.getNMSVersion("Packet") != null);
	}
}