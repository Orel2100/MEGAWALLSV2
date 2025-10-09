package com.megawallsffa.listeners;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.lobby.LobbyManager;
import com.megawallsffa.ui.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyItemListener implements Listener {

    private final LobbyManager lobbyManager;
    private final MenuManager menuManager;

    public LobbyItemListener(MegaWallsFFA plugin) {
        this.lobbyManager = plugin.getLobbyManager();
        this.menuManager = plugin.getMenuManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        // Only works in the lobby
        if (lobbyManager.getLobbyLocation() == null || !player.getWorld().equals(lobbyManager.getLobbyLocation().getWorld())) {
             // A simple check to see if the player is in the lobby world.
             // A more precise region check could be added later if needed.
            return;
        }

        if (item.getType() == Material.COMPASS && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getDisplayName().equals(ChatColor.GREEN + "Class Selector" + ChatColor.GRAY + " (Right Click)")) {
                menuManager.openClassSelector(player);
                event.setCancelled(true);
            }
        } else if (item.getType() == Material.EMERALD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getDisplayName().equals(ChatColor.GREEN + "Shop" + ChatColor.GRAY + " (Right Click)")) {
                menuManager.openShopMenu(player);
                event.setCancelled(true);
            }
        }
    }
}