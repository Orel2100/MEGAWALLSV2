package com.megawallsffa.commands;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MegaWallsCommand implements CommandExecutor {

    private final LobbyManager lobbyManager;

    public MegaWallsCommand(MegaWallsFFA plugin) {
        this.lobbyManager = plugin.getLobbyManager();
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

        if (args.length > 0 && args[0].equalsIgnoreCase("setlobby")) {
            lobbyManager.setLobbyLocation(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Lobby location set to your current position.");
            return true;
        }

        sendHelpMessage(player);
        return true;
    }

    private void sendHelpMessage(CommandSender player) {
        player.sendMessage(ChatColor.GOLD + "--- MegaWallsFFA General Commands ---");
        player.sendMessage(ChatColor.YELLOW + "/megawalls setlobby" + ChatColor.GRAY + " - Set the lobby spawn point.");
    }
}