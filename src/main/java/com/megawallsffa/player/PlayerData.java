package com.megawallsffa.player;

import com.megawallsffa.classes.MegaWallsClass;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private int coins;
    private int kills;
    private int deaths;
    private int killStreak;
    private MegaWallsClass selectedClass;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.coins = 0;
        this.kills = 0;
        this.deaths = 0;
        this.killStreak = 0;
        this.selectedClass = null; // Default to no class
    }

    // Getters
    public UUID getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public MegaWallsClass getSelectedClass() {
        return selectedClass;
    }

    // Setters
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void incrementKills() {
        this.kills++;
        this.killStreak++;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void incrementDeaths() {
        this.deaths++;
    }

    public void resetKillStreak() {
        this.killStreak = 0;
    }

    public void setSelectedClass(MegaWallsClass selectedClass) {
        this.selectedClass = selectedClass;
    }
}