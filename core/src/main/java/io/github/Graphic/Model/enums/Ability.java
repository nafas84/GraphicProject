package io.github.Graphic.Model.enums;

import io.github.Graphic.Model.App;

public enum Ability {
    Vitality("Vitality", App.getLanguage("ability.vitality"), -1),
    Damager("Damager",App.getLanguage("ability.damager"), -1),
    Procrease("Procrease",App.getLanguage("ability.procrease"), -1),
    Amocrease("Amocrease",App.getLanguage("ability.amocrease"), -1),
    Speedy("Speedy",App.getLanguage("ability.speedy"), -1),
    ;

    private final String name;
    private final String description;
    private final int time;

    Ability(String name, String description, int time) {
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public Ability getRandom() {

    }
}
