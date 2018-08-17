package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class HitboxManager implements Listener {

    private static HitboxManager instance;
    private HashMap<LivingEntity, HitboxBounds> hitboxes;

    void setup() {
        instance = this;
        hitboxes = new HashMap<LivingEntity, HitboxBounds>();
        Bukkit.getServer().getPluginManager().registerEvents(instance, Main.plugin);
        updateHitboxes();
    }

    static Object interceptsHitbox(Point point) {
        for(HitboxBounds box : instance.hitboxes.values()) {
            if(box.contains(point)) {
                return box;
            }
        }

        return false;
    }

    void updateHitboxes() {
        BukkitRunnable br = new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    List<LivingEntity> nearbyEntities = new ArrayList<LivingEntity>();
                    for (Entity entity : p.getNearbyEntities(20, 20, 20)) {
                        if (entity instanceof LivingEntity) {
                            nearbyEntities.add((LivingEntity) entity);
                        }
                    }

                    for (LivingEntity entity : nearbyEntities) {
                        if (hitboxes.containsKey(entity)) {
                            continue;
                        }

                        //If entity is humanoid
                        HitboxBounds hitbox =  new HitboxBounds(new Point(0, 2, 0), new Point(1, 0, 1), entity);
                        hitboxes.put(entity, hitbox);
                    }

                }
            }
        };

        br.runTaskTimer(Main.plugin, 0l, 5l);
    }
}
