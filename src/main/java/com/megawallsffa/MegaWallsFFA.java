package com.megawallsffa;

import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.commands.ArenaCommand;
import com.megawallsffa.commands.MegaWallsCommand;
import com.megawallsffa.listeners.BlockBreakListener;
import com.megawallsffa.listeners.LobbyListener;
import com.megawallsffa.listeners.PlayerDeathListener;
import com.megawallsffa.lobby.LobbyManager;
import com.megawallsffa.player.PlayerManager;
import com.megawallsffa.ui.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaWallsFFA extends JavaPlugin {

    private ArenaManager arenaManager;
    private PlayerManager playerManager;
    private LobbyManager lobbyManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        // Initialize managers
        arenaManager = new ArenaManager(this);
        playerManager = new PlayerManager(this);
        lobbyManager = new LobbyManager(this);
        scoreboardManager = new ScoreboardManager(this);

        // Register commands
        getCommand("arena").setExecutor(new ArenaCommand(arenaManager));
        getCommand("megawalls").setExecutor(new MegaWallsCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);

        // Start tasks
        scoreboardManager.startUpdater();

        getLogger().info("MegaWallsFFA has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("MegaWallsFFA has been disabled!");
    }

    // Manager Getters
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}