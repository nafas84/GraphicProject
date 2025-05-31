package io.github.Graphic.Model.enums;

public enum MonsterType {
    Tree("Tree", "assets/monster/treeMonster", 10,
        false, false, false, 1f, 0),

    Lamprey("Lamprey", "assets/monster/lampreyMonster",
        25, false, false, true, 0.2f, 100),

    EyeBat("EyeBat", "assets/monster/eyebatMonster", 50,
        false, true, true, 0.15f, 90),

    Yog("Yog", "assets/monster/yogMonster", 400,
        true, false, true, 0.1f, 120)
    ;

    private final String name;
    private final String assetFolderPath;
    private final int hp;
    private final boolean canDash;
    private final boolean canShoot;
    private final boolean canWalk;

    private final float frameDuration;
    private final int speed; //pixel/s

    MonsterType(String name, String assetFolderPath, int hp, boolean canDash, boolean canShoot, boolean canWalk, float frameDuration, int speed) {
        this.name = name;
        this.assetFolderPath = assetFolderPath;
        this.hp = hp;
        this.canDash = canDash;
        this.canShoot = canShoot;
        this.canWalk = canWalk;
        this.frameDuration = frameDuration;
        this.speed = speed;
    }

    public String getAssetFolderPath() {
        return assetFolderPath;
    }

    public int getHp() {
        return hp;
    }

    public boolean isCanDash() {
        return canDash;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public boolean isCanWalk() {
        return canWalk;
    }

    public String getName() {
        return name;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public int getSpeed() {
        return speed;
    }
}
