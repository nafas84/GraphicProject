package io.github.Graphic.View.Main;

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
import io.github.Graphic.Model.Result;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Start.StartMenu;

import java.io.IOException;

public class ProfileMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label changeUsername, changePassword, info, result;
    private final TextField usernameField, passwordField;
    private final TextButton submitButton, backButton, deleteButton, changeAvatarButton;
    private final Image avatarImage;

    public ProfileMenu () {
        Skin skin = TillDown.getSkin();

        changeUsername = new Label(App.getLanguage("profile.changeUsername"), skin);
        changePassword = new Label(App.getLanguage("profile.changePassword"), skin);
        info = new Label(App.getCurrentUser().getUserInfo(), skin);
        result = new Label("", skin);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        submitButton = new TextButton(App.getLanguage("button.submit"), skin);
        backButton = new TextButton(App.getLanguage("button.back"), skin);
        changeAvatarButton = new TextButton(App.getLanguage("profile.changeAvatar"), skin);
        deleteButton = new TextButton(App.getLanguage("profile.deleteAccount"), skin);

        Texture avatarTexture = new Texture(Gdx.files.internal(App.getCurrentUser().getAvatarPath()));
        avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
        avatarImage.setScaling(Scaling.fit);
        //avatarImage.setSize(64, 64);

        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();
        table.add(result).width(300).pad(20).row();

        table.add(avatarImage).size(250).pad(20);
        table.add(info).pad(10).row();

        table.add(changeUsername);
        table.add(usernameField).width(300).pad(10).row();

        table.add(changePassword);
        table.add(passwordField).width(300).pad(10).row();

        table.row();
        table.add(changeAvatarButton).width(375).padRight(10);
        table.add(deleteButton).width(375).padLeft(10).row();

        table.add(backButton).width(150).padRight(10).pad(10);
        table.add(submitButton).width(200).padLeft(10).pad(10);

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
                TillDown.getGame().setScreen(new MainMenu());
            }
        });
        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new ChangeAvatar());
            }
        });
        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuController.deleteAccount();
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

    private void submitButtonClicked() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Result resultController = MainMenuController.changeUserInfo(username, password);
        result.setText(resultController.getMessage());

        if (resultController.isSuccess()) {
            result.setColor(Color.GREEN);
        } else {
            result.setColor(Color.RED);
        }

        info.setText(App.getCurrentUser().getUserInfo());
    }
}
