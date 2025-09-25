package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.classes.EnergyManager;
import com.megawallsffa.lobby.LobbyItemManager;
import com.megawallsffa.lobby.LobbyManager;
import com.megawallsffa.player.PlayerData;
import com.megawallsffa.player.PlayerManager;
import com.megawallsffa.ui.ScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final MegaWallsFFA plugin;
    private final ArenaManager arenaManager;
    private final PlayerManager playerManager;
    private final LobbyManager lobbyManager;
    private final ScoreboardManager scoreboardManager;
    private final LobbyItemManager lobbyItemManager;
    private final EnergyManager energyManager;

    public PlayerDeathListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
        this.playerManager = plugin.getPlayerManager();
        this.lobbyManager = plugin.getLobbyManager();
        this.scoreboardManager = plugin.getScoreboardManager();
        this.lobbyItemManager = plugin.getLobbyItemManager();
        this.energyManager = plugin.getEnergyManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // Only proceed if the death happened inside the arena
        if (!arenaManager.isWithinArena(victim.getLocation())) {
            return;
        }

        // Prevent default death behavior
        event.setDeathMessage(null);
        event.getDrops().clear();

        PlayerData victimData = playerManager.getPlayerData(victim);
        victimData.incrementDeaths();
        victimData.resetKillStreak();

        // Remove UI elements from the victim
        scoreboardManager.removeScoreboard(victim);
        energyManager.removeEnergyBar(victim);

        // Teleport victim to lobby
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            Location lobby = lobbyManager.getLobbyLocation();
            if (lobby != null) {
                victim.teleport(lobby);
                victim.sendMessage(ChatColor.YELLOW + "You were eliminated! Sent back to the lobby.");
                lobbyItemManager.giveLobbyItems(victim); // Give lobby items
            } else {
                victim.sendMessage(ChatColor.RED + "You were eliminated, but no lobby point is set!");
            }
        }, 1L);

        if (killer != null && killer != victim) {
            // A player killed another player
            PlayerData killerData = playerManager.getPlayerData(killer);
            killerData.incrementKills();

            int coinsForKill = 10; // Base coins
            int streakBonus = killerData.getKillStreak() / 5; // Example: +1 coin every 5 kills
            int totalCoins = coinsForKill + streakBonus;

            killerData.addCoins(totalCoins);

            plugin.getServer().broadcastMessage(
                ChatColor.AQUA + victim.getName() + ChatColor.GRAY + " was slain by " +
                ChatColor.AQUA + killer.getName() + ChatColor.GRAY + ".");

            killer.sendMessage(ChatColor.GOLD + "+"+ totalCoins + " coins" + ChatColor.GRAY + " for eliminating " + victim.getName());

            // Check for kill streak milestones
            if (killerData.getKillStreak() > 0 && killerData.getKillStreak() % 5 == 0) {
                killer.sendTitle(ChatColor.GREEN + "Kill Streak!",
                    ChatColor.YELLOW + "You are on a " + killerData.getKillStreak() + " kill streak!", 10, 70, 20);
                plugin.getServer().broadcastMessage(
                    ChatColor.AQUA + killer.getName() + ChatColor.YELLOW + " is on a " +
                    ChatColor.RED + killerData.getKillStreak() + ChatColor.YELLOW + " kill streak!");
            }

        } else {
            // Player died from environment or self-inflicted damage
            plugin.getServer().broadcastMessage(
                ChatColor.AQUA + victim.getName() + ChatColor.GRAY + " died.");
        }
    }
}