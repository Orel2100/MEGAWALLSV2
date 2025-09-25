package com.megawallsffa.classes;

import com.megawallsffa.classes.abilities.Ability;

public abstract class MegaWallsClass {

    private final String name;
    private final String description;
    private final Ability mainAbility;

    public MegaWallsClass(String name, String description, Ability mainAbility) {
        this.name = name;
        this.description = description;
        this.mainAbility = mainAbility;
    }

    /**
     * @return The name of the class.
     */
    public String getName() {
        return name;
    }

    /**
     * @return A brief description of the class.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The main ability of the class.
     */
    public Ability getMainAbility() {
        return mainAbility;
    }

    /**
     * Gets the kit for this class at a specific tier.
     * @param tier The tier of the kit (1-5).
     * @return The Kit object for the specified tier.
     */
    public abstract Kit getKit(int tier);
}