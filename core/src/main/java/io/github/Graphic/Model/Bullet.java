package io.github.Graphic.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import io.github.Graphic.Model.enums.AssetType;

public class Bullet {
    private Sprite sprite;
    private boolean isMonsterBullet = false;
    private int damage = 5;
    private Vector2 direction;

    private float x;
    private float y;

    public Bullet(float targetX, float targetY) {
        Player player = App.getGame().getPlayer();

        float startX = player.getX() + player.getHero().getSprite().getWidth() / 2f;
        float startY = player.getY() + player.getHero().getSprite().getHeight() / 2f;

        this.x = startX;
        this.y = startY;

        direction = new Vector2(targetX - startX, targetY - startY).nor();

        initializeSprite();
    }

    public Bullet(boolean isMonsterBullet, float monsterX, float monsterY) {
        this.isMonsterBullet = isMonsterBullet;
        //TODO:wtf
        Player player = App.getGame().getPlayer();
        float targetX = player.getX() + player.getHero().getSprite().getWidth();
        float targetY = player.getY() + player.getHero().getSprite().getHeight();

        this.x = monsterX;
        this.y = monsterY;

        direction = new Vector2(targetX - monsterX, targetY - monsterY).nor();

        initializeSprite();
    }


    private void initializeSprite() {
        if (this.isMonsterBullet) {
            sprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.MonsterBullet));
            sprite.setScale(3f);
        } else {
            sprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.Bullet));
        }
        sprite.setOriginCenter();
        sprite.setPosition(x - sprite.getWidth() / 2f, y - sprite.getHeight() / 2f);
        sprite.setSize(40, 40);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void updatePosition(float dx, float dy) {
        x += dx;
        y += dy;
        sprite.setPosition(x - sprite.getWidth() / 2f, y - sprite.getHeight() / 2f);
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
