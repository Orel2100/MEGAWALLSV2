package com.megawallsffa;

import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.classes.ClassManager;
import com.megawallsffa.classes.EnergyManager;
import com.megawallsffa.commands.ArenaCommand;
import com.megawallsffa.commands.MegaWallsCommand;
import com.megawallsffa.listeners.BlockBreakListener;
import com.megawallsffa.listeners.LobbyItemListener;
import com.megawallsffa.listeners.LobbyListener;
import com.megawallsffa.listeners.PlayerDeathListener;
import com.megawallsffa.lobby.LobbyItemManager;
import com.megawallsffa.lobby.LobbyManager;
import com.megawallsffa.player.PlayerManager;
import com.megawallsffa.ui.MenuManager;
import com.megawallsffa.ui.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaWallsFFA extends JavaPlugin {

    private ArenaManager arenaManager;
    private PlayerManager playerManager;
    private LobbyManager lobbyManager;
    private ScoreboardManager scoreboardManager;
    private MenuManager menuManager;
    private LobbyItemManager lobbyItemManager;
    private ClassManager classManager;
    private EnergyManager energyManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        // Initialize managers
        arenaManager = new ArenaManager(this);
        playerManager = new PlayerManager(this);
        lobbyManager = new LobbyManager(this);
        scoreboardManager = new ScoreboardManager(this);
        menuManager = new MenuManager();
        lobbyItemManager = new LobbyItemManager();
        classManager = new ClassManager();
        energyManager = new EnergyManager(this);

        // Register commands
        getCommand("arena").setExecutor(new ArenaCommand(arenaManager));
        getCommand("megawalls").setExecutor(new MegaWallsCommand(this));
        getCommand("class").setExecutor(new ClassCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new LobbyItemListener(this), this);

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

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public LobbyItemManager getLobbyItemManager() {
        return lobbyItemManager;
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public EnergyManager getEnergyManager() {
        return energyManager;
    }
}