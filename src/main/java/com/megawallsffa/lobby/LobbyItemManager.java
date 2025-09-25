package com.megawallsffa.lobby;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class LobbyItemManager {

    public void giveLobbyItems(Player player) {
        player.getInventory().clear();

        // Class Selector Compass
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName(ChatColor.GREEN + "Class Selector" + ChatColor.GRAY + " (Right Click)");
        compassMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right-click to choose your class!"));
        compass.setItemMeta(compassMeta);

        // Shop Emerald
        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName(ChatColor.GREEN + "Shop" + ChatColor.GRAY + " (Right Click)");
        emeraldMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right-click to browse the shop!"));
        emerald.setItemMeta(emeraldMeta);

        player.getInventory().setItem(0, compass);
        player.getInventory().setItem(4, emerald);
        player.updateInventory();
    }

    public void clearLobbyItems(Player player) {
        player.getInventory().clear();
    }
}