package com.megawallsffa;

import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.commands.ArenaCommand;
import com.megawallsffa.listeners.BlockBreakListener;
import com.megawallsffa.listeners.LobbyListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaWallsFFA extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        arenaManager = new ArenaManager(this);
        getCommand("arena").setExecutor(new ArenaCommand(arenaManager));
        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getLogger().info("MegaWallsFFA has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("MegaWallsFFA has been disabled!");
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}