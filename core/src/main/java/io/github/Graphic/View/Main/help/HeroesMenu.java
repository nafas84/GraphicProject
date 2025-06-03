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
import io.github.Graphic.Model.enums.HeroType;
import io.github.Graphic.TillDown;

public class HeroesMenu implements Screen {
    private final Stage stage;
    private final Table table;
    private final TextButton backButton;

    private final int where;

    public HeroesMenu(int where) {
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

        table.add(new Label(App.getLanguage("title.hero"), TillDown.getSkin(), "title")).colspan(2).padBottom(30);
        table.row();

        for (HeroType hero : HeroType.values()) {
            Table heroCard = createHeroCard(hero);
            table.add(heroCard).pad(10);

            if (hero.ordinal() % 3 == 2) table.row();
        }

        table.add(backButton).colspan(2).width(200).pad(20);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new HelpMenu(where));
            }
        });
    }

    private Table createHeroCard(HeroType hero) {
        Skin skin = TillDown.getSkin();
        Table card = new Table(skin);

        Texture texture = new Texture(Gdx.files.internal(hero.getAssetFolderPath() + "/idle/Idle_0.png"));
        Image image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
        image.setScaling(Scaling.fit);

        Label name = new Label(hero.getName(), skin, "title");
        Label speed = new Label(App.getLanguage("hero.speed") + " " + hero.getBaseSpeed(), skin);
        Label life = new Label(App.getLanguage("hero.life") + " " + hero.getBaseLife(), skin);
        Label hp = new Label(App.getLanguage("hero.HP") +" " + hero.getMaxHP(), skin);

        card.add(image).size(150, 150).colspan(2).pad(10);
        card.row();
        card.add(name).colspan(2).padBottom(10);
        card.row();
        card.add(speed).left().pad(5);
        card.add(life).left().pad(5);
        card.row();
        card.add(hp).left().colspan(2).pad(5);

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
