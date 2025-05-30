package io.github.Graphic.Model.enums;

public enum WeaponType {
    Revolver("Revolver", "assets/weapon/RevolverStill/RevolverStill.png", 20, 1, 1, 6),
    Shotgun("Shotgun", "assets/weapon/ShotgunStill/ShotgunStill.png", 10, 4, 1, 2),
    SMG("SMG", "assets/weapon/SMGStill/SMGStill.png", 8, 1, 2, 24)
    ;

    private final String name;
    private final String assetPath; // assets/weapon/RevolverStill/Revolver.png

    private final int damage;
    private final int projectile;
    private final int timeReload;
    private final int ammoMax;

    WeaponType(String name, String assetPath, int damage, int projectile, int timeReload, int ammoMax) {
        this.name = name;
        this.assetPath = assetPath;
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.ammoMax = ammoMax;
    }

    public String getName() {
        return name;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getAmmoMax() {
        return ammoMax;
    }
}
