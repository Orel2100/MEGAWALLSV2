package com.megawallsffa.arena;

import com.megawallsffa.MegaWallsFFA;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaManager {

    private final MegaWallsFFA plugin;
    private final Random random = new Random();
    private Location pos1;
    private Location pos2;
    private final List<Location> spawnPoints = new ArrayList<>();

    public ArenaManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
        loadArenaData();
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
        plugin.getConfig().set("arena.pos1", pos1);
        plugin.saveConfig();
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
        plugin.getConfig().set("arena.pos2", pos2);
        plugin.saveConfig();
    }

    public void addSpawnPoint(Location location) {
        spawnPoints.add(location);
        plugin.getConfig().set("arena.spawns", spawnPoints);
        plugin.saveConfig();
    }

    public boolean removeSpawnPoint(int index) {
        if (index >= 0 && index < spawnPoints.size()) {
            spawnPoints.remove(index);
            plugin.getConfig().set("arena.spawns", spawnPoints);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public List<Location> getSpawnPoints() {
        return new ArrayList<>(spawnPoints);
    }

    public Location getRandomSpawnPoint() {
        if (spawnPoints.isEmpty()) {
            return null;
        }
        return spawnPoints.get(random.nextInt(spawnPoints.size()));
    }

    public boolean isWithinArena(Location location) {
        if (pos1 == null || pos2 == null) {
            return false;
        }
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return location.getX() >= minX && location.getX() <= maxX &&
               location.getY() >= minY && location.getY() <= maxY &&
               location.getZ() >= minZ && location.getZ() <= maxZ;
    }

    @SuppressWarnings("unchecked")
    private void loadArenaData() {
        FileConfiguration config = plugin.getConfig();
        plugin.saveDefaultConfig(); // Ensures config.yml exists

        if (config.contains("arena.pos1")) {
            pos1 = new Location(
                    plugin.getServer().getWorld(config.getString("arena.pos1.world")),
                    config.getDouble("arena.pos1.x"),
                    config.getDouble("arena.pos1.y"),
                    config.getDouble("arena.pos1.z")
            );
        }
        if (config.contains("arena.pos2")) {
            pos2 = new Location(
                    plugin.getServer().getWorld(config.getString("arena.pos2.world")),
                    config.getDouble("arena.pos2.x"),
                    config.getDouble("arena.pos2.y"),
                    config.getDouble("arena.pos2.z")
            );
        }
        if (config.contains("arena.spawns")) {
            // Bukkit's config API saves a list of locations directly
            List<?> rawList = config.getList("arena.spawns");
            if (rawList != null) {
                for (Object obj : rawList) {
                    if (obj instanceof Location) {
                        spawnPoints.add((Location) obj);
                    }
                }
            }
        }
    }
}