package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import com.megawallsffa.ui.ScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyListener implements Listener {

    private final ArenaManager arenaManager;
    private final ScoreboardManager scoreboardManager;

    public LobbyListener(MegaWallsFFA plugin) {
        this.arenaManager = plugin.getArenaManager();
        this.scoreboardManager = plugin.getScoreboardManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // We only care about right-clicking a block
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.CAKE) {
            return;
        }

        Player player = event.getPlayer();
        Location spawnPoint = arenaManager.getRandomSpawnPoint();

        if (spawnPoint != null) {
            player.teleport(spawnPoint);
            player.sendMessage(ChatColor.GREEN + "You have joined the MegaWalls FFA arena!");
            scoreboardManager.setScoreboard(player);
        } else {
            player.sendMessage(ChatColor.RED + "The arena is not ready yet. No spawn points have been set.");
        }
    }
}