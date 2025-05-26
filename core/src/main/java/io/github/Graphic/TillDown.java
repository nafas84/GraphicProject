package io.github.Graphic;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.Graphic.View.Start.StartMenu;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class TillDown extends Game {
    private static TillDown game;
    private static SpriteBatch batch;
    private static Skin skin;

    @Override
    public void create() {
        game = this;
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        getGame().setScreen(new StartMenu());

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static TillDown getGame() {
        return game;
    }

    public static void setGame(TillDown game) {
        TillDown.game = game;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        TillDown.batch = batch;
    }

    public static Skin getSkin() {
        return skin;
    }
}
