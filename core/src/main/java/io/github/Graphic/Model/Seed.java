package io.github.Graphic.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Graphic.Model.enums.AssetType;

public class Seed {
    private Sprite sprite;
    private CollisionRect rect;
    private final float x, y;

    public Seed(float x, float y) {
        this.x = x;
        this.y = y;

        sprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.Seed));
        sprite.setScale(2.5f);
        sprite.setPosition(x, y);

        rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
