package io.github.Graphic.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.MainMenu;
import io.github.Graphic.View.Main.PreGameMenu;

public class HelpMenu implements Screen {
    private Stage stage;
    private final Table table;
    private final int where;

    private final TextButton heroes, weapons, abilities, gameControllers, backButton;

    public HelpMenu(int where) {
        this.where = where;

        Skin skin = TillDown.getSkin();

        heroes = new TextButton(App.getLanguage("button.heroes"), skin);
        weapons = new TextButton(App.getLanguage("button.weapons"), skin);
        abilities = new TextButton(App.getLanguage("button.abilities"), skin);
        gameControllers = new TextButton(App.getLanguage("button.gameControllers"), skin);
        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center().row();

        table.add(heroes).width(200).pad(10).row();
        table.add(weapons).width(250).pad(10).row();
        table.add(abilities).width(250).pad(10).row();
        table.add(gameControllers).width(300).pad(10).row();
        table.add(backButton).width(150).pad(10);

        stage.addActor(table);

        heroes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new HeroesMenu());
            }
        });
        weapons.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new WeaponsMenu());
            }
        });
        abilities.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new AbilitiesMenu());
            }
        });
        gameControllers.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new ControllersMenu());
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (where == 0)
                    TillDown.getGame().setScreen(new MainMenu());
                else
                    TillDown.getGame().setScreen(new PreGameMenu());
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
