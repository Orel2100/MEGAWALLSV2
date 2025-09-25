package com.megawallsffa.lobby;

import com.megawallsffa.MegaWallsFFA;
import org.bukkit.Location;

public class LobbyManager {

    private final MegaWallsFFA plugin;
    private Location lobbyLocation;

    public LobbyManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
        loadLobbyLocation();
    }

    /**
     * Gets the currently set lobby location.
     * @return The lobby location, or null if not set.
     */
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    /**
     * Sets the lobby location and saves it to the config.
     * @param location The new lobby location.
     */
    public void setLobbyLocation(Location location) {
        this.lobbyLocation = location;
        plugin.getConfig().set("lobby.location", location);
        plugin.saveConfig();
    }

    /**
     * Loads the lobby location from the config file.
     */
    private void loadLobbyLocation() {
        if (plugin.getConfig().contains("lobby.location")) {
            this.lobbyLocation = plugin.getConfig().getLocation("lobby.location");
        }
    }
}