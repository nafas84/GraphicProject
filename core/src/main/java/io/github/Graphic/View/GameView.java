package io.github.Graphic.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.GameController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.MainMenu;

import java.io.IOException;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private Table table = new Table();

    private final float MapWidth = 2688;
    private final float MapHeight = 2688;

    private boolean paused = false;

    private OrthographicCamera camera;
    private GameController controller;

    public GameView(GameController controller) {
        this.controller = controller;
        controller.setViews(this);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Table getTable() {
        return table;
    }

    public Stage getStage() {
        return stage;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
        table.setFillParent(true);
        table.top().left();
        stage.addActor(table);
        Gdx.input.setInputProcessor(this);

        // InputMultiplexer for resume menu
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        //set curser
        Pixmap pixmap = new Pixmap(Gdx.files.internal("assets/etc/T_Cursor.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth()/2, pixmap.getHeight()/2); // offset x,y - نقطه فعال
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();

        // set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            ScreenUtils.clear(0, 0, 0, 1);

            App.getGame().updateTime(delta);

            // update camera
            updateCamera();

            // set shader
            TillDown.getBatch().setShader(App.getShader());
            App.getShader().setUniformi("u_grayscale", App.isGrayscale() ? 1 : 0);

            // update game
            TillDown.getBatch().begin();
            try {
                controller.updateGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TillDown.getBatch().end();
        }

        App.getShader().setUniformi("u_grayscale", App.isGrayscale() ? 1 : 0);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void updateCamera() {
        float camHalfWidth = camera.viewportWidth / 2f;
        float camHalfHeight = camera.viewportHeight / 2f;

        float playerX = App.getGame().getPlayer().getX();
        float playerY = App.getGame().getPlayer().getY();
        float spriteWidth = App.getGame().getPlayer().getHero().getSprite().getWidth();
        float spriteHeight = App.getGame().getPlayer().getHero().getSprite().getHeight();

        float centerX = playerX + spriteWidth / 2f;
        float centerY = playerY + spriteHeight / 2f;

        centerX = MathUtils.clamp(centerX, camHalfWidth, MapWidth - camHalfWidth);
        centerY = MathUtils.clamp(centerY, camHalfHeight, MapHeight - camHalfHeight);

        camera.position.set(centerX, centerY, 0);
        camera.update();

        TillDown.getBatch().setProjectionMatrix(camera.combined);
    }

    public void showPauseDialog() {
        Skin skin = TillDown.getSkin();
        Table table = getTableDialog();

        CheckBox colorCheckbox = new CheckBox(" " + App.getLanguage("setting.color"), skin);
        colorCheckbox.setChecked(App.isGrayscale());

        CheckBox soundCheckbox = new CheckBox(" " + App.getLanguage("setting.sound"), skin);
        soundCheckbox.setChecked(App.isIsSfx());

        CheckBox autoReloadCheckbox = new CheckBox(" " + App.getLanguage("setting.autoReload"), skin);
        autoReloadCheckbox.setChecked(App.isIsAutoReload());

        table.row();
        table.add(colorCheckbox).pad(10);
        table.add(soundCheckbox).pad(10);
        table.add(autoReloadCheckbox).pad(10).row();

        Dialog pauseDialog = new Dialog("Pause", skin, "round") {
            @Override
            protected void result(Object object) {
                if (object instanceof String) {
                    switch ((String) object) {
                        case "resume":
                            GameController.setSetting(colorCheckbox.isChecked(), soundCheckbox.isChecked(), autoReloadCheckbox.isChecked());
                            paused = false;
                            break;
                        case "give up":
                            try {
                                TillDown.getGame().setScreen(new EndGameMenu(0));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "save & exit":
                            // reset game save & exit
                            TillDown.getGame().setScreen(new MainMenu());
                            break;
                    }
                }
            }
        };

        pauseDialog.getContentTable().add(table).expand().fill();

        pauseDialog.button(App.getLanguage("game.resume"), "resume").row();
        pauseDialog.button(App.getLanguage("game.giveUp"), "give up").row();
        pauseDialog.button(App.getLanguage("game.save"), "save & exit").row();

        pauseDialog.setMovable(false);
        pauseDialog.setModal(true);
        pauseDialog.show(stage);
    }

    private Table getTableDialog() {
        final Label moveUp, moveDown, moveLeft, moveRight, autoAim, reloadWeapon, cheatTime, cheatLevel, cheatLife, cheatHp, pauseGame, shash;
        final Label moveUpButton, moveDownButton, moveLeftButton, moveRightButton, autoAimButton,pauseGameButton,  reloadWeaponButton, cheatTimeButton, cheatLevelButton, cheatLifeButton, cheatHpButton;

        Skin skin = TillDown.getSkin();

        shash = new Label("     ", skin);

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


        Table table = new Table();
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

        return table;
    }




    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getPlayerController().getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getPlayerController().getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
