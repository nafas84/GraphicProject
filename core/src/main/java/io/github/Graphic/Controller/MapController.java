package io.github.Graphic.Controller;

import io.github.Graphic.Model.App;
import io.github.Graphic.Model.enums.AssetType;
import io.github.Graphic.Model.GameAssetManager;
import io.github.Graphic.TillDown;

public class MapController {
    private final GameAssetManager gameAssetManager = GameAssetManager.getGameAssetManager();

    public void update() {
        TillDown.getBatch().draw(gameAssetManager.getTexture(AssetType.BackGround), 0, 0);
    }
}
