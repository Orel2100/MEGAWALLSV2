package com.megawallsffa.gui;

import com.megawallsffa.MegaWallsFFA;
import com.megawallsffa.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitGui {

    private final MegaWallsFFA plugin;

    public KitGui(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        List<Kit> kits = new ArrayList<>(plugin.getKitManager().getKits().values());
        int size = (int) Math.ceil(kits.size() / 9.0) * 9;
        Inventory gui = Bukkit.createInventory(null, size, "Select a Kit");

        for (Kit kit : kits) {
            ItemStack item = kit.getWeapon(0);
            if (item == null) {
                item = new ItemStack(Material.STONE_SWORD);
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§a" + kit.getName());
            List<String> lore = new ArrayList<>();
            lore.add("§7Click to select this kit.");
            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
    }
}