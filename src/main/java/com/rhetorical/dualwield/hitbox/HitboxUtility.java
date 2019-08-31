package com.rhetorical.dualwield.hitbox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

public class HitboxUtility {

	private static HitboxUtility instance;

	private Map<Class <? extends LivingEntity>, Hitbox> largeHitboxes = new HashMap<>();
	private Map<Class<? extends LivingEntity>, Hitbox> hitboxes = new HashMap<>();
	private Map<Class <? extends LivingEntity>, Hitbox> babyHitboxes = new HashMap<>();

	private HitboxUtility() {
		if (instance != null)
			return;

		instance = this;

		setup();
	}

	public static HitboxUtility getInstance() {
		return instance != null ? instance : new HitboxUtility();
	}

	private void setup() {

		/* Baby Mobs */

		babyHitboxes.put(Rabbit.class, new Hitbox(Rabbit.class, 0.2f, 0.2f));
		babyHitboxes.put(Chicken.class, new Hitbox(Chicken.class, 0.2f, 0.3f));
		babyHitboxes.put(Ocelot.class, new Hitbox(Ocelot.class, 0.3f, 0.3f));
		babyHitboxes.put(Cat.class, new Hitbox(Cat.class, 0.3f, 0.3f));
		babyHitboxes.put(Wolf.class, new Hitbox(Wolf.class, 0.3f, 0.4f));
		babyHitboxes.put(Pig.class, new Hitbox(Pig.class, 0.4f, 0.4f));
		babyHitboxes.put(Sheep.class, new Hitbox(Sheep.class, 0.4f, 0.65f));
		babyHitboxes.put(Cow.class, new Hitbox(Cow.class, 0.4f, 0.7f));
		babyHitboxes.put(PolarBear.class, new Hitbox(PolarBear.class, 0.6f, 0.7f));
		babyHitboxes.put(Horse.class, new Hitbox(Horse.class, 0.7f, 0.8f));
		babyHitboxes.put(SkeletonHorse.class, new Hitbox(SkeletonHorse.class, 0.7f, 0.8f));
		babyHitboxes.put(Llama.class, new Hitbox(Llama.class, 0.4f, 0.9f));
		babyHitboxes.put(Zombie.class, new Hitbox(Zombie.class, 0.3f, 0.9f));
		babyHitboxes.put(PigZombie.class, new Hitbox(PigZombie.class, 0.3f, 0.9f));
		babyHitboxes.put(Husk.class, new Hitbox(Husk.class, 0.3f, 0.9f));
		babyHitboxes.put(Villager.class, new Hitbox(Villager.class, 0.3f, 0.9f));

		babyHitboxes.put(Slime.class, new Hitbox(Slime.class, 0.5f, 0.5f));
		babyHitboxes.put(MagmaCube.class, new Hitbox(MagmaCube.class, 0.5f, 0.5f));


		/* Normal Size Mobs */

		hitboxes.put(Player.class, new Hitbox(Player.class, 0.6f, 1.8f));
		hitboxes.put(Rabbit.class, new Hitbox(Rabbit.class, 0.4f, 0.5f));
		hitboxes.put(Chicken.class, new Hitbox(Chicken.class, 0.4f, 0.7f));
		hitboxes.put(Wolf.class, new Hitbox(Wolf.class, 0.6f, 0.85f));
		hitboxes.put(Pig.class, new Hitbox(Pig.class, 0.9f, 0.9f));
		hitboxes.put(Sheep.class, new Hitbox(Sheep.class, 0.9f, 1.3f));
		hitboxes.put(Cow.class, new Hitbox(Cow.class, 0.9f, 1.4f));
		hitboxes.put(PolarBear.class, new Hitbox(PolarBear.class, 1.3f, 1.4f));
		hitboxes.put(Horse.class, new Hitbox(Horse.class, 1.4f, 1.6f));
		hitboxes.put(SkeletonHorse.class, new Hitbox(SkeletonHorse.class, 1.4f, 1.6f));
		hitboxes.put(Llama.class, new Hitbox(Llama.class, 0.9f, 1.9f));
		hitboxes.put(Silverfish.class, new Hitbox(Silverfish.class, 0.4f, 0.3f));
		hitboxes.put(Endermite.class, new Hitbox(Endermite.class, 0.4f, 0.3f));
		hitboxes.put(Spider.class, new Hitbox(Spider.class, 1.4f, 0.9f));
		hitboxes.put(Shulker.class, new Hitbox(Shulker.class, 1f, 1.2f));
		hitboxes.put(MagmaCube.class, new Hitbox(MagmaCube.class, 1f, 1f)); //medium magma cube
		hitboxes.put(Slime.class, new Hitbox(Slime.class, 1f, 1f)); //medium slime
		hitboxes.put(Creeper.class, new Hitbox(Creeper.class, 0.6f, 1.7f));
		hitboxes.put(Blaze.class, new Hitbox(Blaze.class, 0.6f, 1.8f));
		hitboxes.put(Zombie.class, new Hitbox(Zombie.class, 0.6f, 1.9f));
		hitboxes.put(PigZombie.class, new Hitbox(PigZombie.class, 0.3f, 0.9f));
		hitboxes.put(Evoker.class, new Hitbox(Evoker.class, 0.6f, 1.9f));
		hitboxes.put(Villager.class, new Hitbox(Villager.class, 0.6f, 1.9f));
		hitboxes.put(Husk.class, new Hitbox(Husk.class, 0.6f, 1.9f));
		hitboxes.put(Skeleton.class, new Hitbox(Skeleton.class, 0.6f, 2f));
		hitboxes.put(Stray.class, new Hitbox(Stray.class, 0.6f, 2f));
		hitboxes.put(ElderGuardian.class, new Hitbox(ElderGuardian.class, 0.9f, 0.9f));
		hitboxes.put(WitherSkeleton.class, new Hitbox(WitherSkeleton.class, 0.7f, 24f));
		hitboxes.put(Enderman.class, new Hitbox(Enderman.class, 0.6f, 2.9f));
		hitboxes.put(Wither.class, new Hitbox(Wither.class, 0.9f, 3.5f));
		hitboxes.put(Ghast.class, new Hitbox(Ghast.class, 4f, 4f));
		hitboxes.put(EnderDragon.class, new Hitbox(EnderDragon.class, 16f, 8f));
		hitboxes.put(Squid.class, new Hitbox(Squid.class, 0.8f, 0.8f));
		hitboxes.put(Bat.class, new Hitbox(Bat.class, 0.5f, 0.9f));
		hitboxes.put(Ocelot.class, new Hitbox(Ocelot.class, 0.6f, 0.6f));
		hitboxes.put(Golem.class, new Hitbox(Golem.class, 0.7f, 1.9f));
		hitboxes.put(IronGolem.class, new Hitbox(IronGolem.class, 1.4f, 2.7f));

		/* Large Hitboxes */

		largeHitboxes.put(Slime.class, new Hitbox(Slime.class, 2f, 2f));
		largeHitboxes.put(MagmaCube.class, new Hitbox(MagmaCube.class, 2f, 2f));

		Bukkit.getLogger().info("Setup hitboxes!");
	}

	private Hitbox getHitbox(LivingEntity entity) {

		Class<? extends Entity> type = entity.getType().getEntityClass();

		if (type == null)
			return null;

		boolean isAgeable = Ageable.class.isAssignableFrom(type);

		if (type.equals(Slime.class) || type.equals(MagmaCube.class)) {
			Slime slime = (Slime) entity;

			Hitbox box = null;

			switch(slime.getSize()) {
				case 1:
					box = babyHitboxes.get(type);
					break;
				case 2:
					box = hitboxes.get(type);
					break;
				case 3:
					box = largeHitboxes.get(type);
					break;
				default:
					Bukkit.getLogger().warning("Could not get slime or magma cube size of " + slime.getSize());
					break;
			}

			return box;
		} else if (isAgeable) {
			Ageable ageable = (Ageable) entity;
			Hitbox box = null;

			if (ageable.isAdult())
				box = hitboxes.get(type);
			else
				box = babyHitboxes.get(type);

			return box;
		} if (type.equals(Zombie.class)) {
			Zombie z = (Zombie) entity;

			Hitbox box = null;

			if (z.isBaby())
				box = babyHitboxes.get(type);
			else
				box = hitboxes.get(type);

			return box;
		}

		return null;
	}

	public static boolean withinBounds(LivingEntity entity, Location loc) {

		Hitbox hitbox = getInstance().getHitbox(entity);
		if (hitbox == null) {
			Bukkit.getLogger().warning(String.format("Could not get hitbox for entity with type %s", entity.getClass().getName()));
			return false;
		}

		float locX = (float) loc.getX();
		float locY = (float) loc.getY();
		float locZ = (float) loc.getZ();

		float entX = (float) entity.getLocation().getX();
		float entY = (float) entity.getLocation().getY();
		float entZ = (float) entity.getLocation().getZ();

		float width = hitbox.getWidth() * 1.05f;
		float height = hitbox.getHeight() * 1.05f;

		// x side to side, z is front to back, y is top to bottom (- and + represent the total length, width, or heigh when added together)
		if(((locX - width < entX) && (entX < locX + width))
				&& ((locY - height < entY) && (entY < locY + height))
				&& ((locZ - width < entZ) && (entZ < locZ + width))) {
			return true;
		}

		return false;
	}
}
