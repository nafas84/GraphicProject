package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.enums.MonsterType;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.GameView;

import java.util.ArrayList;
import java.util.List;

public class WeaponController {
    private GameView view;

    private Weapon weapon = App.getGame().getPlayer().getWeapon();
    private Player player = App.getGame().getPlayer();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public void setView(GameView view) {
        this.view = view;
    }

    public void update(){
        updateWeapon();
        updateBullets();
        if (weapon.isReloading()) {
            weapon.updateReloadTimeRemaining(-Gdx.graphics.getDeltaTime());
            if (weapon.getReloadTimeRemaining() <= 0) {
                reloadWeapon();
            }
        }
    }

    private void updateWeapon() {
        Sprite weaponSprite = weapon.getSprite();

        float centerX = player.getX() + player.getHero().getSprite().getWidth();
        float centerY = player.getY() + player.getHero().getSprite().getHeight() / 2f;

        weaponSprite.setOriginCenter();

        weaponSprite.setPosition(
            centerX - weaponSprite.getWidth() / 2f,
            centerY - weaponSprite.getHeight() / 2f
        );

        weaponSprite.draw(TillDown.getBatch());
    }

    public void handleWeaponRotation(int x, int y) {  // called when player mouse clic
        Vector3 worldCoordinate = new Vector3(x, y, 0);
        view.getCamera().unproject(worldCoordinate);

        float playerX = player.getX() + player.getHero().getSprite().getWidth() / 2f;
        float playerY = player.getY() + player.getHero().getSprite().getHeight() / 2f;

        float angleRad = (float) Math.atan2(worldCoordinate.y - playerY, worldCoordinate.x - playerX);
        float angleDeg = angleRad * MathUtils.radiansToDegrees;

        weapon.getSprite().setRotation(angleDeg);
    }

    public void handleWeaponShoot(int x, int y){ // called when player mouse clic
        Vector3 worldCoordinate = new Vector3(x, y, 0);
        view.getCamera().unproject(worldCoordinate);
        if (weapon.getAmmo() > 0) {
            bullets.add(new Bullet(worldCoordinate.x, worldCoordinate.y));
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
        List<Bullet> toRemoveBullets = new ArrayList<>();
        List<Monster> toRemovedMonsters = new ArrayList<>();

        for (Bullet bullet : bullets) {
            // update positions:
            bullet.getSprite().draw(TillDown.getBatch());

            Vector2 dir = bullet.getDirection();
            bullet.updatePosition(dir.x * 5, dir.y * 5);

            // update collision with monsters:
            for (Monster monster : App.getGame().getMonsters()) {
                if (!monster.getType().equals(MonsterType.Tree) && bullet.getRect().collidesWith(monster.getRect())) {
                    monster.updateHp(-App.getGame().getPlayer().getWeapon().getType().getDamage());
                    if (monster.getHp() <= 0)
                        toRemovedMonsters.add(monster);
                    toRemoveBullets.add(bullet);
                    break;
                }
            }
        }

        bullets.removeAll(toRemoveBullets);
        App.getGame().getMonsters().removeAll(toRemovedMonsters);
    }


}
