package io.github.Graphic.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.gson.Gson;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.SaveData.GameData;
import io.github.Graphic.Model.SaveData.MonsterData;
import io.github.Graphic.Model.SaveData.PlayerData;
import io.github.Graphic.Model.SaveData.SeedData;
import io.github.Graphic.Model.enums.MonsterType;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    public void updateGame() throws IOException {
        handleTime();
        // draw sprite, animation,...
        MapController.update();
        playerController.update();
        monsterController.update();

        updateGameBar();
        updateGameMessages();
    }

    private void handleTime() throws IOException {
        if (App.getGame().getTimeRemaining() <= 0) {
            if (App.getGame().getPlayer().getLife() <= 0) {
                TillDown.getGame().setScreen(new EndGameMenu(-1));
            } else {
                TillDown.getGame().setScreen(new EndGameMenu(1));
            }
        }
    }

    public void updateGameBar() {
        Table table = view.getTable();
        table.clearChildren();

        Skin skin = TillDown.getSkin();
        int neededXp = App.getGame().getPlayer().getXpNeedLevelUp() - App.getGame().getPlayer().getXp();

        Label life = new Label("    " + App.getLanguage("game.controller.life") +" " + App.getGame().getPlayer().getLife() + "    ", skin);
        Label kill = new Label(App.getLanguage("game.controller.kill") + " " + App.getGame().getPlayer().getKills() + "    ", skin);
        Label ammo = new Label(App.getLanguage("game.controller.ammo") +" " + App.getGame().getPlayer().getWeapon().getAmmo() + "    ", skin);
        Label level = new Label(App.getLanguage("game.controller.level") +" " + App.getGame().getPlayer().getLevel() + "    ", skin);
        Label xp = new Label(App.getLanguage("game.controller.xp") +" " + neededXp + "    ", skin);
        Label hp = new Label(App.getLanguage("game.controller.hp") + " " + App.getGame().getPlayer().getHp() + "    ", skin);
        Label raminTime = new Label(App.getLanguage("game.controller.remainTime") + " " + formatTime(App.getGame().getTimeRemaining()) + "    ", skin);
        Label abilities = new Label(App.getLanguage("game.controller.ability") +" " + App.getGame().getPlayer().getAbilitiesName() + "    ", skin);

        table.add(life);
        table.add(kill);
        table.add(ammo);
        table.add(level);
        table.add(xp);
        table.add(hp);
        table.add(raminTime);
        table.add(abilities);
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

    public static void initializeGame(boolean isFirstTime) {
        // initializeSprites:
        Player player = App.getGame().getPlayer();
        // player:
        player.getHero().initializeSprite();
        // weapon:
        player.getWeapon().initializeSprite();

        // initializeMonsters(Tree):
        if (isFirstTime)
            randomTrees();
    }

    private static void randomTrees() {
        int count = MathUtils.random(30, 60);

        for (int i = 0; i < count; i++) {
            float x = MathUtils.random(0f, 2600f);
            float y = MathUtils.random(0f, 2600f);
            App.getGame().getMonsters().add(new Monster(MonsterType.Tree, x, y));
        }
    }

    public static void setSetting(boolean grayScale, boolean sound, boolean autoReload){
        App.setIsSfx(sound);
        App.setIsAutoReload(autoReload);
        App.setGrayscale(grayScale);
    }

    public static void saveGame() throws IOException {
        // make game data:
        App.getSoundWastelandCombat().dispose();
        Game game = App.getGame();
        Player player = game.getPlayer();

        float totalTime = game.getTotalTime();
        float remainingTime = game.getTimeRemaining();

        PlayerData playerData = new PlayerData(player.getId(), player.getHero().getType(),
            player.getWeapon().getType(),
            player.getX(), player.getY(),
            player.getXp(), player.getHp(),
            player.getLife(), player.getKills(),
            player.getLevel());

        List<SeedData> seedDataList = new ArrayList<>();
        for (Seed seed: game.getSeeds()) {
            seedDataList.add(new SeedData(seed.getSprite().getX(), seed.getSprite().getY()));
        }

        List<MonsterData> monsterDataList = new ArrayList<>();
        for (Monster monster: game.getMonsters()) {
            monsterDataList.add(new MonsterData(monster.getX(), monster.getY(), monster.getType()));
        }

        // now save it to json:
        GameData gameData = new GameData(totalTime, remainingTime,
            playerData, seedDataList, monsterDataList);

        Gson gson = new Gson();
        FileWriter writer = new FileWriter("data/users/" + player.getId() + "/Game.json");
        gson.toJson(gameData, writer);
        writer.close();
    }
}
