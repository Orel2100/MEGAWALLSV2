package com.megawallsffa.classes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

    private final ItemStack[] armor;
    private final ItemStack[] inventory;

    public Kit(ItemStack[] armor, ItemStack[] inventory) {
        this.armor = armor;
        this.inventory = inventory;
    }

    /**
     * Applies this kit to a player, clearing their inventory first.
     * @param player The player to apply the kit to.
     */
    public void apply(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(armor);
        player.getInventory().setContents(inventory);
        player.updateInventory();
    }
}