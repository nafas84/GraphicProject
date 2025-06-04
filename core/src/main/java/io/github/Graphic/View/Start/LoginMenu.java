package io.github.Graphic.View.Start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.StartMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.Model.Result;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.MainMenu;

public class LoginMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label username, password, result;
    private final TextField usernameField, passwordField;
    private final TextButton loginButton, backButton, forgetPasswordButton;

    public LoginMenu () {
        Skin skin = TillDown.getSkin();

        username = new Label(App.getLanguage("signUp.username"), skin);
        password = new Label(App.getLanguage("signUp.password"), skin);
        result = new Label("", skin);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);


        loginButton = new TextButton(App.getLanguage("button.login"), skin);
        forgetPasswordButton = new TextButton(App.getLanguage("button.forgetPassword"), skin);
        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();
        table.add(result).width(300).pad(20).row();

        table.add(username);
        table.add(usernameField).width(300).pad(5).row();

        table.add(password);
        table.add(passwordField).width(300).pad(5).row();

        table.row();
        table.add(forgetPasswordButton).width(400).pad(10);
        table.add(loginButton).width(150).pad(10);

        table.row();
        table.add(backButton).width(200).pad(15);

        stage.addActor(table);

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginButtonClicked();
            }
        });
        forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                forgetPasswordButtonClicked();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new StartMenu());
            }
        });
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

    private void loginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Result resultController = StartMenuController.login(username, password);

        if (resultController.isSuccess())
            TillDown.getGame().setScreen(new MainMenu());
        result.setText(resultController.getMessage());
        result.setColor(Color.RED);
    }

    public void forgetPasswordButtonClicked() {
        String username = usernameField.getText();

        Result resultController = StartMenuController.forgetPassword(username);

        if (resultController.isSuccess())
            TillDown.getGame().setScreen(new ForgetPasswordMenu());
        result.setText(resultController.getMessage());
        result.setColor(Color.RED);
    }
}
