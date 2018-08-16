package com.rhetorical.dualwield;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    static Plugin plugin;

    private static DualWieldManager dwManager;

    @Override
    public void onEnable() {
        plugin = this;
        dwManager = new DualWieldManager();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!label.equalsIgnoreCase("dualWield") && !label.equalsIgnoreCase("dw"))
            return false;



        return true;
    }

}