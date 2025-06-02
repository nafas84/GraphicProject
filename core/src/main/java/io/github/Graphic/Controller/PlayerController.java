package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.enums.Ability;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.EndGameMenu;
import io.github.Graphic.View.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlayerController {
    private GameView view;

    private final WeaponController weaponController = new WeaponController();
    private final Player player = App.getGame().getPlayer();

    public void setViews(GameView view) {
        this.view = view;
        weaponController.setView(view);
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void update(){
        player.getHero().getSprite().draw(TillDown.getBatch());

        handleAnimation();
        handlePlayerInput();
        handleCollision();
        handlePlayerStatus();
        handlePlayerAbilities();
        weaponController.update();
    }

    private void handlePlayerAbilities() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Iterator<Map.Entry<Ability, Float>> iterator = player.getAbilities().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Ability, Float> entry = iterator.next();
            float time = entry.getValue();

            if (time == -1) continue;

            time -= deltaTime;

            if (time <= 0) {
                iterator.remove();
            } else {
                entry.setValue(time);
            }
        }
    }


    private void handlePlayerStatus() {
        // handle invincible:
        if (player.isInvincible()) {
            player.updateInvincibleTimeRemaining(-Gdx.graphics.getDeltaTime());
            if (player.getInvincibleTimeRemaining() <= 0) {
                player.setInvincible(false);
            }
        }

        // update life:
        if (player.getLife() <= 0)
            TillDown.getGame().setScreen(new EndGameMenu(-1));

    }

    private void handlePlayerInput(){
        boolean isMoving = false;
        float newX = player.getX();
        float newY = player.getY();

        // Player move:
        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveUp())){
            newY += player.getSpeed();
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveDown())){
            newY -= player.getSpeed();
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveRight())){
            newX += player.getSpeed();
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveLeft())){
            newX -= player.getSpeed();
            player.getHero().getSprite().flip(true, false);
            isMoving = true;
        }

        // reload weapon:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getPauseGame()) && !view.isPaused()){
            view.setPaused(true);
            view.showPauseDialog();
        }
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getReloadWeapon())){
            weaponController.handleManualReload();
        }

        // cheats:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getCheatTime())){
            cheatTime();
        }
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getCheatLevel())){
            cheatLevel();
        }
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getCheatLife())){
            cheatLife();
        }
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getCheatHp())){
            cheatHp();
        }
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getCheatBossFight())){
            cheatBossFight();
        }

        // clamp player inside map boundaries
        int playerWidth = (int) player.getHero().getSprite().getWidth();
        int playerHeight = (int) player.getHero().getSprite().getHeight();
        float mapWidth = 2688;
        float mapHeight = 2688;
        newX = MathUtils.clamp(newX, 0, mapWidth - playerWidth);
        newY = MathUtils.clamp(newY, playerHeight, mapHeight - playerHeight);

        // set position
        player.setX(newX);
        player.setY(newY);
        player.getHero().getSprite().setPosition(newX, newY);
        player.getHero().getRect().move(newX, newY);

        // status
        player.setIdle(!isMoving);
    }

    private void handleAnimation(){
        Animation<Texture> animation = App.getGame().getPlayer().getHero().getAnimation();

        player.getHero().getSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void handleCollision() {
        // handle collision player with monsters: -10HP
        for (Monster monster: App.getGame().getMonsters()) {
            if (player.getHero().getRect().collidesWith(monster.getRect()) && !player.isInvincible()) {
                GameController.setWarning(App.getLanguage("game.collision"));
                player.updateHp(-10);
                player.setInvincible(true);
                player.setInvincibleTimeRemaining(1f);
            }
        }
        // handle collision player with seeds(bahman hashemi):
        List<Seed> toRemovedSeeds = new ArrayList<>();
        for (Seed seed: App.getGame().getSeeds()) {
            if (player.getHero().getRect().collidesWith(seed.getRect())) {
                toRemovedSeeds.add(seed);
                player.updateXp(5);
            }
        }

        App.getGame().getSeeds().removeAll(toRemovedSeeds);
    }

    //Cheat inputs:
    private void cheatTime() {
        float time = App.getGame().getTimeRemaining() - 60;
        if (time < 0) time = 0;
        App.getGame().setTime(time);
    }

    private void cheatLevel() {
        App.getGame().getPlayer().updateLevel();
    }

    private void cheatLife() {
        App.getGame().getPlayer().setLife(App.getGame().getPlayer().getHero().getType().getBaseLife());
    }

    private void cheatHp() {
        App.getGame().getPlayer().setHp(App.getGame().getPlayer().getMaxHP());
    }

    private void cheatBossFight() {
        MonsterController.getMonsterController().cheatBossFight();
    }
}
