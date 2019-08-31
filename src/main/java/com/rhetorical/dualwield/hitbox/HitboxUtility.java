package com.rhetorical.dualwield.hitbox;

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
		babyHitboxes.put(PigZombie.class, new Hitbox(PigZombie.class, 0.3f, 0.9f));
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

		babyHitboxes.put(Slime.class, new Hitbox(Slime.class, 2f, 2f));
		babyHitboxes.put(MagmaCube.class, new Hitbox(MagmaCube.class, 2f, 2f));
	}

	public static boolean withinBounds(LivingEntity entity, Location loc) {



		return false;
	}
}
