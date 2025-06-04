package io.github.Graphic.View.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Graphic.Controller.MainMenuController;
import io.github.Graphic.Model.App;
import io.github.Graphic.TillDown;

public class SettingMenu implements Screen {
    private final Stage stage;
    private final Table table;

    private final Label selectMusic, music, language;
    private final SelectBox musicBox, languageBox;
    private final TextButton submitButton, backButton, changeButton;
    private final Slider volumeSlider;
    private final CheckBox soundCheckbox, autoReloadCheckbox, colorCheckbox;

    public SettingMenu () {
        Skin skin = TillDown.getSkin();

        selectMusic = new Label(App.getLanguage("setting.selectMusic"), skin);
        music = new Label(App.getLanguage("setting.music"), skin);
        language = new Label(App.getLanguage("setting.language"), skin);

        musicBox = new SelectBox<>(skin);
        musicBox.setItems(
            "---",
            "Alireza Roozegar - Nari Nari",
            "Avantasia - The Haunting",
            "Sabaton -  The Attack of the dead Men",
            "Tribulation - The Wilderness"
        );

        musicBox.setSelected(App.getMusicName());

        languageBox = new SelectBox<>(skin);
        languageBox.setItems(
            "English",
            "French",
            "Italian"
        );

        languageBox.setSelected(App.getLanguageName());

        submitButton = new TextButton(App.getLanguage("button.submit"), skin);
        backButton = new TextButton(App.getLanguage("button.back"), skin);
        changeButton = new TextButton(App.getLanguage("setting.changeKey"), skin);

        soundCheckbox = new CheckBox(" " + App.getLanguage("setting.sound"), skin);
        soundCheckbox.setChecked(App.isIsSfx());

        autoReloadCheckbox = new CheckBox(" " + App.getLanguage("setting.autoReload"), skin);
        autoReloadCheckbox.setChecked(App.isIsAutoReload());

        colorCheckbox = new CheckBox(" " + App.getLanguage("setting.color"), skin);
        colorCheckbox.setChecked(App.isGrayscale());

        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(App.getMusicVolume());

        table = new Table();
        stage = new Stage(new ScreenViewport(), App.getSharedBatch());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        table.add(selectMusic);
        table.add(musicBox).width(300).pad(5).row();

        table.add(music);
        table.add(volumeSlider).width(300).pad(5).row();

        table.add(language);
        table.add(languageBox).width(300).pad(5).row();

        table.add(soundCheckbox).width(300).pad(10).row();
        table.add(autoReloadCheckbox).width(300).pad(10).row();
        table.add(colorCheckbox).width(300).pad(10).row();

        table.row();
        table.add(changeButton).width(350).pad(10).row();
        table.add(backButton).width(200).padLeft(10);
        table.add(submitButton).width(200).padLeft(10);

        stage.addActor(table);

        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new ChangeKey());
            }
        });
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                submitButtonCheck();
                TillDown.getGame().setScreen(new MainMenu());
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new MainMenu());
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

    private void submitButtonCheck() {
        String music = musicBox.getSelected().toString();
        float volume = volumeSlider.getValue();
        boolean sound = soundCheckbox.isChecked();
        boolean autoReload = autoReloadCheckbox.isChecked();
        boolean color = colorCheckbox.isChecked();
        String language = languageBox.getSelected().toString();

        MainMenuController.appSetting(music, volume, sound, autoReload, color, language);
    }
}
