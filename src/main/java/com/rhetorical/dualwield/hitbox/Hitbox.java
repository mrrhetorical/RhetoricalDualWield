package com.rhetorical.dualwield.hitbox;

import org.bukkit.entity.LivingEntity;

public class Hitbox {

	private final Class<? extends LivingEntity> entity;
	private final float height;
	private final float width;

	public Hitbox(Class<? extends LivingEntity> entity, float width, float height) {
		this.entity = entity;
		this.height = height;
		this.width = width;
	}

	public Class<? extends LivingEntity> getEntity() {
		return entity;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}
}
