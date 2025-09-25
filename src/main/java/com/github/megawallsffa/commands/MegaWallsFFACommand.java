package com.github.megawallsffa.commands;

import com.github.megawallsffa.MegaWallsFFA;
import com.github.megawallsffa.kit.Kit;
import com.github.megawallsffa.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MegaWallsFFACommand implements CommandExecutor {

    private final MegaWallsFFA plugin;

    public MegaWallsFFACommand(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0 && args[0].equalsIgnoreCase("join")) {
            // Teleport player to arena
            FileConfiguration config = plugin.getConfig();
            Location arenaSpawn = new Location(
                    Bukkit.getWorld(config.getString("arena.spawn.world", "world")),
                    config.getDouble("arena.spawn.x", 0.5),
                    config.getDouble("arena.spawn.y", 101),
                    config.getDouble("arena.spawn.z", 0.5),
                    (float) config.getDouble("arena.spawn.yaw", 0),
                    (float) config.getDouble("arena.spawn.pitch", 0)
            );
            player.teleport(arenaSpawn);

            // Give player kit
            PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
            Kit kit = playerData.getKit();
            if (kit == null) {
                kit = plugin.getKitManager().getKit("cow");
                playerData.setKit(kit);
            }

            if (kit != null) {
                player.getInventory().clear();
                // Armor
                List<ItemStack> armor = kit.getArmor(0);
                player.getInventory().setArmorContents(armor.toArray(new ItemStack[0]));
                // Weapon
                player.getInventory().setItem(0, kit.getWeapon(0));
                // Other items
                List<ItemStack> items = kit.getInventory(0);
                for (ItemStack item : items) {
                    player.getInventory().addItem(item);
                }
            }
            player.sendMessage("You have joined the Mega Walls FFA arena with the " + kit.getName() + " kit!");
            return true;
        }

        return false;
    }
}