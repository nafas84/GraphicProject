package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Graphic.Model.enums.MonsterType;

public class Monster {
    private Sprite sprite;
    private Animation<Texture> animation;

    private Sprite deathSprite;
    private Animation<Texture> deathAnimation;
    private boolean isDying = false;
    private float deathTime = 0f;

    private float x;
    private float y;

    private int hp;

    private CollisionRect rect;

    private float time = 0;

    private final MonsterType type;

    // TIME: (Dash/Shoot)
    private float lastHandleTime = 0; // for dash(5second) and shoot(3second)
    private float dashTimeRemaining = 0f;
    private boolean isDashing = false;

    public Monster(MonsterType type, float x, float y) {
        this.type = type;
        this.hp = type.getHp();

        this.x = x;
        this.y = y;

        initialSprites();
    }

    private void initialSprites() {
        String path = this.type.getAssetFolderPath();

        sprite = new Sprite(new Texture(Gdx.files.internal(path + "/T_" + this.type.getName() + "_0.png")));
        sprite.setPosition(x, y);

        if (this.type.equals(MonsterType.Yog)) sprite.setScale(3f);
        else sprite.setScale(2f);

        int frameCounter = (this.type.equals(MonsterType.Lamprey)) ? 5 : (this.type.equals(MonsterType.Tree)) ? 3 : 4;

        Texture[] frames = new Texture[frameCounter];
        for (int i = 0; i < frameCounter; i++) {
            frames[i] = new Texture(Gdx.files.internal(path +  "/T_" + this.type.getName() + "_" + i +".png"));
        }
        animation = new Animation<>(this.type.getFrameDuration(), frames);


        // initialize death animation:
        deathSprite = new Sprite(new Texture(Gdx.files.internal("assets/etc/DeathFX/DeathFX_0.png")));

        if (this.type.equals(MonsterType.Yog)) deathSprite.setScale(4f);
        else deathSprite.setScale(3f);

        int frameCounterDeath = 4;

        Texture[] framesDeath = new Texture[frameCounterDeath];
        for (int i = 0; i < frameCounterDeath; i++) {
            framesDeath[i] = new Texture(Gdx.files.internal("assets/etc/DeathFX/DeathFX_" + i +".png"));
        }
        deathAnimation = new Animation<>(this.type.getFrameDuration(), framesDeath);

        // initialize rect:
        rect = new CollisionRect(
            sprite.getX(),
            sprite.getY(),
            sprite.getWidth(),
            sprite.getHeight()
        );
    }

    public float getDashTimeRemaining() {
        return dashTimeRemaining;
    }

    public void setDashTimeRemaining(float dashTimeRemaining) {
        this.dashTimeRemaining = dashTimeRemaining;
    }

    public boolean isDashing() {
        return isDashing;
    }

    public void setDashing(boolean dashing) {
        isDashing = dashing;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }

    public void setDeathTime(float deathTime) {
        this.deathTime = deathTime;
    }

    public Sprite getDeathSprite() {
        return deathSprite;
    }

    public Animation<Texture> getDeathAnimation() {
        return deathAnimation;
    }

    public boolean isDying() {
        return isDying;
    }

    public float getDeathTime() {
        return deathTime;
    }

    public void updateHp(int hp) {
        this.hp += hp;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public MonsterType getType() {
        return type;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setLastHandleTime(float lastHandleTime) {
        this.lastHandleTime = lastHandleTime;
    }

    public float getLastHandleTime() {
        return lastHandleTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Animation<Texture> getAnimation() {
        return animation;
    }

    public int getHp() {
        return hp;
    }
}
