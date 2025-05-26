package io.github.Graphic.View.Start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.StartMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.MainMenu;

public class StartMenu implements Screen {
    private Stage stage;
    private final Table table;

    private final TextButton signUpButton, loginButton, guestButton, exitButton;

    public StartMenu() {
        Skin skin = TillDown.getSkin();

        signUpButton = new TextButton(App.getLanguage("button.signUp"), skin);
        loginButton = new TextButton(App.getLanguage("button.login"), skin);
        guestButton = new TextButton(App.getLanguage("button.guest"), skin);
        exitButton = new TextButton(App.getLanguage("button.exit"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center().row();

        table.add(signUpButton).width(200).pad(10).row();
        table.add(loginButton).width(200).pad(10).row();
        table.add(guestButton).width(200).pad(10).row();
        table.add(exitButton).width(150).pad(10);

        stage.addActor(table);

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new SignUpMenu());
            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new LoginMenu());
            }
        });
        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StartMenuController.guestMode();
                TillDown.getGame().setScreen(new MainMenu());
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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
