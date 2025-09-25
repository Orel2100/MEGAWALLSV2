package com.github.megawallsffa.listeners;

import com.github.megawallsffa.MegaWallsFFA;
import com.github.megawallsffa.player.PlayerData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final MegaWallsFFA plugin;

    public DamageListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player damager;
            if (event.getDamager() instanceof Player) {
                damager = (Player) event.getDamager();
            } else if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    damager = (Player) arrow.getShooter();
                } else {
                    return;
                }
            } else {
                return;
            }

            PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(damager.getUniqueId());
            if (playerData.getKit() != null) {
                int energy = (event.getDamager() instanceof Arrow) ? playerData.getKit().getEphBow() : playerData.getKit().getEphMelee();
                playerData.addEnergy(energy);
                damager.sendMessage("You gained " + energy + " energy. Total: " + playerData.getEnergy());
            }
        }
    }
}