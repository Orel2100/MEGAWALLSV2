package com.megawallsffa.commands;

import com.megawallsffa.arena.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaCommand implements CommandExecutor {

    private final ArenaManager arenaManager;

    public ArenaCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("megawallsffa.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "pos1":
                arenaManager.setPos1(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Position 1 for the arena boundary set to your location.");
                break;
            case "pos2":
                arenaManager.setPos2(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Position 2 for the arena boundary set to your location.");
                break;
            case "addspawn":
                arenaManager.addSpawnPoint(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "New spawn point added at your location.");
                break;
            case "delspawn":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /arena delspawn <index>");
                    return true;
                }
                try {
                    int index = Integer.parseInt(args[1]) - 1; // User-friendly 1-based index
                    if (arenaManager.removeSpawnPoint(index)) {
                        player.sendMessage(ChatColor.GREEN + "Spawn point " + (index + 1) + " removed.");
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid spawn point index.");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid index. Please use a number.");
                }
                break;
            case "listspawns":
                List<Location> spawns = arenaManager.getSpawnPoints();
                if (spawns.isEmpty()) {
                    player.sendMessage(ChatColor.YELLOW + "No spawn points have been set.");
                } else {
                    player.sendMessage(ChatColor.YELLOW + "--- Spawn Points ---");
                    for (int i = 0; i < spawns.size(); i++) {
                        Location loc = spawns.get(i);
                        player.sendMessage(String.format("%d. World: %s, X: %.2f, Y: %.2f, Z: %.2f",
                                i + 1, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ()));
                    }
                }
                break;
            default:
                sendHelpMessage(player);
                break;
        }
        return true;
    }

    private void sendHelpMessage(CommandSender player) {
        player.sendMessage(ChatColor.GOLD + "--- MegaWallsFFA Admin Commands ---");
        player.sendMessage(ChatColor.YELLOW + "/arena pos1" + ChatColor.GRAY + " - Set arena corner 1");
        player.sendMessage(ChatColor.YELLOW + "/arena pos2" + ChatColor.GRAY + " - Set arena corner 2");
        player.sendMessage(ChatColor.YELLOW + "/arena addspawn" + ChatColor.GRAY + " - Add a spawn point");
        player.sendMessage(ChatColor.YELLOW + "/arena delspawn <index>" + ChatColor.GRAY + " - Remove a spawn point");
        player.sendMessage(ChatColor.YELLOW + "/arena listspawns" + ChatColor.GRAY + " - List all spawn points");
    }
}