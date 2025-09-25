package com.github.megawallsffa.listeners;

import com.github.megawallsffa.MegaWallsFFA;
import com.github.megawallsffa.kit.Kit;
import com.github.megawallsffa.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DeathListener implements Listener {

    private final MegaWallsFFA plugin;

    public DeathListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getDrops().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                FileConfiguration config = plugin.getConfig();
                Location arenaSpawn = new Location(
                        plugin.getServer().getWorld(config.getString("arena.spawn.world", "world")),
                        config.getDouble("arena.spawn.x", 0.5),
                        config.getDouble("arena.spawn.y", 101),
                        config.getDouble("arena.spawn.z", 0.5),
                        (float) config.getDouble("arena.spawn.yaw", 0),
                        (float) config.getDouble("arena.spawn.pitch", 0)
                );
                player.teleport(arenaSpawn);

                PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
                Kit kit = playerData.getKit();
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
            }
        }.runTaskLater(plugin, 1L);
    }
}