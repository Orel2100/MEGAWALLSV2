package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

    private final MegaWallsFFA plugin;

    public BlockListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getOreManager().isOre(event.getBlock())) {
            plugin.getOreManager().handleOreMine(event.getPlayer(), event.getBlock());
            event.setCancelled(true);
        }
    }
}