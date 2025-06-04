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
            // handle projectile:
            int count = weapon.getProjectile();

            float startX = player.getX() + player.getHero().getSprite().getWidth() / 2f;
            float startY = player.getY() + player.getHero().getSprite().getHeight() / 2f;

            for (int i = 0; i < count; i++) {
                float angleOffset = 0;
                if (count > 1) {
                    angleOffset = (i - (count - 1) / 2f) * 10;
                }

                Vector2 direction = new Vector2(worldCoordinate.x - startX, worldCoordinate.y - startY).nor();
                direction.rotateDeg(angleOffset);

                float adjustedTargetX = startX + direction.x * 1000;
                float adjustedTargetY = startY + direction.y * 1000;

                bullets.add(new Bullet(adjustedTargetX, adjustedTargetY));
            }

            if (App.isIsSfx())
                App.getSoundSingleShot().play();

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
        weapon.setAmmo(weapon.getAmmoMax());
    }

    public void handleManualReload() {
        if (!weapon.isReloading() && weapon.getAmmo() < weapon.getAmmoMax()) {
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
                    monster.updateHp(-App.getGame().getPlayer().getWeapon().getDamage());
                    handleKnockBack(monster, bullet);
                    // kill monster:
                    if (monster.getHp() <= 0) {
                        player.updateKills(1);
                        toRemovedMonsters.add(monster);
                        handleSeed(monster);
                    }
                    toRemoveBullets.add(bullet);
                    break;
                }
            }
        }

        bullets.removeAll(toRemoveBullets);
        App.getGame().getMonsters().removeAll(toRemovedMonsters);
    }

    private void handleSeed(Monster monster) {
        App.getGame().getSeeds().add(new Seed(monster.getSprite().getX(), monster.getSprite().getY()));
        if (monster.getType().equals(MonsterType.Yog)) {
            App.getGame().getSeeds().add(new Seed(monster.getSprite().getX() + 5, monster.getSprite().getY() + 5));
            App.getGame().getSeeds().add(new Seed(monster.getSprite().getX() + -5, monster.getSprite().getY() + -5));
            App.getGame().getSeeds().add(new Seed(monster.getSprite().getX() + -10, monster.getSprite().getY() + 5));
            App.getGame().getSeeds().add(new Seed(monster.getSprite().getX() + -10, monster.getSprite().getY() + 10));
        } else if (monster.getType().equals(MonsterType.EyeBat)) {
            App.getGame().getSeeds().add(new Seed(monster.getSprite().getX() + 5, monster.getSprite().getY() + 5));
        }
    }

    private void handleKnockBack(Monster monster, Bullet bullet) {
        float dx = monster.getX() - bullet.getX();
        float dy = monster.getY() - bullet.getY();

        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        float knockbackStrength = 10f + App.getGame().getPlayer().getWeapon().getDamage();
        float newX = monster.getX() + dx * knockbackStrength;
        float newY = monster.getY() + dy * knockbackStrength;

        monster.setX(newX);
        monster.setY(newY);
        monster.getSprite().setPosition(newX, newY);
        monster.getRect().move(newX, newY);
    }
}
