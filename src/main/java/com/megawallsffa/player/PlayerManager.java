package com.megawallsffa.player;

import com.megawallsffa.MegaWallsFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Listener {

    private final MegaWallsFFA plugin;
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
        // Register the events in this class
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Gets the PlayerData for a given player, creating a new profile if one doesn't exist.
     * @param player The player whose data is being requested.
     * @return The PlayerData object for the player.
     */
    public PlayerData getPlayerData(Player player) {
        return playerDataMap.computeIfAbsent(player.getUniqueId(), PlayerData::new);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // When a player joins, we ensure their data is loaded into the map.
        // computeIfAbsent in getPlayerData handles this for us, so we just call it.
        getPlayerData(event.getPlayer());
        plugin.getLogger().info("Initialized PlayerData for " + event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // When a player quits, we should save their data.
        // For now, we will just remove them from the in-memory map.
        // TODO: Implement saving data to a persistent storage (e.g., YAML file).
        playerDataMap.remove(event.getPlayer().getUniqueId());
        plugin.getLogger().info("Removed PlayerData for " + event.getPlayer().getName() + " from memory.");
    }
}