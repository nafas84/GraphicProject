package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import io.github.Graphic.Controller.GameController;
import io.github.Graphic.Model.enums.Ability;

import java.io.File;
import java.util.HashMap;

public class Player {
    private final int id;

    private final Hero hero;
    private final Weapon weapon;

    private float x;
    private float y;

    private float time = 0;

    private boolean isInvincible = false;
    private float invincibleTimeRemaining = 0f;

    private int xp;
    private int hp;
    private int life;
    private int kills;
    private int level;

    private final HashMap<Ability, Float> abilities = new HashMap<>();

    private boolean hit = false;
    private float shieldHitTime = 0f;

    public Player(Hero hero, Weapon weapon, int id) {
        this.id = id;

        this.hero = hero;
        this.weapon = weapon;

        this.life = hero.getType().getBaseLife();

        this.x = (float) Gdx.graphics.getWidth() / 2;
        this.y = (float) Gdx.graphics.getWidth() / 2;
        this.xp  = 0;
        this.hp = 100;
        this.kills = 0;
        this.level = 1;
    }

    public Player(Hero hero, Weapon weapon,
                  int id, float x, float y,
                int xp, int hp, int kill, int level) {
        this.id = id;

        this.hero = hero;
        this.weapon = weapon;

        this.life = hero.getType().getBaseLife();

        this.x = x;
        this.y = y;
        this.xp  = xp;
        this.hp = hp;
        this.kills = kill;
        this.level = level;
    }

    // Update methods:
    public void updateLife(int life) {
        this.life += life;
    }

    public void updateKills(int kills) {
        this.kills += kills;
    }

    public void updateLevel() {
        if (App.isIsSfx())
            App.getSoundLevelUp().play();
        this.level += 1;
        Ability ability = Ability.getRandom();
        abilities.put(ability, ability.getTime());

        GameController.setWarning(App.getLanguage("game.ability") +
            " " + ability.getName() + " " + ability.getDescription());
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

    public void playerHit() {
        hit = true;
        shieldHitTime = 0f;
    }

    public float getShieldHitTime() {
        return shieldHitTime;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setShieldHitTime(float shieldHitTime) {
        this.shieldHitTime = shieldHitTime;
    }

    // getter for  types:(for ability)
    public int getMaxHP() {
        int zarib = 0;
        if (abilities.containsKey(Ability.Vitality))
            zarib = 10;

        return this.hero.getMaxHP() + zarib;
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
        int zarib = 1;
        if (abilities.containsKey(Ability.Speedy))
            zarib = 2;
        return this.hero.getSpeed() * zarib;
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

    public HashMap<Ability, Float> getAbilities() {
        return abilities;
    }

    public String getAbilitiesName() {
        StringBuilder result = new StringBuilder();
        for (Ability ability: abilities.keySet()) {
            result.append(ability.getName()).append(" ");
        }
        return result.toString();
    }
}
