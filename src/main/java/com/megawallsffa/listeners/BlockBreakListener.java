package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockBreakListener implements Listener {

    private final MegaWallsFFA plugin;
    private final ArenaManager arenaManager;

    public BlockBreakListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (arenaManager.isWithinArena(player.getLocation())) {
            if (block.getType() == Material.IRON_ORE) {
                // Handle ore respawning
                event.setDropItems(false); // Don't drop the raw ore

                long respawnDelay = plugin.getConfig().getLong("ore.respawn-delay", 600L); // 30 seconds default

                block.setType(Material.STONE); // Temporary block
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.IRON_ORE); // Respawn ore
                    }
                }.runTaskLater(plugin, respawnDelay);

            } else {
                event.setCancelled(true);
                player.sendMessage("You can only break iron ore in the arena!");
            }
        }
    }
}