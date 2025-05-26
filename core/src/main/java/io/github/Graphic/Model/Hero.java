package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hero {
    private Sprite idleSprite;
    private Sprite runSprite;
    private Animation<Texture> idleAnimation;
    private Animation<Texture> runAnimation;

    private final HeroType type;
    private boolean isIdle = true;

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

        Texture texture = GameAssetManager.getGameAssetManager().getTexture(AssetType.IdleHero);
        idleSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        idleSprite.setSize(texture.getWidth() * 3, texture.getHeight() * 3);

        Texture textureRun = GameAssetManager.getGameAssetManager().getTexture(AssetType.RunHero);
        runSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        runSprite.setSize(textureRun.getWidth() * 3, textureRun.getHeight() * 3);
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
