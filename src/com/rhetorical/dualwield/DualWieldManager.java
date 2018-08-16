package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

class DualWieldManager implements Listener {

    DualWieldManager() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    @EventHandler
    void onRightClick(PlayerInteractEvent e) {
    }


    private void PlayAnimation(NMSVersion version) {
    }

}

enum NMSVersion {
    V_1_8,
    V_1_9,
    V_1_10,
    V_1_11,
    V_1_12,
    V_1_13
}