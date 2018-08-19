package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    static Plugin plugin;

    private static String versionNMS;

    static Class<?> NMSClass;

    static List<Material> allowedMaterials;

    static boolean requirePermission;

    private DualWieldManager manager;

    @Override
    public void onEnable() {
        plugin = this;

        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        versionNMS = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        manager = new DualWieldManager();


        List<String> materialNames = plugin.getConfig().getStringList("offhand_materials");
        allowedMaterials = new ArrayList<Material>();

        for(String mat : materialNames) {
            Material m;
            try {
                m = Material.valueOf(mat);
            } catch(Exception ignored) {
                continue;
            }


            allowedMaterials.add(m);
        }

        requirePermission = plugin.getConfig().getBoolean("require_permission");

        Bukkit.getServer().getConsoleSender().sendMessage("§aRhetorical's Dual Wield v§f" + plugin.getDescription().getVersion() + " §ais now enabled!");

    }

    @Override
    public void onDisable() {

    }

    static Class<?> getNMSVersion(String name){
        try {
            return Class.forName("net.minecraft.server." + versionNMS + "." + name);
        } catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage("§cCould not get class for name! Is there a typo?");
            e.printStackTrace();
            return null;
        }
    }

    static Class getCraftPlayer() {
        try {
            return Class.forName("org.bukkit.craftbukkit." + versionNMS + ".entity.CraftPlayer");
        } catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage("§cCould not get CraftPlayer class! Is there a typo?");
            e.printStackTrace();
            return null;
        }
    }

}