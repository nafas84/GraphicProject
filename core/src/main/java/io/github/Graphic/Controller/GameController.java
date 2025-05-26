package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.Graphic.Model.*;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.*;


public class GameController {
    private GameView view;
    private static String warning = "";

    private final PlayerController playerController = new PlayerController();
    private final MapController MapController = new MapController();

    public void setView(GameView view) {
        this.view = view;
    }

    public static void setWarning(String warning) {
        GameController.warning = warning;
    }

    public void updateGame() {
        // draw sprite, animation,...
        MapController.update();
        playerController.update();

        updateGameBar();
        updateGameMessages();
    }

    public void updateGameBar() {
        Table table = view.getTable();
        table.clearChildren();

        Skin skin = TillDown.getSkin();

        Label life = new Label("    life: " + App.getGame().getPlayer().getLife() + "    ", skin);
        Label kill = new Label("kill: " + App.getGame().getPlayer().getKills() + "    ", skin);
        Label ammo = new Label("ammo: " + App.getGame().getPlayer().getWeapon().getAmmo() + "    ", skin);
        Label level = new Label("level: " + App.getGame().getPlayer().getLevel() + "    ", skin);
        Label xp = new Label("xp: " + App.getGame().getPlayer().getXp() + "    ", skin);
        Label raminTime = new Label("RemainTime : " + formatTime(App.getGame().getTimeRemaining()) + "    ", skin);

        table.add(life);
        table.add(kill);
        table.add(ammo);
        table.add(level);
        table.add(xp);
        table.add(raminTime);
    }

    public void updateGameMessages() {
        Skin skin = TillDown.getSkin();
        // show warning if any
        if (!GameController.warning.isEmpty()) {
            Label resultLabel = new Label(GameController.warning, skin);
            resultLabel.setColor(Color.RED);
            resultLabel.setPosition(50f, Gdx.graphics.getHeight() - 100f);

            resultLabel.addAction(Actions.sequence(
                Actions.fadeIn(0.2f),
                Actions.delay(2f),
                Actions.fadeOut(0.2f),
                Actions.removeActor()
            ));

            view.getStage().addActor(resultLabel);
        }

        setWarning("");
    }

    private String formatTime(float seconds) {
        int min = (int) seconds / 60;
        int sec = (int) seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }


    public PlayerController getPlayerController() {
        return playerController;
    }

    public static void initializeSprites() {
        Player player = App.getGame().getPlayer();
        // player:
        player.getHero().initializeSprite();

        // bullet:

        // weapon:
        player.getWeapon().initializeSprite();
        //rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
    }
}
