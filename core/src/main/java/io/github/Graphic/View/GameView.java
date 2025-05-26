package io.github.Graphic.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.GameController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private Table table = new Table();

    private GameController controller;

    public GameView(GameController controller) {
        this.controller = controller;
        controller.setView(this);
    }

    public Table getTable() {
        return table;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        table.setFillParent(true);
        table.top().left();
        stage.addActor(table);
        Gdx.input.setInputProcessor(this);

        //set curser
        Pixmap pixmap = new Pixmap(Gdx.files.internal("assets/etc/T_Cursor.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth()/2, pixmap.getHeight()/2); // offset x,y - نقطه فعال
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }

    @Override
    public void render(float delta) {
        App.getGame().updateTime(delta);

        ScreenUtils.clear(0, 0, 0, 1);

        TillDown.getBatch().begin();
        controller.updateGame();
        TillDown.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
