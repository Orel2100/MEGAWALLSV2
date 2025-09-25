package com.github.megawallsffa.listeners;

import com.github.megawallsffa.MegaWallsFFA;
import com.github.megawallsffa.kit.Kit;
import com.github.megawallsffa.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {

    private final MegaWallsFFA plugin;

    public GuiListener(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Select a Kit")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                Player player = (Player) event.getWhoClicked();
                String kitName = event.getCurrentItem().getItemMeta().getDisplayName().substring(2);
                Kit kit = plugin.getKitManager().getKit(kitName);
                if (kit != null) {
                    PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
                    playerData.setKit(kit);
                    player.sendMessage("You have selected the " + kit.getName() + " kit.");
                    player.closeInventory();
                }
            }
        }
    }
}