package com.megawallsffa.kit;

import com.megawallsffa.MegaWallsFFA;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager {

    private final MegaWallsFFA plugin;
    private final Map<String, Kit> kits = new HashMap<>();

    public KitManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
    }

    public void loadKits() {
        File kitsFolder = new File(plugin.getDataFolder(), "kits");
        if (!kitsFolder.exists()) {
            kitsFolder.mkdirs();
        }
        saveDefaultKits();

        File[] kitFiles = kitsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (kitFiles == null) {
            return;
        }

        for (File kitFile : kitFiles) {
            FileConfiguration kitConfig = YamlConfiguration.loadConfiguration(kitFile);
            String name = kitConfig.getString("name");
            int ephMelee = kitConfig.getInt("eph.melee");
            int ephBow = kitConfig.getInt("eph.bow");

            Map<Integer, List<ItemStack>> armorTiers = loadItemMap(kitConfig, "armor");
            Map<Integer, ItemStack> weaponTiers = loadWeaponMap(kitConfig, "weapon");
            Map<Integer, List<ItemStack>> inventoryTiers = loadItemMap(kitConfig, "inventory");

            Kit kit = new Kit(name, ephMelee, ephBow, armorTiers, weaponTiers, inventoryTiers);
            kits.put(name.toLowerCase(), kit);
            plugin.getLogger().info("Loaded kit: " + name);
        }
    }

    private Map<Integer, ItemStack> loadWeaponMap(FileConfiguration config, String path) {
        Map<Integer, ItemStack> tiers = new HashMap<>();
        ConfigurationSection tierSection = config.getConfigurationSection(path);
        if (tierSection == null) return tiers;
        for (String tierKey : tierSection.getKeys(false)) {
            int tier = Integer.parseInt(tierKey);
            ConfigurationSection itemSection = tierSection.getConfigurationSection(tierKey);
            if (itemSection != null) {
                ItemStack item = new ItemStack(Material.valueOf(itemSection.getString("material")), itemSection.getInt("amount", 1));
                if (itemSection.contains("enchantment")) {
                    String[] enchantment = itemSection.getString("enchantment").split(":");
                    item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.getByName(enchantment[0]), Integer.parseInt(enchantment[1]));
                }
                tiers.put(tier, item);
            }
        }
        return tiers;
    }

    private Map<Integer, List<ItemStack>> loadItemMap(FileConfiguration config, String path) {
        Map<Integer, List<ItemStack>> tiers = new HashMap<>();
        ConfigurationSection tierSection = config.getConfigurationSection(path);
        if (tierSection == null) return tiers;

        for (String tierKey : tierSection.getKeys(false)) {
            int tier = Integer.parseInt(tierKey);
            List<ItemStack> items = new ArrayList<>();
            List<?> itemList = tierSection.getList(tierKey);
            if (itemList != null) {
                for (Object itemObj : itemList) {
                    if (itemObj instanceof Map) {
                        Map<?, ?> itemMap = (Map<?, ?>) itemObj;
                        String materialName = (String) itemMap.get("material");
                        int amount = 1;
                        if (itemMap.containsKey("amount")) {
                            amount = ((Number) itemMap.get("amount")).intValue();
                        }
                        ItemStack item = new ItemStack(Material.valueOf(materialName), amount);
                        if (itemMap.containsKey("enchantment")) {
                            String[] enchantment = ((String) itemMap.get("enchantment")).split(":");
                            item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.getByName(enchantment[0]), Integer.parseInt(enchantment[1]));
                        }
                        items.add(item);
                    }
                }
            }
            tiers.put(tier, items);
        }
        return tiers;
    }

    public Kit getKit(String name) {
        return kits.get(name.toLowerCase());
    }

    public Map<String, Kit> getKits() {
        return kits;
    }

    private void saveDefaultKits() {
        String[] kitNames = {"cow", "hunter", "shark", "skeleton", "herobrine", "spider", "dreadlord", "shaman", "arcanist", "golem", "blaze", "pigman", "zombie", "squid", "moleman", "phoenix", "werewolf", "renegade", "assassin", "automaton"};
        for (String kitName : kitNames) {
            File kitFile = new File(plugin.getDataFolder(), "kits/" + kitName + ".yml");
            if (!kitFile.exists()) {
                plugin.saveResource("kits/" + kitName + ".yml", false);
            }
        }
    }
}