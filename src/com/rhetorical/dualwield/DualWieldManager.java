package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

class DualWieldManager implements Listener {

    DualWieldManager() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    @EventHandler
    void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (!Main.allowedMaterials.contains(e.getPlayer().getInventory().getItemInOffHand().getType()))
            return;

        playAnimation(e.getPlayer());
        performSwing(e.getPlayer(), 3);
    }

    private double getDamage(LivingEntity player, LivingEntity victim, double baseDamage) {
        if (!(victim instanceof Player)) {
            return baseDamage;
        }

//        damage = damage * ( 1 - min( 20, max( defensePoints / 5, defensePoints - damage / ( toughness / 4 + 2 ) ) ) / 25 )

        return baseDamage;
    }

    private boolean performSwing(Player from, int swingDistance) {
        RayCast rayCast = new RayCast(new Point((float) from.getLocation().getX(), (float) from.getLocation().getY(), (float) from.getLocation().getZ()), from.getEyeLocation().getPitch(), from.getEyeLocation().getYaw(), 3f);
        Object raycastHit = rayCast.getHit();
        ItemStack heldItem = from.getInventory().getItemInOffHand();
        double damage = ItemStats.getAttackDamage(heldItem.getType());
        if (raycastHit != null) {
            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent((Entity) from, (Entity) ((HitboxBounds) raycastHit).getParent(), EntityDamageEvent.DamageCause.CUSTOM, damage);
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerHitEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            ((LivingEntity) e.getEntity()).damage(e.getDamage());
        }
    }

    /* NMS Stuff */

    private void playAnimation(Player p) {
        try {
            Constructor<?> animationConstructor = Main.getNMSVersion("PacketPlayOutAnimation").getConstructor(Main.getNMSVersion("Entity"), int.class);
            Object packet = animationConstructor.newInstance(Main.getCraftPlayer().cast(p).getClass().getMethod("getHandle").invoke(p), 3);
            sendPacket(p, packet);
        } catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage("§cCould not get class for name! Is there a typo?");
            e.printStackTrace();
        }
    }

    private void sendPacket(Player player, Object packet) {
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