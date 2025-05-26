package io.github.Graphic.Controller;

import io.github.Graphic.Model.App;
import io.github.Graphic.Model.AssetType;
import io.github.Graphic.Model.GameAssetManager;
import io.github.Graphic.TillDown;

public class MapController {
    private final GameAssetManager gameAssetManager = GameAssetManager.getGameAssetManager();

    public void update() {
        float backgroundX = App.getGame().getPlayer().getPosX();
        float backgroundY = App.getGame().getPlayer().getPosY();
        TillDown.getBatch().draw(gameAssetManager.getTexture(AssetType.BackGround), backgroundX, backgroundY);
    }
}
