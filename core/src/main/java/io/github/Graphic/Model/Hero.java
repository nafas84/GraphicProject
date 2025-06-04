package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Graphic.Model.enums.AssetType;
import io.github.Graphic.Model.enums.HeroType;

public class Hero {
    private Sprite idleSprite;
    private Sprite runSprite;
    private Animation<Texture> idleAnimation;
    private Animation<Texture> runAnimation;

    // holy shield:
    private Sprite holeyShieldSprite;
    private Animation<Texture> holeyShieldAnimation;

    private final HeroType type;
    private boolean isIdle = true;

    private CollisionRect rect;

    public Hero(HeroType type) {
        this.type = type;
    }

    public void initializeSprite(){
        idleSprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.IdleHero));
        runSprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.RunHero));

        Texture[] idleFrames = GameAssetManager.getGameAssetManager().getAnimation(AssetType.IdleHero).getKeyFrames();
        Texture[] runFrames = GameAssetManager.getGameAssetManager().getAnimation(AssetType.RunHero).getKeyFrames();

        idleAnimation = new Animation<>(0.1f, idleFrames.clone());
        runAnimation = new Animation<>(0.1f, runFrames.clone());

        // initialize position:
        Texture texture = GameAssetManager.getGameAssetManager().getTexture(AssetType.IdleHero);
        idleSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        idleSprite.setSize(texture.getWidth() * 3, texture.getHeight() * 3);

        Texture textureRun = GameAssetManager.getGameAssetManager().getTexture(AssetType.RunHero);
        runSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        runSprite.setSize(textureRun.getWidth() * 3, textureRun.getHeight() * 3);

        // Holey Shield:
        holeyShieldSprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.HoleyShield));

        Texture[] shieldFrames = GameAssetManager.getGameAssetManager().getAnimation(AssetType.HoleyShield).getKeyFrames();
        holeyShieldAnimation = new Animation<>(0.1f, shieldFrames.clone());

        Texture textureShield = GameAssetManager.getGameAssetManager().getTexture(AssetType.HoleyShield);
        holeyShieldSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        holeyShieldSprite.setSize(textureShield.getWidth() * 4, textureShield.getHeight() * 4);

        // initialize rect:
        rect = new CollisionRect(
            idleSprite.getX(),
            idleSprite.getY(),
            idleSprite.getWidth(),
            idleSprite.getHeight()
        );
    }

    public Sprite getHoleyShieldSprite() {
        return holeyShieldSprite;
    }

    public Animation<Texture> getHoleyShieldAnimation() {
        return holeyShieldAnimation;
    }

    public Sprite getSprite() {
        if (isIdle) return idleSprite;
        else return runSprite;
    }

    public Animation<Texture> getAnimation() {
        if (this.isIdle)
            return idleAnimation;
        else
            return runAnimation;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public int getMaxHP() {
        return this.type.getMaxHP();
    }

    public HeroType getType() {
        return type;
    }

    public int getSpeed() {
        return this.type.getBaseSpeed();
    }
    public String getAssetFolderPath() {
        return this.type.getAssetFolderPath();
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }
}
