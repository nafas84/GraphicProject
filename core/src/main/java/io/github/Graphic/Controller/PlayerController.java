package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import io.github.Graphic.Model.*;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.GameView;

public class PlayerController {
    private GameView view;

    private final WeaponController weaponController = new WeaponController();

    private final Player player = App.getGame().getPlayer();

    public void setViews(GameView view) {
        this.view = view;
        weaponController.setView(view);
    }

    public void update(){
        player.getHero().getSprite().draw(TillDown.getBatch());

        handleAnimation();
        handlePlayerInput();
        weaponController.update();
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void handlePlayerInput(){
        boolean isMoving = false;
        float newX = player.getPosX();
        float newY = player.getPosY();

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

        // auto aim, reload weapon:
        if (Gdx.input.isKeyPressed(App.getKeyManager().getAutoAim())){
            //TODO
        }
        if (Gdx.input.isKeyPressed(App.getKeyManager().getReloadWeapon())){
            weaponController.handleManualReload();
        }
        // cheats:
        if (Gdx.input.isKeyPressed(App.getKeyManager().getCheatTime())){
            weaponController.handleManualReload();
        }
        if (Gdx.input.isKeyPressed(App.getKeyManager().getCheatLevel())){
            weaponController.handleManualReload();
        }
        if (Gdx.input.isKeyPressed(App.getKeyManager().getCheatLife())){
            weaponController.handleManualReload();
        }
        if (Gdx.input.isKeyPressed(App.getKeyManager().getCheatHp())){
            weaponController.handleManualReload();
        }

        // clamp player inside map boundaries
        int playerWidth = (int) player.getHero().getSprite().getWidth();
        int playerHeight = (int) player.getHero().getSprite().getHeight();
        float mapWidth = 2688;
        float mapHeight = 2688;
        newX = MathUtils.clamp(newX, 0, mapWidth - playerWidth);
        newY = MathUtils.clamp(newY, playerHeight, mapHeight - playerHeight);

        // set position
        player.setPosX(newX);
        player.setPosY(newY);
        player.getHero().getSprite().setPosition(newX, newY);

        // status
        player.setIdle(!isMoving);
    }


    public void handleAnimation(){
        Animation<Texture> animation = App.getGame().getPlayer().getHero().getAnimation();

        player.getHero().getSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    //Cheat inputs:
    private void cheatTime() {
        App.getGame().setTime(Math.min(App.getGame().getTimeRemaining() - 60, 0));
    }
    private void cheatLevel() {
        //TODO: logic
        App.getGame().getPlayer().updateLevel();
    }
    private void cheatLife() {
        App.getGame().getPlayer().setLife(App.getGame().getPlayer().getHero().getType().getBaseLife());
    }
    private void cheatHp() {
        App.getGame().getPlayer().setHp(App.getGame().getPlayer().getMaxHP());
    }

}
