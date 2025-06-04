package io.github.Graphic.View.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.MainMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.Main.help.HelpMenu;
import io.github.Graphic.View.Start.StartMenu;

public class MainMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label result;
    private final TextButton newGameButton, loadGameButton, profileButton, scoreboardButton, settingButton, talentButton, logoutButton;

    public MainMenu() {
        Skin skin = TillDown.getSkin();

        result = new Label("", skin);

        this.newGameButton = new TextButton(App.getLanguage("button.newGame"), skin);
        this.loadGameButton = new TextButton(App.getLanguage("button.loadGame"), skin);
        this.profileButton = new TextButton(App.getLanguage("button.profile"), skin);
        this.scoreboardButton = new TextButton(App.getLanguage("button.scoreboard"), skin);
        this.settingButton = new TextButton(App.getLanguage("button.setting"), skin);
        this.talentButton = new TextButton(App.getLanguage("button.talent"), skin);
        this.logoutButton = new TextButton(App.getLanguage("button.logout"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center().row();
        table.add(result).width(300).pad(20).row();

        table.add(newGameButton).width(325).pad(10);
        table.add(loadGameButton).width(325).pad(10).row();
        table.add(profileButton).width(300).pad(10);
        table.add(scoreboardButton).width(325).pad(10).row();
        table.add(settingButton).width(300).pad(10);
        table.add(talentButton).width(300).pad(10).row();
        table.add(logoutButton).width(250).pad(10).row();

        stage.addActor(table);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new PreGameMenu());
            }
        });
        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.getCurrentUser().getUsername().equals("Guest")) {
                    result.setText(App.getLanguage("main.error.login"));
                    result.setColor(Color.RED);
                } else {
                    if (!App.getCurrentUser().hasSavedGame()) {
                        result.setText(App.getLanguage("main.error.game"));
                        result.setColor(Color.RED);
                    } else {
                        MainMenuController.loadGame();
                    }
                }
            }
        });
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.getCurrentUser().getUsername().equals("Guest")) {
                    result.setText(App.getLanguage("main.error.login"));
                    result.setColor(Color.RED);
                } else {
                    TillDown.getGame().setScreen(new ProfileMenu());
                }
            }
        });
        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new ScoreboardMenu());
            }
        });
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new SettingMenu());
            }
        });
        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new HelpMenu(0));
            }
        });
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setCurrentUser(null);
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
