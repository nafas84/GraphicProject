package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;

public class Player {
    private final int id;

    private final Hero hero;
    private final Weapon weapon;

    private float x = (float) Gdx.graphics.getWidth() / 2;
    private float y =  (float) Gdx.graphics.getWidth() / 2;

    private CollisionRect rect ;
    private float time = 0;

    private boolean isInvincible = false;
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

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
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
}
