package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import org.bukkit.ChatColor;
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

        // Check if the action is within the defined arena boundaries
        if (arenaManager.isWithinArena(block.getLocation())) {
            if (block.getType() == Material.IRON_ORE) {
                // The block is iron ore, handle the custom respawn logic
                event.setDropItems(false); // Prevent default drops

                // Get the respawn delay from the config
                long respawnDelay = plugin.getConfig().getLong("ore.respawn-delay", 600L);

                // Immediately replace the ore with a temporary block
                block.setType(Material.STONE);

                // Schedule the ore to respawn later
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.IRON_ORE);
                    }
                }.runTaskLater(plugin, respawnDelay);

                // TODO: Add coin reward logic here in a future step.

            } else {
                // The block is not iron ore, cancel the break event
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can only break Iron Ore in the arena!");
            }
        }
    }
}