package com.rhetorical.dualwield;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    static Plugin plugin;

    private static String versionNMS;

    static List<Material> allowedMaterials;
    static List<Material> disallowedMaterials; //Materials that will prevent the offhand from swinging if they are present.

    static boolean requirePermission;

    static boolean postWaterUpdate = false;

    static float accuracy = 0.05f;

    //Effectively final
    private static boolean debug = false;

    private String prefix = ChatColor.WHITE + "[" + ChatColor.YELLOW + "DW" + ChatColor.WHITE + "]" + ChatColor.RESET + " ";

    @Override
    public void onEnable() {
        plugin = this;

        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        versionNMS = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        try {
            //Instantiate singleton.
            DualWieldManager.create();
        } catch(DualWieldManagerAlreadyExistsException e) {
            e.printStackTrace();
        }

        for (int i = 13; i < 100; i++) {
            if (Bukkit.getServer().getBukkitVersion().startsWith("1." + i)) {
                postWaterUpdate = true;
                break;
            }else  if (Bukkit.getServer().getBukkitVersion().startsWith("2")) {
                postWaterUpdate = true;
                break;
            }
        }


        if (postWaterUpdate) {
            Bukkit.getConsoleSender().sendMessage(prefix + "Spigot version 1.13+ detected!");
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "Spigot version < 1.12 detected!");
        }

        List<String> materialNames = plugin.getConfig().getStringList("offhand_materials");
        allowedMaterials = getMaterials(materialNames);

        List<String> disallowedMaterialNames = plugin.getConfig().getStringList("disallowed_materials");
        disallowedMaterials = getMaterials(disallowedMaterialNames);

        requirePermission = plugin.getConfig().getBoolean("require_permission");

        accuracy = (float) plugin.getConfig().getDouble("accuracy");

        debug = plugin.getConfig().getBoolean("debug");

        if (accuracy < 0.05f)
            accuracy = 0.05f;

        Bukkit.getServer().getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "DuelWielding v" + ChatColor.WHITE + plugin.getDescription().getVersion() + ChatColor.GREEN + " is now enabled!");

    }

    @Override
    public void onDisable() {
    	allowedMaterials = new ArrayList<>();
    	disallowedMaterials = new ArrayList<>();

    	DualWieldManager.destroy();
    }

    /** Returns the appropriately versioned class for the given name.
	 * @param name The name of the class.
	 * @return The class with the given name for the NMS version.
	 * */
    static Class<?> getNMSVersion(String name){
        try {
            return Class.forName("net.minecraft.server." + versionNMS + "." + name);
        } catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not get class for name! Is there a typo?");
            e.printStackTrace();
            return null;
        }
    }

    /**
	 * @return The CraftPlayer class for the current NMS version.
	 * */
    static Class getCraftPlayer() {
        try {
            return Class.forName("org.bukkit.craftbukkit." + versionNMS + ".entity.CraftPlayer");
        } catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not get CraftPlayer class! Is there a typo?");
            e.printStackTrace();
            return null;
        }
    }

    /** Gets a list of materials from a given list of strings.
     * @param names The list of material names to be converted into materials.
     * @return A list of converted materials.
     * */
    private List<Material> getMaterials(List<String> names) {
        List<Material> mats = new ArrayList<>();
        for(String mat : names) {
            Material m;
            try {
                m = Material.valueOf(mat);
            } catch(Exception ignored) {
                continue;
            }


            mats.add(m);
        }

        return mats;
    }

    static boolean isDebug() {
        return debug;
    }

}