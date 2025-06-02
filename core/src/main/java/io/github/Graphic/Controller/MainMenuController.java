package io.github.Graphic.Controller;

import com.google.gson.Gson;
import io.github.Graphic.Model.*;
import io.github.Graphic.Model.enums.HeroType;
import io.github.Graphic.Model.enums.WeaponType;
import io.github.Graphic.TillDown;
import io.github.Graphic.View.GameView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainMenuController {
    public static void appSetting(String music, float volume,
                                  boolean sound, boolean autoReload,
                                  boolean color, String language) {
        App.setMusic(music);
        App.setMusicVolume(volume);
        //TODO:
        App.setIsSfx(sound);
        App.setIsAutoReload(autoReload);
        App.setIsColored(color);

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
}
