package io.github.Graphic.Model.enums;

public enum HeroType {
    Shana("Shana", "assets/hero/Shana", 4, 4, 100),
    Diamond("Diamond", "assets/hero/Diamond", 7, 1, 100),
    Scarlet("Scarlet", "assets/hero/Scarlet", 3, 5, 100),
    Lilith("Lilith", "assets/hero/Lilith", 5, 3, 100),
    Dasher("Dasher", "assets/hero/Dasher", 2, 10, 100)
    ;

    private final String name;
    private final String assetFolderPath; // assets/hero/Dasher|Lilith... (animated)
    private final int baseSpeed;
    private final int baseLife;
    private final int maxHP;

    HeroType(String name, String assetFolderPath, int baseSpeed, int baseLife, int maxHP) {
        this.name = name;
        this.assetFolderPath = assetFolderPath;
        this.baseSpeed = baseSpeed;
        this.baseLife = baseLife;
        this.maxHP = maxHP;
    }

    public String getAssetFolderPath() {
        return assetFolderPath;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseLife() {
        return baseLife;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }
}
