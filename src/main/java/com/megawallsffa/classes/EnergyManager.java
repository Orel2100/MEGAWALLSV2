package com.megawallsffa.classes;

import com.megawallsffa.MegaWallsFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnergyManager implements Listener {

    private final MegaWallsFFA plugin;
    private final Map<UUID, Integer> playerEnergy = new HashMap<>();
    private final Map<UUID, BossBar> energyBars = new HashMap<>();

    public EnergyManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public int getEnergy(Player player) {
        return playerEnergy.getOrDefault(player.getUniqueId(), 0);
    }

    public void addEnergy(Player player, int amount) {
        int currentEnergy = getEnergy(player);
        int newEnergy = Math.min(100, currentEnergy + amount);
        playerEnergy.put(player.getUniqueId(), newEnergy);
        updateEnergyBar(player, newEnergy);
    }

    public boolean useEnergy(Player player, int amount) {
        int currentEnergy = getEnergy(player);
        if (currentEnergy >= amount) {
            playerEnergy.put(player.getUniqueId(), currentEnergy - amount);
            updateEnergyBar(player, currentEnergy - amount);
            return true;
        }
        return false;
    }

    public void createEnergyBar(Player player) {
        BossBar bossBar = Bukkit.createBossBar(
            ChatColor.YELLOW + "Energy: " + ChatColor.GREEN + "0/100",
            BarColor.BLUE,
            BarStyle.SOLID
        );
        bossBar.addPlayer(player);
        energyBars.put(player.getUniqueId(), bossBar);
        updateEnergyBar(player, getEnergy(player));
    }

    public void removeEnergyBar(Player player) {
        BossBar bossBar = energyBars.remove(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removeAll();
        }
        playerEnergy.remove(player.getUniqueId());
    }

    private void updateEnergyBar(Player player, int energy) {
        BossBar bossBar = energyBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.setProgress(energy / 100.0);
            bossBar.setTitle(ChatColor.YELLOW + "Energy: " + ChatColor.GREEN + energy + "/100");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            // Check if attacker is in the arena
            if (plugin.getArenaManager().isWithinArena(attacker.getLocation())) {
                addEnergy(attacker, 5); // Grant 5 energy per hit
            }
        }
    }
}