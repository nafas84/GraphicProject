package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.enums.MonsterType;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.*;


public class GameController {
    private GameView view;
    private static String warning = "";

    private final PlayerController playerController = new PlayerController();
    private final MapController MapController = new MapController();
    private final MonsterController monsterController = new MonsterController();

    public void setViews(GameView view) {
        this.view = view;
        playerController.setViews(view);
    }

    public static void setWarning(String warning) {
        GameController.warning = warning;
    }

    public void updateGame() {
        // draw sprite, animation,...
        MapController.update();
        playerController.update();
        monsterController.update();

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
        Label position = new Label("Position : " + App.getGame().getPlayer().getX() + ", " + App.getGame().getPlayer().getY()  + "    ", skin);

        table.add(life);
        table.add(kill);
        table.add(ammo);
        table.add(level);
        table.add(xp);
        table.add(raminTime);
        table.add(position);
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

    public static void initializeGame() {
        // initializeSprites:
        Player player = App.getGame().getPlayer();
        // player:
        player.getHero().initializeSprite();
        // weapon:
        player.getWeapon().initializeSprite();

        // initializeMonsters(Tree):
        randomTrees();
//        App.getGame().getMonsters().add(new Monster(MonsterType.Tree, 1200, 960));
//        App.getGame().getMonsters().add(new Monster(MonsterType.Lamprey, 800, 900));
//        App.getGame().getMonsters().add(new Monster(MonsterType.Yog, 900, 1200));
//        App.getGame().getMonsters().add(new Monster(MonsterType.EyeBat, 500, 1000));

    }

    private static void randomTrees() {
        int count = MathUtils.random(30, 60);

        for (int i = 0; i < count; i++) {
            float x = MathUtils.random(0f, 2600f);
            float y = MathUtils.random(0f, 2600f);
            App.getGame().getMonsters().add(new Monster(MonsterType.Tree, x, y));
        }
    }
}
