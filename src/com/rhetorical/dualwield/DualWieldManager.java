package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
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

        performSwing(e.getPlayer());
    }

    private double getDamage(LivingEntity player, LivingEntity victim, double baseDamage) {
        if (!(victim instanceof Player)) {
            return baseDamage;
        }

//        damage = damage * ( 1 - min( 20, max( defensePoints / 5, defensePoints - damage / ( toughness / 4 + 2 ) ) ) / 25 )

        return baseDamage;
    }

    private boolean performSwing(Player from) {

        ItemStack heldItem = from.getInventory().getItemInOffHand();

        if (Main.requirePermission) {
            if (!from.hasPermission("rdw.use." + heldItem.getType().toString().toUpperCase()) && !from.isOp() && !from.hasPermission("rdw.use.*"))
                return false;
        }

        playAnimation(from);

//        List<Block> blocks = from.getLineOfSight(null, ItemStats.getSwingDistance(heldItem.getType()));
//
//        if (blocks.isEmpty())
//            return false;
//
//        for (Block b : blocks) {
//            List<Entity> nearby = (List<Entity>) b.getWorld().getNearbyEntities(b.getLocation(), 1.5d, 2.5d, 1.5d);
//
//            if (nearby.isEmpty())
//                continue;
//
//            for(Entity e : nearby) {
//                if (!(e instanceof LivingEntity))
//                    continue;
//
//                EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(from, e, EntityDamageEvent.DamageCause.ENTITY_ATTACK, ItemStats.getAttackDamage(heldItem.getType()));
//                Bukkit.getServer().getPluginManager().callEvent(event);
//                return true;
//            }
//        }

        Entity e = getTargetEntity(from);

        if (e != null) {
            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(from, e, EntityDamageEvent.DamageCause.ENTITY_ATTACK, ItemStats.getAttackDamage(heldItem.getType()));
                Bukkit.getServer().getPluginManager().callEvent(event);
                return true;
        }

        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerHitEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            ((LivingEntity) e.getEntity()).damage(e.getDamage());
        }
    }

    private Entity getTargetEntity(Player player) {
        Collection<Entity> entities = player.getNearbyEntities(30, 10, 30);
        ArrayList<Location> locations = new ArrayList<Location>();

        for (int i = 1; i <= 30; i++) {
            locations.add(player.getTargetBlock(null, i).getLocation());
        }

        for(Entity entity : entities) {
            for(Location loc : locations) {
                int locX = (int) loc.getX();
                int locY = (int) loc.getY();
                int locZ = (int) loc.getZ();

                int entX = (int) entity.getLocation().getX();
                int entY = (int) entity.getLocation().getY();
                int entZ = (int) entity.getLocation().getZ();

                if(((locX-2 < entX)&&(entX < locX+2))&&((locY-3 < entY)&&(entY < locY+3))&&((locZ-2 < entZ)&&(entZ < locZ+2))) {
                    return entity;
                }
            }
        }
        return null;
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