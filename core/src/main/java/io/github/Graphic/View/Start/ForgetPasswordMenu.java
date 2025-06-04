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

public class ForgetPasswordMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label securityQuestion, question, answer, result, newPassword;
    private final TextField passwordField, answerField;
    private final TextButton submitButton, backButton;

    public ForgetPasswordMenu() {
        Skin skin = TillDown.getSkin();

        securityQuestion = new Label(App.getLanguage("signUp.question"), skin);
        question = new Label(App.getCurrentUser().getQuestion(), skin);
        answer = new Label(App.getLanguage("signUp.answer"), skin);
        newPassword = new Label(App.getLanguage("forgetPassword.newPassword"), skin);
        result = new Label("", skin);

        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        answerField = new TextField("", skin);

        submitButton = new TextButton(App.getLanguage("button.submit"), skin);
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

        table.add(securityQuestion);
        table.add(question).width(300).pad(5).row();

        table.add(answer);
        table.add(answerField).width(300).pad(10).row();

        table.add(newPassword);
        table.add(passwordField).width(300).pad(10).row();

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
                TillDown.getGame().setScreen(new LoginMenu());
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

    private void submitButtonClicked() throws IOException {
        String answer = answerField.getText();
        String password = passwordField.getText();

        Result resultController = StartMenuController.newPassword(answer, password);

        if (resultController.isSuccess())
            TillDown.getGame().setScreen(new LoginMenu());
        result.setText(resultController.getMessage());
        result.setColor(Color.RED);
    }
}
