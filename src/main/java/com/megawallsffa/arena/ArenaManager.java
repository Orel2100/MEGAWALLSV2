package com.megawallsffa.arena;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import com.megawallsffa.MegaWallsFFA;

public class ArenaManager {

    private final MegaWallsFFA plugin;
    private Location pos1;
    private Location pos2;

    public ArenaManager(MegaWallsFFA plugin) {
        this.plugin = plugin;
        loadArena();
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

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean isArenaDefined() {
        return pos1 != null && pos2 != null;
    }

    public boolean isWithinArena(Location location) {
        if (!isArenaDefined()) {
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

    private void loadArena() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("arena.pos1")) {
            pos1 = config.getLocation("arena.pos1");
        }
        if (config.contains("arena.pos2")) {
            pos2 = config.getLocation("arena.pos2");
        }
    }
}