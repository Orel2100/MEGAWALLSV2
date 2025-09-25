package com.github.megawallsffa.kit;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class Kit {
    private final String name;
    private final int ephMelee;
    private final int ephBow;
    private final Map<Integer, List<ItemStack>> armorTiers;
    private final Map<Integer, ItemStack> weaponTiers;
    private final Map<Integer, List<ItemStack>> inventoryTiers;


    public Kit(String name, int ephMelee, int ephBow, Map<Integer, List<ItemStack>> armorTiers, Map<Integer, ItemStack> weaponTiers, Map<Integer, List<ItemStack>> inventoryTiers) {
        this.name = name;
        this.ephMelee = ephMelee;
        this.ephBow = ephBow;
        this.armorTiers = armorTiers;
        this.weaponTiers = weaponTiers;
        this.inventoryTiers = inventoryTiers;
    }

    public String getName() {
        return name;
    }

    public int getEphMelee() {
        return ephMelee;
    }

    public int getEphBow() {
        return ephBow;
    }

    public List<ItemStack> getArmor(int tier) {
        return armorTiers.get(tier);
    }

    public ItemStack getWeapon(int tier) {
        return weaponTiers.get(tier);
    }

    public List<ItemStack> getInventory(int tier) {
        return inventoryTiers.get(tier);
    }
}