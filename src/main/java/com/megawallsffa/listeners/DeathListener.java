package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.lobby.LobbyItemManager;
import com.megawallsffa.lobby.LobbyManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    private final MegaWallsFFA plugin;
    private final LobbyManager lobbyManager;
    private final LobbyItemManager lobbyItemManager;

    public DeathListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
        this.lobbyManager = plugin.getLobbyManager();
        this.lobbyItemManager = plugin.getLobbyItemManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getDrops().clear();

        new BukkitRunnable() {
            @Override
            public void run() {
                Location lobbyLocation = lobbyManager.getLobbyLocation();
                if (lobbyLocation != null) {
                    player.teleport(lobbyLocation);
                } else {
                    // Fallback to world spawn if lobby is not set
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                lobbyItemManager.giveLobbyItems(player);
            }
        }.runTaskLater(plugin, 1L);
    }
}