package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Graphic.Model.enums.AssetType;
import io.github.Graphic.Model.enums.WeaponType;

public class Weapon {
    private Sprite sprite;

    private final WeaponType type;
    private int ammo;

    private boolean isReloading = false;
    private float reloadTimeRemaining = 0;

    public Weapon(WeaponType type){
        this.type = type;
        this.ammo = type.getAmmoMax();
    }

    public void initializeSprite(){
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getTexture(AssetType.Weapon));
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
    }

    public void setReloadTimeRemaining(float reloadTimeRemaining) {
        this.reloadTimeRemaining = reloadTimeRemaining;
    }

    public void setReloading(boolean reloading) {
        isReloading = reloading;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    //Update methods:
    public void updateAmmo(int ammo) {
        this.ammo += ammo;
    }

    public void updateReloadTimeRemaining(float reloadTimeRemaining) {
        this.reloadTimeRemaining += reloadTimeRemaining;
    }

    public float getReloadTimeRemaining() {
        return reloadTimeRemaining;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public String getAssetPath() {
        return type.getAssetPath();
    }

    public WeaponType getType() {
        return type;
    }
}
