package io.github.Graphic.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.MainMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.Model.Player;
import io.github.Graphic.Model.Result;
import io.github.Graphic.Model.User;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.ChangeAvatar;
import io.github.Graphic.View.Main.MainMenu;
import io.github.Graphic.View.Start.StartMenu;

import java.io.IOException;

public class EndGameMenu implements Screen {
    private final int state; // -1 lose, 0 give up, 1 win
    private final Stage stage;
    private final Table table;

    private final Label resultGame, gameInfo;
    private final TextButton exit, backButton;

    public EndGameMenu (int state) throws IOException {
        App.getSoundWastelandCombat().dispose();
        updateGameInfoToUser();
        //TODO: language
        String result = "";
        if (state == -1) {
            result = "You lose noobe sag";
        } else if (state == 0) {
            result = "Give up? nadan";
        } else if (state == 1) {
            result = "You winnnnnnnnnnn";
        }

        this.state = state;

        Skin skin = TillDown.getSkin();

        gameInfo = new Label(App.getGame().gameInfo(), skin);
        resultGame = new Label(result, skin);

        exit = new TextButton(App.getLanguage("button.exit"), skin);
        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        table.add(resultGame).size(250).pad(20).row();
        table.add(gameInfo).size(250).pad(20).row();

        table.add(backButton).width(150).padRight(10).pad(10);
        table.add(exit).width(200).padLeft(10).pad(10);

        stage.addActor(table);

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setGame(null);
                TillDown.getGame().setScreen(new MainMenu());
            }
        });
    }

    private void updateGameInfoToUser() throws IOException {
        Player player = App.getGame().getPlayer();
        User user = App.getCurrentUser();

        int score = (int) (App.getGame().getPassedTime() * player.getKills());
        user.setTotalKill(user.getTotalKill() + player.getKills());
        user.setBestTimeLive(Math.max(user.getBestTimeLive(), App.getGame().getPassedTime()));
        user.setTotalScore(user.getTotalScore() + score);

        App.updateUser();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        App.getShader().setUniformi("u_grayscale", App.isGrayscale() ? 1 : 0);
        stage.act(delta);
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
        stage.dispose();
    }
}
