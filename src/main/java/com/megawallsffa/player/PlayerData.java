package com.megawallsffa.player;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private int coins;
    private int kills;
    private int deaths;
    private int killStreak;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.coins = 0;
        this.kills = 0;
        this.deaths = 0;
        this.killStreak = 0;
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
}