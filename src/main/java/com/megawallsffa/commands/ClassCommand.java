package com.megawallsffa.commands;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.ui.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassCommand implements CommandExecutor {

    private final MenuManager menuManager;

    public ClassCommand(MegaWallsFFA plugin) {
        this.menuManager = plugin.getMenuManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        menuManager.openClassSelector(player);
        return true;
    }
}