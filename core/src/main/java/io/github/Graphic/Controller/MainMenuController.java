package io.github.Graphic.Controller;

import com.google.gson.Gson;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.SaveData.GameData;
import io.github.Graphic.Model.SaveData.MonsterData;
import io.github.Graphic.Model.SaveData.PlayerData;
import io.github.Graphic.Model.SaveData.SeedData;
import io.github.Graphic.Model.enums.HeroType;
import io.github.Graphic.Model.enums.WeaponType;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.GameView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenuController {
    public static void appSetting(String music, float volume,
                                  boolean sound, boolean autoReload,
                                  boolean color, String language) {
        App.setMusic(music);
        App.setMusicVolume(volume);
        //TODO:
        App.setIsSfx(sound);
        App.setIsAutoReload(autoReload);
        App.setGrayscale(color);

        App.setLanguage(language);
    }

    public static Result changeUserInfo(String username, String password) throws IOException {
        if (username.isEmpty() && password.isEmpty()) {
            return new Result(false, App.getLanguage("profile.empty.change"));
        }

        if (!username.isEmpty()) {
            if (App.getUserByUsername(username) != null) {
                return new Result(false, App.getLanguage("username.error"));
            }

            String oldUsername = App.getCurrentUser().getUsername();
            App.getCurrentUser().setUsername(username);

            Gson gson = new Gson();

            File userDir = new File("data/users/" + App.getCurrentUser().getId());
            File oldFile = new File(userDir, oldUsername + ".json");
            File newFile = new File(userDir, username + ".json");

            try (FileWriter writer = new FileWriter(newFile)) {
                gson.toJson(App.getCurrentUser(), writer);
            }

            if (oldFile.exists()) {
                oldFile.delete();
            }
        }


        if (!password.isEmpty()) {
             if (password.length() < 8) {
                return new Result(false, App.getLanguage("password.error.len"));
            } else if (!password.matches(".*[0-9].*")) {
                return new Result(false, App.getLanguage("password.error.number"));
            } else if (!password.matches(".*[A-Z].*")) {
                return new Result(false, App.getLanguage("password.error.upperCase"));
            } else if (!password.matches(".*[!@#$%^&*()].*")) {
                return new Result(false, App.getLanguage("password.error.special"));
            }

             App.getCurrentUser().setPassword(password);
             App.updateUser();
        }


        return new Result(true, App.getLanguage("profile.suc"));
    }

    public static void deleteAccount() {
        File baseDir = new File("data/users");
        File[] userDirs = baseDir.listFiles(File::isDirectory);

        if (userDirs == null) return;

        for (File dir : userDirs) {
            File file = new File(dir, App.getCurrentUser().getUsername() + ".json");
                if (file.exists()) file.delete();
        }
        App.setCurrentUser(null);
    }

    public static void changeDefaultAvatar(int index) throws IOException {
        App.getCurrentUser().setAvatarPath("assets/defaultAvatar/avatar" + index + ".jpg");
        App.updateUser();
    }

    public static void newGame(String hero, String weapon, int time) {
        Hero heroPlayer = new Hero(getHeroType(hero));
        Weapon weaponPlayer = new Weapon(getWeaponType(weapon));
        Player player = new Player(heroPlayer, weaponPlayer, App.getCurrentUser().getId());

        App.setGame(new Game(player, time));
        GameController.initializeGame();

        TillDown.getGame().setScreen(new GameView(new GameController()));
    }

    private static HeroType getHeroType(String nameHero) {
        for (HeroType heroType: HeroType.values()) {
            if (heroType.getName().equals(nameHero)) {
                return heroType;
            }
        }
        return null;
    }

    private static WeaponType getWeaponType(String nameWeapon) {
        for (WeaponType weaponType: WeaponType.values()) {
            if (weaponType.getName().equals(nameWeapon)) {
                return weaponType;
            }
        }
        return null;
    }

    public static void loadGame() {
        GameData gameData = getGameData();
        if (gameData == null)
            throw new RuntimeException("game data not found!");

        float totalTime = gameData.getTotalTime();
        float remainingTime = gameData.getRemainingTime();

        // make player:
        PlayerData playerData = gameData.getPlayerData();

        Hero hero = new Hero(playerData.getHeroType());
        Weapon weapon = new Weapon(playerData.getWeaponType());

        Player player = new Player(hero, weapon,
            playerData.getId(), playerData.getX(), playerData.getY(),
            playerData.getXp(), playerData.getHp(), playerData.getKill(), playerData.getLevel());

        // make monsters:
        List<MonsterData> monsterDataList = gameData.getMonsterDataList();
        List<Monster> monsters = new ArrayList<>();

        for (MonsterData data: monsterDataList) {
            monsters.add(new Monster(data.getType(), data.getX(), data.getY()));
        }

        // make seeds:
        List<SeedData> seedDataList = gameData.getSeedDataList();
        List<Seed> seeds = new ArrayList<>();

        for (SeedData data: seedDataList) {
            seeds.add(new Seed(data.getX(), data.getY()));
        }

        Game game = new Game(player, totalTime, remainingTime, monsters, seeds);

        App.setGame(game);
        TillDown.getGame().setScreen(new GameView(new GameController()));
    }

    private static GameData getGameData() {
        File file = new File("data/users/" + App.getCurrentUser().getId() + "/Game.json");

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, GameData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
