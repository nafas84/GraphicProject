package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Graphic.Model.enums.AssetType;

import java.util.HashMap;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final HashMap<AssetType, Texture> textures = new HashMap<>();
    private final HashMap<AssetType, Sprite> sprites = new HashMap<>();
    private final HashMap<AssetType, Animation<Texture>> animations = new HashMap<>();

    private GameAssetManager() {
        for (AssetType assetType: AssetType.values()) {
            Texture texture = new Texture(Gdx.files.internal(getPath(assetType)));
            // load sprite & texture
            textures.put(assetType, texture);
            sprites.put(assetType, new Sprite(texture));
            // load animations
            if (assetType.equals(AssetType.IdleHero) || assetType.equals(AssetType.RunHero) || assetType.equals(AssetType.HoleyShield)) {
                loadAnimation(assetType);
            }
        }
    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    //-------------------
    public Texture getTexture(AssetType assetType) {
        return textures.get(assetType);
    }

    public Sprite getSprite(AssetType assetType) {
        return sprites.get(assetType);
    }

    public Animation<Texture> getAnimation(AssetType assetType) {
        return animations.get(assetType);
    }

    public void loadAnimation(AssetType assetType) {
        if (!assetType.equals(AssetType.HoleyShield)) {
            Player player = App.getGame().getPlayer();
            String path = (assetType.equals(AssetType.IdleHero)) ? player.getHero().getAssetFolderPath() + "/idle/Idle_" : player.getHero().getAssetFolderPath() + "/run/Run_";
            int frameCounter = (assetType.equals(AssetType.IdleHero)) ? 6 : 4;

            Texture[] frames = new Texture[frameCounter];
            for (int i = 0; i < frameCounter; i++) {
                frames[i] = new Texture(Gdx.files.internal(path + i + ".png"));
            }
            animations.put(assetType, new Animation<>(0.1f, frames));
        } else {
            String path = "assets/etc/HolyShield/HolyShield_";
            int frameCounter = 7;

            Texture[] frames = new Texture[frameCounter];
            for (int i = 0; i < frameCounter; i++) {
                frames[i] = new Texture(Gdx.files.internal(path + i + ".png"));
            }
            animations.put(assetType, new Animation<>(0.1f, frames));
        }
    }

    private String getPath(AssetType assetType) {
        Player player = App.getGame().getPlayer();
        String folder = player.getHero().getAssetFolderPath();
        // for fixed obj (for animated obj -> get 0 idle frame)

        String result;
        switch (assetType) {
            case IdleHero:
                result = folder + "/idle/" + "Idle_0.png";
                break;
            case RunHero:
                result = folder + "/run/" + "Run_0.png";
                break;
            case Weapon:
                result = player.getWeapon().getAssetPath();
                break;
            case Bullet:
                result = "assets/weapon/bullet.png";
                break;
            case MonsterBullet:
                result = "assets/monster/eyebatMonster/T_EyeBat_EM.png";
                break;
            case BackGround:
                result = "assets/etc/background.png";
                break;
            case Seed:
                result = "assets/etc/BahmanHashemi.png";
                break;
            case HoleyShield:
                result = "assets/etc/HolyShield/HolyShield_0.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid assetType for getTexture/sprite(GameAssetManager)!");
        }
        return result;
    }
}
