package com.megawallsffa;

import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.commands.KitCommand;
import com.megawallsffa.commands.MegaWallsCommand;
import com.megawallsffa.commands.MegaWallsFFACommand;
import com.megawallsffa.game.OreManager;
import com.megawallsffa.kit.KitManager;
import com.megawallsffa.listeners.DamageListener;
import com.megawallsffa.listeners.DeathListener;
import com.megawallsffa.listeners.GuiListener;
import com.megawallsffa.listeners.LobbyItemListener;
import com.megawallsffa.listeners.PlayerListener;
import com.megawallsffa.lobby.LobbyItemManager;
import com.megawallsffa.lobby.LobbyManager;
import com.megawallsffa.player.PlayerDataManager;
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
    private KitManager kitManager;
    private PlayerDataManager playerDataManager;
    private OreManager oreManager;

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
        kitManager = new KitManager(this);
        kitManager.loadKits();
        playerDataManager = new PlayerDataManager();
        oreManager = new OreManager(this);

        // Register commands
        getCommand("megawalls").setExecutor(new MegaWallsCommand(this));
        getCommand("mwffa").setExecutor(new MegaWallsFFACommand(this));
        getCommand("kit").setExecutor(new KitCommand(this));


        // Register listeners
        getServer().getPluginManager().registerEvents(new LobbyItemListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

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