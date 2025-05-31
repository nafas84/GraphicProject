package io.github.Graphic.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.GameController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private Table table = new Table();

    private final float MapWidth = 2688;
    private final float MapHeight = 2688;

    private OrthographicCamera camera;
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

        // set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        App.getGame().updateTime(delta);

        // update camera
        updateCamera();
        // update game
        TillDown.getBatch().begin();
        controller.updateGame();
        TillDown.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void updateCamera() {
        float camHalfWidth = camera.viewportWidth / 2f;
        float camHalfHeight = camera.viewportHeight / 2f;

        float playerX = App.getGame().getPlayer().getPosX();
        float playerY = App.getGame().getPlayer().getPosY();
        float spriteWidth = App.getGame().getPlayer().getHero().getSprite().getWidth();
        float spriteHeight = App.getGame().getPlayer().getHero().getSprite().getHeight();

        float centerX = playerX + spriteWidth / 2f;
        float centerY = playerY + spriteHeight / 2f;

        float cameraX = MathUtils.clamp(centerX, camHalfWidth, MapWidth - camHalfWidth);
        float cameraY = MathUtils.clamp(centerY, camHalfHeight, MapHeight - camHalfHeight);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();

        TillDown.getBatch().setProjectionMatrix(camera.combined);
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
