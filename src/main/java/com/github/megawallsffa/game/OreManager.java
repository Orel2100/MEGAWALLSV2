package com.github.megawallsffa.game;

import com.github.megawallsffa.MegaWallsFFA;
import com.github.megawallsffa.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreManager {

    private final MegaWallsFFA plugin;
    private final Map<Location, Integer> oreTiers = new HashMap<>();
    private final Map<Location, Long> respawnTimes = new HashMap<>();
    private final Map<Integer, Integer> coinRewards = new HashMap<>();
    private final int respawnTimer;

    public OreManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.respawnTimer = config.getInt("ores.respawn-timer", 45);
        loadOres();
        loadCoinRewards();
    }

    private void loadOres() {
        FileConfiguration config = plugin.getConfig();
        List<String> oreLocations = config.getStringList("ores.locations");
        for (String locString : oreLocations) {
            String[] parts = locString.split(";");
            if (parts.length == 5) {
                String world = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                int tier = Integer.parseInt(parts[4]);
                oreTiers.put(new Location(Bukkit.getWorld(world), x, y, z), tier);
            }
        }
    }

    private void loadCoinRewards() {
        coinRewards.put(1, 20);
        coinRewards.put(2, 40);
        coinRewards.put(3, 80);
        coinRewards.put(4, 160);
        coinRewards.put(5, 320);
    }

    public boolean isOre(Block block) {
        return block.getType() == Material.IRON_ORE && oreTiers.containsKey(block.getLocation());
    }

    public void handleOreMine(Player player, Block block) {
        Location loc = block.getLocation();
        if (respawnTimes.containsKey(loc) && System.currentTimeMillis() < respawnTimes.get(loc)) {
            player.sendMessage("This ore has already been mined recently.");
            return;
        }

        int tier = oreTiers.get(loc);
        int coins = coinRewards.get(tier);
        PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
        playerData.addCoins(coins);
        player.sendMessage("You mined a tier " + tier + " ore and received " + coins + " coins. You now have " + playerData.getCoins() + " coins.");

        block.setType(Material.BEDROCK);
        respawnTimes.put(loc, System.currentTimeMillis() + (long) respawnTimer * 1000);

        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.IRON_ORE);
            }
        }.runTaskLater(plugin, (long) respawnTimer * 20);
    }
}