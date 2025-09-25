package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class LobbyListener implements Listener {

    private final MegaWallsFFA plugin;
    private final ArenaManager arenaManager;
    private final Random random = new Random();

    public LobbyListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && clickedBlock.getType() == Material.CAKE) {
            if (arenaManager.isArenaDefined()) {
                teleportToArena(player);
            } else {
                player.sendMessage("The arena has not been defined yet!");
            }
        }
    }

    private void teleportToArena(Player player) {
        Location pos1 = arenaManager.getPos1();
        Location pos2 = arenaManager.getPos2();

        double minX = Math.min(pos1.getX(), pos2.getX());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        double x = minX + (maxX - minX) * random.nextDouble();
        double z = minZ + (maxZ - minZ) * random.nextDouble();

        // Assuming the world is the same
        Location teleportLocation = new Location(pos1.getWorld(), x, 0, z);

        // Find the highest solid block to avoid spawning underground
        teleportLocation.setY(teleportLocation.getWorld().getHighestBlockYAt(teleportLocation) + 1);

        player.teleport(teleportLocation);
        player.sendMessage("You have been teleported to the arena!");
    }
}