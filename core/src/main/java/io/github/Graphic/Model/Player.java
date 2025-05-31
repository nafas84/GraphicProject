package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileReader;

public class Player {
    private final int id;

    private final Hero hero;
    private final Weapon weapon;

    private float x = (float) Gdx.graphics.getWidth() / 2;
    private float y =  (float) Gdx.graphics.getWidth() / 2;

    private float time = 0;

    private boolean isInvincible = false;
    private float invincibleTimeRemaining = 0f;

    private int xp = 0;
    private int hp = 100;
    private int life;
    private int kills = 0;
    private int level = 1;

    public Player(Hero hero, Weapon weapon, int id) {
        this.id = id;

        this.hero = hero;
        this.weapon = weapon;

        this.life = hero.getType().getBaseLife();
    }

    // Update methods:
    public void updateLife(int life) {
        this.life += life;
    }

    public void updateKills(int kills) {
        this.kills += kills;
    }

    public void updateLevel() {
        this.level += 1;
        //TODO: set random ability
    }

    public void updateHp(int hp) {
        this.hp += hp;
        if (this.hp <= 0) {
            this.hp = this.getMaxHP();
            updateLife(-1);
        }
    }

    public void updateXp(int xp) {
        this.xp += xp;
        if (this.xp >= getXpNeedLevelUp()) {
            this.xp = 0;
            updateLevel();
        }
    }

    public int getXpNeedLevelUp() {
        return this.level * 20;
    }
    // getter for  types:(for ability)
    public int getMaxHP() {
        //TODO ability
        return this.hero.getMaxHP();
    }
     public int getId() {
        return id;
    }

    public boolean isInvincible() {
        return isInvincible;
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

    public int getKills() {
        return kills;
    }

    public int getLevel() {
        return level;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public float getSpeed() {
        return this.hero.getSpeed();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isIdle() {
        return this.hero.isIdle();
    }

    public void setIdle(boolean idle) {
        this.hero.setIdle(idle);
    }

    public Hero getHero() {
        return hero;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setInvincibleTimeRemaining(float invincibleTimeRemaining) {
        this.invincibleTimeRemaining = invincibleTimeRemaining;
    }

    public void updateInvincibleTimeRemaining(float invincibleTimeRemaining) {
        this.invincibleTimeRemaining += invincibleTimeRemaining;
    }

    public float getInvincibleTimeRemaining() {
        return invincibleTimeRemaining;
    }

    public String getUsername() {
        try {
            File folder = new File("data/users/" + id);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

            if (files == null || files.length == 0) return null;

            String filename = files[0].getName();
            return filename.substring(0, filename.lastIndexOf('.'));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
