package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
    private Sprite sprite;

    private int damage = 5;
    private float x;
    private float y;

    public Bullet(float x, float y){
        this.x = x;
        this.y = y;

        initializeSprite();
    }

    public void initializeSprite(){
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.Bullet));

        sprite.setSize(20 , 20);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
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
}
