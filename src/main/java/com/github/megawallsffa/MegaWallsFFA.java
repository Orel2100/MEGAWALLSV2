package com.github.megawallsffa;

import com.github.megawallsffa.commands.KitCommand;
import com.github.megawallsffa.commands.MegaWallsFFACommand;
import com.github.megawallsffa.game.OreManager;
import com.github.megawallsffa.kit.KitManager;
import com.github.megawallsffa.listeners.BlockListener;
import com.github.megawallsffa.listeners.DamageListener;
import com.github.megawallsffa.listeners.GuiListener;
import com.github.megawallsffa.listeners.PlayerListener;
import com.github.megawallsffa.player.PlayerDataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MegaWallsFFA extends JavaPlugin {

    private KitManager kitManager;
    private PlayerDataManager playerDataManager;
    private OreManager oreManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        kitManager = new KitManager(this);
        kitManager.loadKits();
        playerDataManager = new PlayerDataManager();
        oreManager = new OreManager(this);

        getCommand("mwffa").setExecutor(new MegaWallsFFACommand(this));
        getCommand("kit").setExecutor(new KitCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);


        getLogger().info("MegaWallsFFA has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MegaWallsFFA has been disabled!");
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public OreManager getOreManager() {
        return oreManager;
    }
}