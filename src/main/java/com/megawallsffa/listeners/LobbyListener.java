package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.classes.EnergyManager;
import com.megawallsffa.lobby.LobbyItemManager;
import com.megawallsffa.ui.ScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyListener implements Listener {

    private final MegaWallsFFA plugin;
    private final ArenaManager arenaManager;
    private final ScoreboardManager scoreboardManager;
    private final LobbyItemManager lobbyItemManager;
    private final EnergyManager energyManager;

    public LobbyListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
        this.scoreboardManager = plugin.getScoreboardManager();
        this.lobbyItemManager = plugin.getLobbyItemManager();
        this.energyManager = plugin.getEnergyManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.CAKE) {
            return;
        }

        // A better approach for a "named" block is to check for a nearby entity, like an Armor Stand.
        // This avoids cluttering the world with signs and is more robust.
        boolean playButtonFound = clickedBlock.getWorld().getNearbyEntities(clickedBlock.getLocation().add(0.5, 1.5, 0.5), 1, 1, 1)
                .stream()
                .anyMatch(entity -> entity.getCustomName() != null && ChatColor.stripColor(entity.getCustomName()).equalsIgnoreCase("PLAY!"));

        if (!playButtonFound) {
            return;
        }


        Player player = event.getPlayer();
        Location spawnPoint = arenaManager.getRandomSpawnPoint();

        if (spawnPoint != null) {
            player.sendTitle(ChatColor.YELLOW + "Teleporting...", ChatColor.GRAY + "Get ready for battle!", 10, 40, 10);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            new BukkitRunnable() {
                @Override
                public void run() {
                    lobbyItemManager.clearLobbyItems(player);
                    player.teleport(spawnPoint);
                    player.sendTitle("", "", 0, 1, 0); // Clear title
                    player.sendMessage(ChatColor.GREEN + "You have joined the MegaWalls FFA arena!");
                    scoreboardManager.setScoreboard(player);
                    energyManager.createEnergyBar(player);

                    // Equip kit for selected class
                    com.megawallsffa.player.PlayerData data = plugin.getPlayerManager().getPlayerData(player);
                    com.megawallsffa.classes.MegaWallsClass selectedClass = data.getSelectedClass();
                    if (selectedClass != null) {
                        selectedClass.getKit(1).apply(player); // Apply Tier 1 kit
                    } else {
                        // Maybe give a default kit or send a message
                        player.sendMessage(ChatColor.YELLOW + "You haven't selected a class! Use /class to choose one.");
                    }
                }
            }.runTaskLater(plugin, 40L); // 2-second delay (20 ticks * 2)

        } else {
            player.sendMessage(ChatColor.RED + "The arena is not ready yet. No spawn points have been set.");
        }
    }
}