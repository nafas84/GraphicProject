package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.I18NBundle;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class App {
    private static User currentUser;
    private static Game Game;

    // language:
    private static I18NBundle bundle;
    private static String languageName = "English";// English , Italian, French
    private static final KeyManager keyManager = new KeyManager();

    private static boolean isAutoReload = true;

    // sfx:
    private static boolean isSfx = true;
    private static final Sound singleShot, wastelandCombat,
        obtainPoints1, obtainPoints2, obtainPoints3, obtainPoints4, obtainPoints5, obtainPoints6,
        levelUp, ahmagh;

    // grayscale:
    private static boolean grayscale = false;
    private static ShaderProgram grayscaleShader;
    private static final SpriteBatch sharedBatch = new SpriteBatch();

    // music:
    private static float musicVolume = 1f;// 0-1
    private static Music currentMusic;
    private static String musicName = "---"; // --- , Alireza Roozegar - Nari Nari, Avantasia - The Haunting, Sabaton -  The Attack of the dead Men, Tribulation - The Wilderness

    static {
        FileHandle baseFileHandle = Gdx.files.internal("i18n/messages");
        Locale defaultLocale = new Locale("en");
        bundle = I18NBundle.createBundle(baseFileHandle, defaultLocale);
    }

    static {
        if (grayscaleShader == null) {
            ShaderProgram.pedantic = false;
            grayscaleShader = new ShaderProgram(
                Gdx.files.internal("assets/shaders/grayscale.vert"),
                Gdx.files.internal("assets/shaders/grayscale.frag")
            );

        }
        sharedBatch.setShader(grayscaleShader);
    }

    static {
        singleShot = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/single_shot.wav"));
        wastelandCombat = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Wasteland_Combat.wav"));

        obtainPoints1 = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Obtain_Points_01.wav"));
        obtainPoints2 = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Obtain_Points_02.wav"));
        obtainPoints3 = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Obtain_Points_03.wav"));
        obtainPoints4 = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Obtain_Points_04.wav"));
        obtainPoints5 = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Obtain_Points_05.wav"));
        obtainPoints6 = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Obtain_Points_06.wav"));

        levelUp = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/levelUp.wav"));
        ahmagh = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/Ahmagh.wav"));

    }

    public static Sound getSoundSingleShot() {
        return singleShot;
    }

    public static Sound getSoundWastelandCombat() {
        return wastelandCombat;
    }

    public static Sound getSoundObtainPoints() {
        int number = MathUtils.random(1, 6);

        switch (number) {
            case 1: return obtainPoints1;
            case 2: return obtainPoints2;
            case 3: return obtainPoints3;
            case 4: return obtainPoints4;
            case 5: return obtainPoints5;
            case 6: return obtainPoints6;
            default: return obtainPoints1;
        }
    }

    public static Sound getSoundLevelUp() {
        return levelUp;
    }

    public static Sound getSoundAhmagh() {
        return ahmagh;
    }

    public static ShaderProgram getShader() {
        return grayscaleShader;
    }

    public static SpriteBatch getSharedBatch() {
        return sharedBatch;
    }
    public static KeyManager getKeyManager() {
        return keyManager;
    }

    public static I18NBundle getBundle() {
        return bundle;
    }

    public static void setGame(Game game) {
        Game = game;
    }

    public static void setMusicVolume(float musicVolume) {
        App.musicVolume = musicVolume;
        if (currentMusic != null) {
            currentMusic.setVolume(musicVolume);
        }
    }

    public static void setIsSfx(boolean soundEffectEnable) {
        App.isSfx = soundEffectEnable;
    }

    public static void setGrayscale(boolean grayscale) {
        App.grayscale = grayscale;
    }

    public static void setIsAutoReload(boolean isAutoReload) {
        App.isAutoReload = isAutoReload;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    public static void setLanguage(String languageName) {
        App.languageName = languageName;
        String languageCode;
        switch (languageName) {
            case "French":
                languageCode = "fr";
                break;
            case "Italian":
                languageCode = "it";
                break;
            default:
                languageCode = "en";
        }
        FileHandle baseFileHandle = Gdx.files.internal("i18n/messages");
        Locale locale = new Locale(languageCode);
        bundle = I18NBundle.createBundle(baseFileHandle, locale);
    }

    public static void setMusic(String fileName) {
        App.musicName = fileName;
        if (fileName.equals("---")) {
            if (currentMusic != null) {
                currentMusic.stop();
                currentMusic.dispose();
            }
            return;
        }

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/" + fileName + ".mp3"));
        currentMusic.play();
    }

    public static String getLanguage(String key) {
        return bundle.get(key);
    }

    public static User getUserByUsername(String username) {
        if (username == null) return null;

        File baseDir = new File("data/users");
        File[] userDirs = baseDir.listFiles(File::isDirectory);

        if (userDirs == null) return null;

        for (File dir : userDirs) {
            File userFile = new File(dir, username + ".json");
            if (userFile.exists()) {
                try (FileReader reader = new FileReader(userFile)) {
                    return new Gson().fromJson(reader, User.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }


    public static int getNumberOfUsers() {
        File baseDir = new File("data/users");
        File[] userDirs = baseDir.listFiles(File::isDirectory);
        return (userDirs != null) ? userDirs.length : 0;
    }

    public static void updateUser() throws IOException {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) return;

        String username = currentUser.getUsername();

        File userFile = new File("data/users/" + currentUser.getId() + "/" + username + ".json");
        try (FileWriter writer = new FileWriter(userFile)) {
            new Gson().toJson(currentUser, writer);
        }
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static String getMusicName() {
        return musicName;
    }

    public static String getLanguageName() {
        return languageName;
    }

    public static Game getGame() {
        return Game;
    }

    public static boolean isIsSfx() {
        return isSfx;
    }

    public static boolean isIsAutoReload() {
        return isAutoReload;
    }

    public static boolean isGrayscale() {
        return grayscale;
    }

}
