package com.megawallsffa.player;

import com.megawallsffa.kit.Kit;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private Kit kit;
    private int energy;
    private int coins;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.energy = 0;
        this.coins = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int amount) {
        this.energy = Math.min(100, this.energy + amount);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getKills() {
        return 0;
    }

    public int getDeaths() {
        return 0;
    }

    public int getKillStreak() {
        return 0;
    }
}