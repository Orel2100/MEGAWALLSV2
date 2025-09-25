package com.megawallsffa.classes.abilities;

import org.bukkit.entity.Player;

public interface Ability {

    /**
     * @return The name of the ability.
     */
    String getName();

    /**
     * @return A brief description of what the ability does.
     */
    String getDescription();

    /**
     * @return The amount of energy required to use this ability.
     */
    int getEnergyCost();

    /**
     * Executes the ability for the given player.
     * @param player The player using the ability.
     * @return true if the ability was used successfully, false otherwise.
     */
    boolean execute(Player player);
}