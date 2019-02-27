package com.rhetorical.dualwield;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class PlayerUtility {

	/**
	 * Gets the armor points for the given player.
	 *
	 * @param p The player to get the armor points for.
	 * @return The amount of armor points the player has.
	 * */
	static int getArmorPoints(Player p) {

		return (int) p.getAttribute(Attribute.GENERIC_ARMOR).getValue();
	}


	/**
	 * Gets the armor toughness for the given player.
	 *
	 * @param p The player to get the armor toughness for.
	 * @return The amount of armor toughness the player has.
	 * */
	static int getArmorToughness(Player p) {
		return (int) p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue();
	}

	/**
	 * Calculates the amount of damage that is done to the victim given the base damage and item stack in the hand.
	 * This takes into account both the victims armor as well as any enchantments on the weapon or on the armor.
	 *
	 * @param stack The ItemStack the player is currently holding.
	 * @param attacker The player that is the attacker.
	 * @param victim The victim of the attack. Can be any LivingEntity.
	 * @param baseDamage The calculated base damage of the attack.
	 * @return The amount of damage the attack should output.
	 * */
	static double getDamage(ItemStack stack, Player attacker, LivingEntity victim, double baseDamage) {

		/* Enchantment management */

		Map<Enchantment, Integer> enchantments = stack.getEnchantments();

		if (enchantments.containsKey(Enchantment.DAMAGE_ARTHROPODS)) {
			if (victim instanceof Spider || victim instanceof Silverfish || victim instanceof Endermite) {
				baseDamage += (2.5 * enchantments.get(Enchantment.DAMAGE_ARTHROPODS));
			}
		}

		if (enchantments.containsKey(Enchantment.FIRE_ASPECT)) {
			victim.setFireTicks(80 * enchantments.get(Enchantment.FIRE_ASPECT));
		}

		if (Main.postWaterUpdate) {
			if (enchantments.containsKey(Enchantment.IMPALING)) {
				if (victim instanceof Dolphin || victim instanceof Fish || victim instanceof Guardian || victim instanceof Squid || victim instanceof Turtle) {
					baseDamage += (2.5 * enchantments.get(Enchantment.IMPALING));
				}
			}
		}

		if (enchantments.containsKey(Enchantment.DAMAGE_ALL)) {
			baseDamage += 1 + (0.5 * enchantments.get(Enchantment.DAMAGE_ALL));
		}

		if (enchantments.containsKey(Enchantment.DAMAGE_UNDEAD)) {
			if (victim instanceof Skeleton || victim instanceof Zombie || victim instanceof Wither || victim instanceof SkeletonHorse) {
				baseDamage += (2.5 * enchantments.get(Enchantment.DAMAGE_UNDEAD));
			}


			if (Main.postWaterUpdate) {
				if (victim instanceof Phantom) {
					baseDamage += (2.5 * enchantments.get(Enchantment.DAMAGE_UNDEAD));
				}
			}

		}

		if (enchantments.containsKey(Enchantment.DURABILITY)) {
			double rand = Math.random() * 100;

			if ((100 / enchantments.get(Enchantment.DURABILITY) + 1) > rand) {
				stack.setDurability((short) (stack.getDurability() + (short) 1));
			}
		} else {
			stack.setDurability((short) (stack.getDurability() + (short) 1));
		}

		if (stack.getDurability() <= 0) {
			attacker.getInventory().remove(stack);
			attacker.playSound(attacker.getEyeLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
		}


		/* End enchantment management */

		if (!(victim instanceof Player)) {
			return baseDamage;
		}


		Player p = (Player) victim;

		if (PlayerUtility.getArmorPoints(p) == 0) {
			return baseDamage;
		}

		return baseDamage * (1 - Math.min(20, Math.max((float) PlayerUtility.getArmorPoints(p) / 5, PlayerUtility.getArmorPoints(p) - baseDamage / (float) (PlayerUtility.getArmorToughness(p) / 4 + 2))) / 25);
	}

	/**
	 * Gets a target entity within the player's line of sight.
	 *
	 * @param player The player to check their line of sight.
	 * @param distance The distance of how far out from the player to check.
	 * @return Any LivingEntity that the player is looking at, or null if nothing.
	 * */
	static LivingEntity getTargetEntity(Player player, int distance) {
		Collection<Entity> entities = player.getNearbyEntities(distance, 10, 30);
		ArrayList<Location> locations = new ArrayList<>();

		for (int i = distance; i >= 1; i--) {
			locations.add(player.getTargetBlock(null, i).getLocation());
		}

		for(Entity e : entities) {

			if (!(e instanceof LivingEntity))
				continue;

			LivingEntity entity = (LivingEntity) e;

			for(Location loc : locations) {
				float locX = (float) loc.getX();
				float locY = (float) loc.getY();
				float locZ = (float) loc.getZ();

				float entX = (float) entity.getLocation().getX();
				float entY = (float) entity.getLocation().getY();
				float entZ = (float) entity.getLocation().getZ();

				if(((locX-2f < entX)&&(entX < locX+2f))&&((locY-3f < entY)&&(entY < locY+3f))&&((locZ-2f < entZ)&&(entZ < locZ+2f))) {
					return entity;
				}
			}
		}
		return null;
	}
}