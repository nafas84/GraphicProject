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

import java.io.IOException;

public class SignUpMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label username, password, securityQuestion, answer, result;
    private final TextField usernameField, passwordField, answerField;
    private final SelectBox securityQuestionBox;
    private final TextButton submitButton, backButton;


    public SignUpMenu () {
        Skin skin = TillDown.getSkin();

        username = new Label(App.getLanguage("signUp.username"), skin);
        password = new Label(App.getLanguage("signUp.password"), skin);
        securityQuestion = new Label(App.getLanguage("signUp.question"), skin);
        answer = new Label(App.getLanguage("signUp.answer"), skin);
        result = new Label("", skin);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        answerField = new TextField("", skin);

        securityQuestionBox = new SelectBox<>(skin);
        securityQuestionBox.setItems(
            App.getLanguage("signUp.securityQuestionBox.1"),
            App.getLanguage("signUp.securityQuestionBox.2"),
            App.getLanguage("signUp.securityQuestionBox.3"),
            App.getLanguage("signUp.securityQuestionBox.4"),
            App.getLanguage("signUp.securityQuestionBox.5")
        );

        submitButton = new TextButton(App.getLanguage("button.submit"), skin);
        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport());
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

        table.add(securityQuestion);
        table.add(securityQuestionBox).width(300).pad(10).row();

        table.add(answer);
        table.add(answerField).width(300).pad(10).row();

        table.row();
        table.add(backButton).width(150).padRight(10);
        table.add(submitButton).width(200).padLeft(10);

        stage.addActor(table);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    submitButtonClicked();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

    private void submitButtonClicked() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String question = securityQuestionBox.getSelected().toString();
        String answer = answerField.getText();

        Result resultController = StartMenuController.register(username, password, question, answer);

        if (resultController.isSuccess())
            TillDown.getGame().setScreen(new StartMenu());
        result.setText(resultController.getMessage());
        result.setColor(Color.RED);
    }
}
