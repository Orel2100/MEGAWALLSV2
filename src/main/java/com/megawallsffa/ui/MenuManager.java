package com.megawallsffa.ui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MenuManager {

    public void openClassSelector(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Class Selector");

        // Placeholder for class items
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = placeholder.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Coming Soon!");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "This class is not yet available."));
        placeholder.setItemMeta(meta);

        inv.setItem(11, placeholder); // Example slot for Skeleton
        inv.setItem(13, placeholder); // Example slot for Zombie
        inv.setItem(15, placeholder); // Example slot for Creeper

        player.openInventory(inv);
    }

    public void openShopMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Shop");

        // Placeholder for shop categories
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = placeholder.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Coming Soon!");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "This shop category is not yet available."));
        placeholder.setItemMeta(meta);

        inv.setItem(11, placeholder); // Example slot for Class Upgrades
        inv.setItem(13, placeholder); // Example slot for Cosmetics
        inv.setItem(15, placeholder); // Example slot for Mythic Favor

        player.openInventory(inv);
    }
}