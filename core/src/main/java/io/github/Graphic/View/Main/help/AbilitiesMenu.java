package io.github.Graphic.View.Main.help;

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
import io.github.Graphic.Model.App;
import io.github.Graphic.Model.enums.Ability;
import io.github.Graphic.TillDown;

public class AbilitiesMenu implements Screen {
    private final Stage stage;
    private final Table table;
    private final TextButton backButton;

    private final int where;

    public AbilitiesMenu(int where) {
        this.where = where;

        Skin skin = TillDown.getSkin();
        stage = new Stage(new ScreenViewport());
        table = new Table();
        backButton = new TextButton(App.getLanguage("button.back"), skin);

        table.setFillParent(true);
        table.top().pad(20);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table.add(new Label(App.getLanguage("title.ability"), TillDown.getSkin(), "title")).colspan(3).padBottom(30);
        table.row();

        for (Ability ability : Ability.values()) {
            Table abilityCard = createAbilityCard(ability);
            table.add(abilityCard).pad(10);

            if (ability.ordinal() % 3 == 2) table.row();
        }

        table.add(backButton).colspan(3).width(200).padTop(30);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new HelpMenu(where));
            }
        });
    }

    private Table createAbilityCard(Ability ability) {
        Skin skin = TillDown.getSkin();
        Table card = new Table(skin);

        Texture texture = new Texture(Gdx.files.internal(ability.getPath()));
        Image image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
        image.setScaling(Scaling.fit);

        Label name = new Label(ability.getName(), skin, "title");
        Label description = new Label(ability.getDescription(), skin);

        card.add(image).size(100, 100).colspan(2).pad(10);
        card.row();
        card.add(name).colspan(2).padBottom(10);
        card.row();
        card.add(description).colspan(2).pad(5);

        return card;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
