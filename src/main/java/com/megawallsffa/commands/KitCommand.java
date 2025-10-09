package com.megawallsffa.commands;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.gui.KitGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private final MegaWallsFFA plugin;
    private final KitGui kitGui;

    public KitCommand(MegaWallsFFA plugin) {
        this.plugin = plugin;
        this.kitGui = new KitGui(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        kitGui.open(player);
        return true;
    }
}