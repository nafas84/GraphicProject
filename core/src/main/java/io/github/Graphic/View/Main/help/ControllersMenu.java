package io.github.Graphic.View.Main.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;

public class ControllersMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label help, moveUp, moveDown, moveLeft, moveRight, autoAim, reloadWeapon, cheatTime, cheatLevel, cheatLife, cheatHp, pauseGame, shash;
    private final Label moveUpButton, moveDownButton, moveLeftButton, moveRightButton, autoAimButton,pauseGameButton,  reloadWeaponButton, cheatTimeButton, cheatLevelButton, cheatLifeButton, cheatHpButton;
    private final TextButton backButton;

    private final int where;

    public ControllersMenu(int where) {
        this.where = where;

        Skin skin = TillDown.getSkin();

        shash = new Label("     ", skin);
        help = new Label(App.getLanguage("title.control"), TillDown.getSkin(), "title");

        moveUp = new Label(App.getLanguage("up"), skin, "subtitle");
        moveDown = new Label(App.getLanguage("down"), skin, "subtitle");
        moveLeft = new Label(App.getLanguage("left"), skin, "subtitle");
        moveRight = new Label(App.getLanguage("right"), skin, "subtitle");
        autoAim = new Label(App.getLanguage("autoAim"), skin, "subtitle");
        reloadWeapon = new Label(App.getLanguage("reload"), skin, "subtitle");
        cheatTime = new Label(App.getLanguage("cheatTime"), skin, "subtitle");
        cheatLevel = new Label(App.getLanguage("cheatLevel"), skin, "subtitle");
        cheatLife = new Label(App.getLanguage("cheatLife"), skin, "subtitle");
        cheatHp = new Label(App.getLanguage("cheatHp"), skin, "subtitle");
        pauseGame = new Label(App.getLanguage("pause"), skin, "subtitle");

        float labelFontScale = 1.5f;

        moveUpButton = new Label(Input.Keys.toString(App.getKeyManager().getMoveUp()), skin);
        moveDownButton = new Label(Input.Keys.toString(App.getKeyManager().getMoveDown()), skin);
        moveLeftButton = new Label(Input.Keys.toString(App.getKeyManager().getMoveLeft()), skin);
        moveRightButton = new Label(Input.Keys.toString(App.getKeyManager().getMoveRight()), skin);
        autoAimButton = new Label(Input.Keys.toString(App.getKeyManager().getCheatBossFight()), skin);
        reloadWeaponButton = new Label(Input.Keys.toString(App.getKeyManager().getReloadWeapon()), skin);
        cheatTimeButton = new Label(Input.Keys.toString(App.getKeyManager().getCheatTime()), skin);
        cheatLevelButton = new Label(Input.Keys.toString(App.getKeyManager().getCheatLevel()), skin);
        cheatLifeButton = new Label(Input.Keys.toString(App.getKeyManager().getCheatLife()), skin);
        cheatHpButton = new Label(Input.Keys.toString(App.getKeyManager().getCheatHp()), skin);
        pauseGameButton = new Label(Input.Keys.toString(App.getKeyManager().getPauseGame()), skin);


        moveUpButton.setFontScale(labelFontScale);
        moveDownButton.setFontScale(labelFontScale);
        moveLeftButton.setFontScale(labelFontScale);
        moveRightButton.setFontScale(labelFontScale);
        autoAimButton.setFontScale(labelFontScale);
        reloadWeaponButton.setFontScale(labelFontScale);
        cheatTimeButton.setFontScale(labelFontScale);
        cheatLevelButton.setFontScale(labelFontScale);
        cheatLifeButton.setFontScale(labelFontScale);
        cheatHpButton.setFontScale(labelFontScale);
        pauseGameButton.setFontScale(labelFontScale);

        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center().row();
        table.add(help).width(100).pad(20).top().left().row();

        table.add(moveUp);
        table.add(moveUpButton).width(300).pad(10);
        table.add(cheatTime);
        table.add(cheatTimeButton).width(300).pad(10).row();

        table.add(moveDown);
        table.add(moveDownButton).width(325).pad(10);
        table.add(cheatLevel);
        table.add(cheatLevelButton).width(325).pad(10).row();

        table.add(moveLeft);
        table.add(moveLeftButton).width(325).pad(10);
        table.add(cheatLife);
        table.add(cheatLifeButton).width(325).pad(10).row();

        table.add(moveRight);
        table.add(moveRightButton).width(325).pad(10);
        table.add(cheatHp);
        table.add(cheatHpButton).width(325).pad(10).row();

        table.add(autoAim);
        table.add(autoAimButton).width(325).pad(10);
        table.add(pauseGame);
        table.add(pauseGameButton).width(325).pad(10).row();

        table.add(reloadWeapon);
        table.add(reloadWeaponButton).width(325).pad(10);
        table.add(shash);

        table.row();
        table.add(backButton);

        stage.addActor(table);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new HelpMenu(where));
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
