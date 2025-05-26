package io.github.Graphic.Model;

public enum Ability {
    Vitality("Vitality",App.getLanguage("ability.vitality"), -1),
    Vitality("Vitality",App.getLanguage("ability.vitality"), -1),
    Vitality("Vitality",App.getLanguage("ability.vitality"), -1),
    Vitality("Vitality",App.getLanguage("ability.vitality"), -1),
    Vitality("Vitality",App.getLanguage("ability.vitality"), -1),

    ;

    private final String name;
    private final String description;
    private final int time;

    Ability(String name, String description, int time) {
        this.name = name;
        this.description = description;
        this.time = time;
    }
}
