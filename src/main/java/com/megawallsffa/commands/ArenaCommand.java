package com.megawallsffa.commands;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.arena.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if (args.length != 1) {
            sender.sendMessage("Usage: /arena <pos1|pos2>");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("megawallsffa.admin")) {
            player.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (args[0].equalsIgnoreCase("pos1")) {
            arenaManager.setPos1(player.getLocation());
            player.sendMessage("Position 1 set to your current location.");
        } else if (args[0].equalsIgnoreCase("pos2")) {
            arenaManager.setPos2(player.getLocation());
            player.sendMessage("Position 2 set to your current location.");
        } else {
            sender.sendMessage("Usage: /arena <pos1|pos2>");
        }

        return true;
    }
}