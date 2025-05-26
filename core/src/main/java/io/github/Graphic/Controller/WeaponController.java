package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.Graphic.Model.*;
import io.github.Graphic.TillDown;

import java.util.ArrayList;

public class WeaponController {

    private Weapon weapon = App.getGame().getPlayer().getWeapon();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public void update(){
        weapon.getSprite().draw(TillDown.getBatch());
        updateBullets();

        if (weapon.isReloading()) {
            weapon.updateReloadTimeRemaining(-Gdx.graphics.getDeltaTime());
            if (weapon.getReloadTimeRemaining() <= 0) {
                reloadWeapon();
            }
        }
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y){ // called when player mouse clic
        if (weapon.getAmmo() > 0) {
            bullets.add(new Bullet(x, y));
            weapon.updateAmmo(-1);

            if (weapon.getAmmo() == 0 && App.isIsAutoReload())
                startReload();
        } else {
            GameController.setWarning(App.getLanguage("error.weapon"));
        }
    }

    private void startReload() {
        weapon.setReloading(true);
        weapon.setReloadTimeRemaining(weapon.getType().getTimeReload());
    }

    private void reloadWeapon() {
        weapon.setReloading(false);
        weapon.setAmmo(weapon.getType().getAmmoMax());
    }

    public void handleManualReload() {
        if (!weapon.isReloading() && weapon.getAmmo() < weapon.getType().getAmmoMax()) {
            startReload();
        }
    }

    public void updateBullets() {
        for(Bullet b : bullets) {
            b.getSprite().draw(TillDown.getBatch());
            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth()/2f - b.getX(),
                Gdx.graphics.getHeight()/2f - b.getY()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }
}
