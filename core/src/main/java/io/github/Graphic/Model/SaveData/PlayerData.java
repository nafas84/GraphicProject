package io.github.Graphic.Model.SaveData;

import io.github.Graphic.Model.enums.HeroType;
import io.github.Graphic.Model.enums.WeaponType;

public class PlayerData {
    private final int id;
    private final HeroType heroType;
    private final WeaponType weaponType;

    private final float x, y;

    private final int xp, hp, life, kill, level;

    public PlayerData(int id, HeroType heroType,
                      WeaponType weaponType,
                      float x, float y,
                      int xp, int hp,
                      int life, int kill,
                      int level) {
        this.id = id;
        this.heroType = heroType;
        this.weaponType = weaponType;

        this.x = x;
        this.y = y;
        this.xp = xp;
        this.hp = hp;
        this.life = life;
        this.kill = kill;
        this.level = level;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getXp() {
        return xp;
    }

    public int getHp() {
        return hp;
    }

    public int getLife() {
        return life;
    }

    public int getKill() {
        return kill;
    }

    public int getLevel() {
        return level;
    }

    public int getId() {
        return id;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
