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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.MainMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.Model.enums.HeroType;
import io.github.Graphic.Model.enums.WeaponType;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.help.HelpMenu;

public class PreGameMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label hero, weapon, time;
    private final SelectBox selectHeroBox, selectWeaponBox, selectTimeBox;
    private final TextButton newGameButton, backButton, helpButton;

    public PreGameMenu () {
        Skin skin = TillDown.getSkin();

        hero = new Label(App.getLanguage("preGame.hero"), skin);
        weapon = new Label(App.getLanguage("preGame.weapon"), skin);
        time = new Label(App.getLanguage("preGame.time"), skin);

        backButton = new TextButton(App.getLanguage("button.back"), skin);
        newGameButton = new TextButton(App.getLanguage("button.newGame"), skin);
        helpButton = new TextButton(App.getLanguage("button.talent"), skin);

        selectHeroBox = new SelectBox<>(skin);
        selectHeroBox.setItems("Dasher", "Diamond", "Lilith", "Scarlet", "Shana");

        selectWeaponBox = new SelectBox<>(skin);
        selectWeaponBox.setItems("Revolver", "Shotgun", "SMG");

        selectTimeBox = new SelectBox<>(skin);
        selectTimeBox.setItems(2, 5, 10, 20);


        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
    }

    @Override
    public void show() {
        Skin skin = TillDown.getSkin();

        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        table.add(hero).pad(10);
        table.add(selectHeroBox).pad(10).row();

        for (HeroType heroType: HeroType.values()) {
            TextureRegionDrawable imageRegion = new TextureRegionDrawable(
                new TextureRegion(new Texture(
                    Gdx.files.internal(heroType.getAssetFolderPath()) +
                        "/T_" +heroType.getName() + "_Portrait.png")));

            Image image = new Image(imageRegion);
            image.setScaling(Scaling.fit);
            image.setSize(250, 250);

            Label indexLabel = new Label(heroType.getName(), skin);

            Table avatarCell = new Table();
            avatarCell.add(image).size(250, 250);
            avatarCell.row();
            avatarCell.add(indexLabel).padTop(5);

            table.add(avatarCell).pad(5);
        }

        table.row();

        table.add(weapon).pad(10);
        table.add(selectWeaponBox).pad(10).row();

        for (WeaponType weaponType: WeaponType.values()) {
            TextureRegionDrawable imageRegion = new TextureRegionDrawable
                (new TextureRegion(new Texture(Gdx.files.internal(weaponType.getAssetPath()))));

            Image image = new Image(imageRegion);
            image.setSize(250, 250);

            Label indexLabel = new Label(weaponType.getName(), skin);

            Table avatarCell = new Table();
            avatarCell.add(image).size(250, 250);
            avatarCell.row();
            avatarCell.add(indexLabel).padTop(5);

            table.add(avatarCell).pad(5);
        }

        table.add(time).padLeft(100);
        table.add(selectTimeBox).padLeft(100);

        table.row();
        table.add(helpButton).width(150).pad(10);
        table.add(backButton).width(150).pad(10);
        table.add(newGameButton).width(300).pad(10);

        stage.addActor(table);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newGameButtonCheck();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new MainMenu());
            }
        });
        helpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new HelpMenu(1));
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

    private void newGameButtonCheck() {
        String hero = selectHeroBox.getSelected().toString();
        String weapon = selectWeaponBox.getSelected().toString();
        String time = selectTimeBox.getSelected().toString();

        MainMenuController.newGame(hero, weapon, Integer.parseInt(time));
    }
}

