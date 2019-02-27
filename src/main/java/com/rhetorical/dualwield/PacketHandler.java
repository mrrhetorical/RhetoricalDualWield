package com.rhetorical.dualwield;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

class PacketHandler {


	/**
	 * Sends the player an offhand swing animation.
	 * @param p The player to send the animation to.
	 * */
	static void playAnimation(Player p) {
		try {
			Constructor<?> animationConstructor = Main.getNMSVersion("PacketPlayOutAnimation").getConstructor(Main.getNMSVersion("Entity"), int.class);
			Object packet = animationConstructor.newInstance(Main.getCraftPlayer().cast(p).getClass().getMethod("getHandle").invoke(p), 3);
			sendPacket(p, packet);
		} catch(Exception e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not get class for name! Is there a typo?");
			e.printStackTrace();
		}
	}

	/**
	 * Sends the player the given packet.
	 * @param player The player to send the packet to.
	 * @param packet The packet to send to the player.
	 * */
	static void sendPacket(Player player, Object packet) {
		try {
			Object handle = Main.getCraftPlayer().cast(player).getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			Class<?> packetClass = Main.getNMSVersion("Packet");
			playerConnection.getClass().getMethod("sendPacket" , packetClass).invoke(playerConnection, packet);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}