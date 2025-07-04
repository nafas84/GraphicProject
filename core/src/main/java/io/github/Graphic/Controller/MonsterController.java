package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.enums.MonsterType;
import io.github.Graphic.TillDown;

import java.util.ArrayList;
import java.util.List;

public class MonsterController {
    private static MonsterController monsterController;
    private ArrayList<Bullet> monsterBullets = new ArrayList<>();
    private float lastSpawnTimeLamprey = 0;
    private float lastSpawnTimeEyeBat = 0;
    private boolean isYogSpawned = false;

    public static MonsterController getMonsterController() {
        if (monsterController == null) {
            monsterController = new MonsterController();
        }
        return monsterController;
    }

    public void update() {
        List<Monster> toRemove = new ArrayList<>();

        for (Monster monster : App.getGame().getMonsters()) {
            // handle dying/remove monsters
            if (monster.isDying()) {
                // handle dying animation:
                monster.setDeathTime(monster.getDeathTime() + Gdx.graphics.getDeltaTime());
                Animation<Texture> animation = monster.getDeathAnimation();
                monster.getDeathSprite().setRegion(animation.getKeyFrame(monster.getDeathTime()));

                monster.getDeathSprite().draw(TillDown.getBatch());

                if (monster.getDeathAnimation().isAnimationFinished(monster.getDeathTime()))
                    toRemove.add(monster);
            } else {
                monster.getSprite().draw(TillDown.getBatch());
            }
        }

        App.getGame().getMonsters().removeAll(toRemove);

        handleAnimations();
        handleMove();
        handleShoot();
        updateBullets();
        handleDash();
        handleSpawn();
    }

    private void handleAnimations(){
        for (Monster monster: App.getGame().getMonsters()) {
            if (monster.isDying()) continue;

            Animation<Texture> animation = monster.getAnimation();
            monster.getSprite().setRegion(animation.getKeyFrame(monster.getTime()));

            if (!animation.isAnimationFinished(monster.getTime())) {
                monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                monster.setTime(0);
            }

            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    private void handleSpawn() {
        handleSpawnLamprey();
        handleSpawnEyeBat();
        handleSpawnYog();
    }

    private void handleSpawnLamprey() {
        float passedTime = App.getGame().getPassedTime();
        // in 3s -> i / 30 (i passed time) spawn
        if (passedTime - lastSpawnTimeLamprey >= 3f) {
            lastSpawnTimeLamprey = passedTime;

            int numToSpawn = (int) (passedTime / 30f);

            for (int i = 0; i < numToSpawn; i++) {
                Vector2 spawnPos = generateRandomSpawnPosition();

                Monster monster = new Monster(MonsterType.Lamprey, spawnPos.x, spawnPos.y);
                App.getGame().getMonsters().add(monster);
            }
        }
    }

    private void handleSpawnEyeBat() {
        float passedTime = App.getGame().getPassedTime();
        float totalTime = App.getGame().getTotalTime();
        // in 10s -> t/4 total timed passed -> (4i - t + 30) / 30 spawn
        if (totalTime / 4f > passedTime) return;

        if (passedTime - lastSpawnTimeEyeBat >= 10f) {
            lastSpawnTimeEyeBat = passedTime;

            int numToSpawn = (int) (4 * passedTime - totalTime + 30) / 30;

            for (int i = 0; i < numToSpawn; i++) {
                Vector2 spawnPos = generateRandomSpawnPosition();

                Monster monster = new Monster(MonsterType.EyeBat, spawnPos.x, spawnPos.y);
                App.getGame().getMonsters().add(monster);
            }
        }
    }

    public void handleSpawnYog() {
        float passedTime = App.getGame().getPassedTime();
        float totalTime = App.getGame().getTotalTime();
        // t/2 game passed 1 spawn
        if (totalTime / 2f <= passedTime && !isYogSpawned) {
            isYogSpawned = true;
            Vector2 spawnPos = generateRandomSpawnPosition();

            Monster monster = new Monster(MonsterType.Yog, spawnPos.x, spawnPos.y);
            App.getGame().getMonsters().add(monster);

            if (App.isIsSfx())
                App.getSoundWastelandCombat().play();
        }
    }

    public void cheatBossFight() {
        if (!isYogSpawned) {
            isYogSpawned = true;
            Vector2 spawnPos = generateRandomSpawnPosition();

            Monster monster = new Monster(MonsterType.Yog, spawnPos.x, spawnPos.y);
            App.getGame().getMonsters().add(monster);
            if (App.isIsSfx())
                App.getSoundWastelandCombat().play();
        }
    }

    private Vector2 generateRandomSpawnPosition() {
        float mapWidth = 2688;
        float mapHeight = 2688;

        float x = 0, y = 0;
        int edge = MathUtils.random(3); // 0: up، 1: down، 2: left، 3: right

        switch (edge) {
            case 0:
                x = MathUtils.random(mapWidth);
                y = mapHeight + 50;
                break;
            case 1:
                x = MathUtils.random(mapWidth);
                y = -50;
                break;
            case 2:
                x = -50;
                y = MathUtils.random(mapHeight);
                break;
            case 3:
                x = mapWidth + 50;
                y = MathUtils.random(mapHeight);
                break;
        }

        return new Vector2(x, y);
    }


    private void handleMove() {
        for (Monster monster : App.getGame().getMonsters()) {
            if (!monster.getType().isCanWalk() || monster.isDying()) continue;
            moveMonster(monster, 1);
        }
    }

    private void handleShoot() {
        float currentTime = App.getGame().getPassedTime();

        for (Monster monster : App.getGame().getMonsters()) {
            if (!monster.getType().isCanShoot()) continue;

            if (currentTime - monster.getLastHandleTime() >= 3f) {
                monster.setLastHandleTime(currentTime);

                float monsterX = monster.getSprite().getX() + monster.getSprite().getWidth() / 2;
                float monsterY = monster.getSprite().getY() + monster.getSprite().getHeight() / 2;

                Bullet bullet = new Bullet(true, monsterX, monsterY);
                monsterBullets.add(bullet);
            }
        }
    }

    private void handleDash() {
        float currentTime = App.getGame().getPassedTime();

        for (Monster monster : App.getGame().getMonsters()) {
            if (!monster.getType().isCanDash()) continue;

            if (monster.isDashing()) {
                moveMonster(monster, 3);
                monster.setDashTimeRemaining(monster.getDashTimeRemaining() - Gdx.graphics.getDeltaTime());

                if (monster.getDashTimeRemaining() <= 0) {
                    monster.setDashing(false);
                }

            } else {
                if (currentTime - monster.getLastHandleTime() >= 5f) {
                    monster.setDashing(true);
                    // dash duration:
                    monster.setDashTimeRemaining(0.3f);

                    monster.setLastHandleTime(currentTime);
                }
            }
        }
    }

    private void updateBullets() {
        Player player = App.getGame().getPlayer();
        List<Bullet> toRemoveBullets = new ArrayList<>();

        for (Bullet b : monsterBullets) {
            b.getSprite().draw(TillDown.getBatch());

            Vector2 dir = b.getDirection();
            b.updatePosition(dir.x * 5, dir.y * 5);

            // update collision with player:
            if (b.getRect().collidesWith(player.getHero().getRect())) {
                player.playerHit();
                player.updateHp(-b.getMonsterDamage());
                GameController.setWarning(App.getLanguage("game.damage"));
                toRemoveBullets.add(b);

                if (App.isIsSfx())
                    App.getSoundAhmagh().play();

                break;
            }
        }

        monsterBullets.removeAll(toRemoveBullets);
    }

    private void moveMonster(Monster monster, int speedShash) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        float playerX = App.getGame().getPlayer().getX();
        float playerY = App.getGame().getPlayer().getY();

        float monsterX = monster.getSprite().getX();
        float monsterY = monster.getSprite().getY();

        float dx = playerX - monsterX;
        float dy = playerY - monsterY;

        float length = (float) Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        float speed = monster.getType().getSpeed() * speedShash;

        float newX = monsterX + dx * speed * deltaTime;
        float newY = monsterY + dy * speed * deltaTime;

        monster.setX(newX);
        monster.setY(newY);
        monster.getSprite().setPosition(newX, newY);
        monster.getRect().move(newX, newY);
    }
}
