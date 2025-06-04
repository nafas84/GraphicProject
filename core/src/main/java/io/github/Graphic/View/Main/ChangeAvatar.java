package io.github.Graphic.View.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.MainMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;

import java.io.IOException;

public class ChangeAvatar implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label select, choose, result;
    private final SelectBox selectAvatarBox;
    private final TextButton submitButton, backButton;
    TextureRegionDrawable[] avatarOptions = new TextureRegionDrawable[8];

    public ChangeAvatar () {
        Skin skin = TillDown.getSkin();

        for (int i = 0; i < 8; i++) {
            avatarOptions[i] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("assets/defaultAvatar/avatar" + (i+1) + ".jpg"))));
        }

        select = new Label(App.getLanguage("avatar.select"), skin);
        choose = new Label(App.getLanguage("avatar.choose"), skin);
        result = new Label("", skin);

        backButton = new TextButton(App.getLanguage("button.back"), skin);
        submitButton = new TextButton(App.getLanguage("button.submit"), skin);

        selectAvatarBox = new SelectBox<>(skin);
        selectAvatarBox.setItems(1,2,3,4,5,6,7,8);

        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
    }

    @Override
    public void show() {
        Skin skin = TillDown.getSkin();

        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();
        table.add(result).width(300).pad(20).row();

        table.add(select).pad(10);
        table.add(selectAvatarBox).pad(10).row();

        for (int i = 0; i < 8; i++) {
            Image image = new Image(avatarOptions[i]);
            image.setSize(250, 250);

            Label indexLabel = new Label(String.valueOf(i + 1), skin);

            Table avatarCell = new Table();
            avatarCell.add(image).size(250, 250);
            avatarCell.row();
            avatarCell.add(indexLabel).padTop(5);

            table.add(avatarCell).pad(5);

            if ((i + 1) % 4 == 0) table.row();
        }

        table.row();
        table.add(backButton).width(150).pad(10);
        table.add(submitButton).width(200).pad(10);

        stage.addActor(table);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    MainMenuController.changeDefaultAvatar(selectAvatarBox.getSelectedIndex() + 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                TillDown.getGame().setScreen(new ProfileMenu());
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new ProfileMenu());
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
}
