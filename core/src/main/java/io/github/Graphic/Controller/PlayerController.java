package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Graphic.Model.*;
import io.github.Graphic.TillDown;

public class PlayerController {
    private final WeaponController weaponController = new WeaponController();

    private Player player = App.getGame().getPlayer();

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
        // Player move:
        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveUp())){
            player.setPosY(player.getPosY() - player.getSpeed());
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveDown())){
            player.setPosY(player.getPosY() + player.getSpeed());
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveRight())){
            player.setPosX(player.getPosX() - player.getSpeed());
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveLeft())){
            player.setPosX(player.getPosX() + player.getSpeed());
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
        // pause game:

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
