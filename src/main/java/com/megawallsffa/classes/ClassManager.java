package com.megawallsffa.classes;

import com.megawallsffa.classes.stater.Skeleton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ClassManager {

    private final Map<String, MegaWallsClass> classes = new HashMap<>();

    public ClassManager() {
        registerClasses();
    }

    private void registerClasses() {
        // In the future, more classes will be registered here
        registerClass(new Skeleton());
    }

    private void registerClass(MegaWallsClass mwClass) {
        classes.put(mwClass.getName().toLowerCase(), mwClass);
    }

    public MegaWallsClass getClass(String name) {
        return classes.get(name.toLowerCase());
    }

    public Map<String, MegaWallsClass> getClasses() {
        return new HashMap<>(classes);
    }
}

// Placeholder Skeleton class for demonstration
class Skeleton extends MegaWallsClass {
    public Skeleton() {
        super("Skeleton", "A ranged attacker.", null); // Ability will be added later
    }

    @Override
    public Kit getKit(int tier) {
        ItemStack[] armor = {
                new ItemStack(Material.LEATHER_BOOTS),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_HELMET)
        };
        ItemStack[] inventory = {
                new ItemStack(Material.WOODEN_SWORD),
                new ItemStack(Material.BOW),
                new ItemStack(Material.ARROW, 16)
        };
        return new Kit(armor, inventory);
    }
}