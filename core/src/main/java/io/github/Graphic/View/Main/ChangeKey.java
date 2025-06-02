package io.github.Graphic.View.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;

public class ChangeKey implements Screen {
    private final Stage stage;
    private final Table table;

    // حوصله نداشتم ولی اوتو ایم همون چیت باس فایته
    private final Label help, moveUp, moveDown, moveLeft, moveRight, autoAim, reloadWeapon, cheatTime, cheatLevel, cheatLife, cheatHp, pauseGame, shash;
    private final TextButton moveUpButton, moveDownButton, moveLeftButton, moveRightButton, autoAimButton,pauseGameButton,  reloadWeaponButton, cheatTimeButton, cheatLevelButton, cheatLifeButton, cheatHpButton, backButton;

    public ChangeKey() {
        Skin skin = TillDown.getSkin();

        shash = new Label("     ", skin);
        help = new Label(App.getLanguage("changeKey.help"), skin);
        moveUp = new Label(App.getLanguage("up"), skin);
        moveDown = new Label(App.getLanguage("down"), skin);
        moveLeft = new Label(App.getLanguage("left"), skin);
        moveRight = new Label(App.getLanguage("right"), skin);
        autoAim = new Label(App.getLanguage("autoAim"), skin);
        reloadWeapon = new Label(App.getLanguage("reload"), skin);
        cheatTime = new Label(App.getLanguage("cheatTime"), skin);
        cheatLevel = new Label(App.getLanguage("cheatLevel"), skin);
        cheatLife = new Label(App.getLanguage("cheatLife"), skin);
        cheatHp = new Label(App.getLanguage("cheatHp"), skin);
        pauseGame = new Label(App.getLanguage("pause"), skin);

        moveUpButton = new TextButton(Input.Keys.toString(App.getKeyManager().getMoveUp()), skin);
        moveDownButton = new TextButton(Input.Keys.toString(App.getKeyManager().getMoveDown()), skin);
        moveLeftButton = new TextButton(Input.Keys.toString(App.getKeyManager().getMoveLeft()), skin);
        moveRightButton = new TextButton(Input.Keys.toString(App.getKeyManager().getMoveRight()), skin);
        autoAimButton = new TextButton(Input.Keys.toString(App.getKeyManager().getCheatBossFight()), skin);
        reloadWeaponButton = new TextButton(Input.Keys.toString(App.getKeyManager().getReloadWeapon()), skin);
        cheatTimeButton = new TextButton(Input.Keys.toString(App.getKeyManager().getCheatTime()), skin);
        cheatLevelButton = new TextButton(Input.Keys.toString(App.getKeyManager().getCheatLevel()), skin);
        cheatLifeButton = new TextButton(Input.Keys.toString(App.getKeyManager().getCheatLife()), skin);
        cheatHpButton = new TextButton(Input.Keys.toString(App.getKeyManager().getCheatHp()), skin);
        pauseGameButton = new TextButton(Input.Keys.toString(App.getKeyManager().getPauseGame()), skin);

        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table = new Table();
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center().row();
        table.add(help).width(100).pad(20).top().left().row();

        table.add(moveUp);
        table.add(moveUpButton).width(300).pad(10);
        table.add(cheatTime);
        table.add(cheatTimeButton).width(300).pad(10).row();

        table.add(moveDown);
        table.add(moveDownButton).width(325).pad(10);
        table.add(cheatLevel);
        table.add(cheatLevelButton).width(325).pad(10).row();

        table.add(moveLeft);
        table.add(moveLeftButton).width(325).pad(10);
        table.add(cheatLife);
        table.add(cheatLifeButton).width(325).pad(10).row();

        table.add(moveRight);
        table.add(moveRightButton).width(325).pad(10);
        table.add(cheatHp);
        table.add(cheatHpButton).width(325).pad(10).row();

        table.add(autoAim);
        table.add(autoAimButton).width(325).pad(10);
        table.add(pauseGame);
        table.add(pauseGameButton).width(325).pad(10).row();

        table.add(reloadWeapon);
        table.add(reloadWeaponButton).width(325).pad(10);
        table.add(shash);
        table.add(backButton);

        stage.addActor(table);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new SettingMenu());
            }
        });
        moveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moveUpButton.setText("...");

                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setMoveUp(keycode);
                        moveUpButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });
        moveDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moveDownButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setMoveDown(keycode);
                        moveDownButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        moveLeftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moveLeftButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setMoveLeft(keycode);
                        moveLeftButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        moveRightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moveRightButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setMoveRight(keycode);
                        moveRightButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        autoAimButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                autoAimButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setCheatBossFight(keycode);
                        autoAimButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        reloadWeaponButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reloadWeaponButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setReloadWeapon(keycode);
                        reloadWeaponButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        cheatTimeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatTimeButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setCheatTime(keycode);
                        cheatTimeButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        cheatLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatLevelButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setCheatLevel(keycode);
                        cheatLevelButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        cheatLifeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatLifeButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setCheatLife(keycode);
                        cheatLifeButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        cheatHpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatHpButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setCheatHp(keycode);
                        cheatHpButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
            }
        });

        pauseGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGameButton.setText("...");
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        App.getKeyManager().setPauseGame(keycode);
                        pauseGameButton.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(stage);
                        return true;
                    }
                });
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
