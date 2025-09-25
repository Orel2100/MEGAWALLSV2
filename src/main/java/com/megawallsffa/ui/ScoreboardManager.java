package com.megawallsffa.ui;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {

    private final MegaWallsFFA plugin;
    private final ConcurrentHashMap<UUID, Scoreboard> activeScoreboards = new ConcurrentHashMap<>();

    public ScoreboardManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    public void setScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("MegaWallsFFA", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "MEGA WALLS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Setup static and dynamic lines using teams
        objective.getScore(" ").setScore(9);

        // Kills Line
        Team killsTeam = board.registerNewTeam("kills");
        String killsEntry = ChatColor.BLACK + "" + ChatColor.WHITE;
        killsTeam.addEntry(killsEntry);
        killsTeam.setPrefix(ChatColor.WHITE + "Kills: ");
        killsTeam.setSuffix(ChatColor.GREEN + "0");
        objective.getScore(killsEntry).setScore(8);

        // Deaths Line
        Team deathsTeam = board.registerNewTeam("deaths");
        String deathsEntry = ChatColor.BLACK + "" + ChatColor.BLACK;
        deathsTeam.addEntry(deathsEntry);
        deathsTeam.setPrefix(ChatColor.WHITE + "Deaths: ");
        deathsTeam.setSuffix(ChatColor.GREEN + "0");
        objective.getScore(deathsEntry).setScore(7);

        objective.getScore("  ").setScore(6);

        // Coins Line
        Team coinsTeam = board.registerNewTeam("coins");
        String coinsEntry = ChatColor.BLACK + "" + ChatColor.RED;
        coinsTeam.addEntry(coinsEntry);
        coinsTeam.setPrefix(ChatColor.WHITE + "Coins: ");
        coinsTeam.setSuffix(ChatColor.GOLD + "0");
        objective.getScore(coinsEntry).setScore(5);

        // Streak Line
        Team streakTeam = board.registerNewTeam("streak");
        String streakEntry = ChatColor.BLACK + "" + ChatColor.GOLD;
        streakTeam.addEntry(streakEntry);
        streakTeam.setPrefix(ChatColor.WHITE + "Streak: ");
        streakTeam.setSuffix(ChatColor.GREEN + "0");
        objective.getScore(streakEntry).setScore(4);

        objective.getScore("   ").setScore(3);
        objective.getScore(ChatColor.YELLOW + "play.yourserver.net").setScore(2);

        player.setScoreboard(board);
        activeScoreboards.put(player.getUniqueId(), board);
    }

    public void removeScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        activeScoreboards.remove(player.getUniqueId());
    }

    public void startUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : activeScoreboards.keySet()) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null || !player.isOnline()) {
                        activeScoreboards.remove(uuid);
                        continue;
                    }

                    // Scoreboard should only be visible in the arena
                    if (plugin.getArenaManager().isWithinArena(player.getLocation())) {
                         // Ensure they have a board if they entered the arena
                        if (!activeScoreboards.containsKey(player.getUniqueId())) {
                            setScoreboard(player);
                        }
                        updateScoreboard(player);
                    } else {
                        // Remove board if they left the arena
                        if (activeScoreboards.containsKey(player.getUniqueId())) {
                            removeScoreboard(player);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L); // Update every second
    }

    private void updateScoreboard(Player player) {
        Scoreboard board = activeScoreboards.get(player.getUniqueId());
        if (board == null) return;

        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        // Update team suffixes to change the displayed score
        board.getTeam("kills").setSuffix(ChatColor.GREEN + "" + data.getKills());
        board.getTeam("deaths").setSuffix(ChatColor.GREEN + "" + data.getDeaths());
        board.getTeam("coins").setSuffix(ChatColor.GOLD + "" + data.getCoins());
        board.getTeam("streak").setSuffix(ChatColor.GREEN + "" + data.getKillStreak());
    }
}