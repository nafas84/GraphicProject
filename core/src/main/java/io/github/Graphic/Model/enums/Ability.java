package io.github.Graphic.Model.enums;

import io.github.Graphic.Model.App;

import java.util.Random;

public enum Ability {
    Vitality("Vitality", App.getLanguage("ability.vitality"), -1, "assets/Ability/Vitality.png"),
    Damager("Damager",App.getLanguage("ability.damager"), 10, "assets/Ability/Damager.png"),
    Procrease("Procrease",App.getLanguage("ability.procrease"), -1, "assets/Ability/Procrease.png"),
    Amocrease("Amocrease",App.getLanguage("ability.amocrease"), -1, "assets/Ability/Amocrease.png"),
    Speedy("Speedy",App.getLanguage("ability.speedy"), 10, "assets/Ability/Speedy.png"),
    ;

    private final String name;
    private final String description;
    private final float time;
    private final String path;

    Ability(String name, String description, float time, String path) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.path = path;
    }

    public static Ability getRandom() {
        Random random = new Random();
        Ability[] abilities = values();
        return abilities[random.nextInt(abilities.length)];
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getTime() {
        return time;
    }

    public String getPath() {
        return path;
    }
}
