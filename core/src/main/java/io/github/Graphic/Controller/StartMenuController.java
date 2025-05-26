package io.github.Graphic.Controller;

import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
import io.github.Graphic.Model.App;
import io.github.Graphic.Model.Result;
import io.github.Graphic.Model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StartMenuController {
    public static Result register(String username, String password, String question, String answer) throws IOException {
        if (username.isEmpty() || password.isEmpty() || answer.isEmpty()) {
            return new Result(false, App.getLanguage("error.empty"));
        } else if (App.getUserByUsername(username) != null) {
            return new Result(false, App.getLanguage("username.error"));
        } else if (password.length() < 8) {
            return new Result(false, App.getLanguage("password.error.len"));
        } else if (!password.matches(".*[0-9].*")) {
            return new Result(false, App.getLanguage("password.error.number"));
        } else if (!password.matches(".*[A-Z].*")) {
            return new Result(false, App.getLanguage("password.error.upperCase"));
        } else if (!password.matches(".*[!@#$%^&*()].*")) {
            return new Result(false, App.getLanguage("password.error.special"));
        }

        int random = MathUtils.random(1, 8);
        User user = new User(username, password, question, answer, "assets/defaultAvatar/avatar" + random + ".jpg");
        File userDir = new File("data/users/" + user.getId());
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        FileWriter writer = new FileWriter(userDir.getPath() + "/" + username + ".json");
        new Gson().toJson(user, writer);
        writer.close();

        return new Result(true, "");
    }

    public static Result login(String username, String password) {
        User user = App.getUserByUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            return new Result(false, App.getLanguage("error.empty"));
        } else if (user == null) {
            return new Result(false, App.getLanguage("login.username.error"));
        } else if (!user.getPassword().equals(password)) {
            return new Result(false, App.getLanguage("login.password.error"));
        }

        App.setCurrentUser(user);
        return new Result(true, "");
    }

    public static Result forgetPassword(String username) {
        User user = App.getUserByUsername(username);

        if (username.isEmpty()) {
            return new Result(false, App.getLanguage("error.empty"));
        } else if (user == null) {
            return new Result(false, App.getLanguage("login.username.error"));
        }

        App.setCurrentUser(user);
        return new Result(true, "");
    }

    public static Result newPassword(String answer, String newPassword) throws IOException {
        if (answer.isEmpty() || newPassword.isEmpty()) {
            return new Result(false, App.getLanguage("error.empty"));
        } else if (!App.getCurrentUser().getAnswer().equals(answer)) {
            return new Result(false, App.getLanguage("forgetPassword.answer.error"));
        } else if (newPassword.length() < 8) {
            return new Result(false, App.getLanguage("password.error.len"));
        } else if (!newPassword.matches(".*[0-9].*")) {
            return new Result(false, App.getLanguage("password.error.number"));
        } else if (!newPassword.matches(".*[A-Z].*")) {
            return new Result(false, App.getLanguage("password.error.upperCase"));
        } else if (!newPassword.matches(".*[!@#$%^&*()].*")) {
            return new Result(false, App.getLanguage("password.error.special"));
        }

        App.getCurrentUser().setPassword(newPassword);
        App.updateCurrentUser();
        return new Result(true, "User successfully created.");
    }

    public static void guestMode() {
        App.setCurrentUser(App.getUserByUsername("Guest"));
    }
}
