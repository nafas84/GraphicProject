package io.github.Graphic.Model.enums;

import io.github.Graphic.Model.App;

import java.util.Random;

public enum Ability {
    Vitality("Vitality", App.getLanguage("ability.vitality"), -1),
    Damager("Damager",App.getLanguage("ability.damager"), 10),
    Procrease("Procrease",App.getLanguage("ability.procrease"), -1),
    Amocrease("Amocrease",App.getLanguage("ability.amocrease"), -1),
    Speedy("Speedy",App.getLanguage("ability.speedy"), 10),
    ;

    private final String name;
    private final String description;
    private final float time;

    Ability(String name, String description, float time) {
        this.name = name;
        this.description = description;
        this.time = time;
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
}
