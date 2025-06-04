package io.github.Graphic.View.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import io.github.Graphic.Model.App;
import io.github.Graphic.Model.User;
import io.github.Graphic.TillDown;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardMenu implements Screen {
    private final Stage stage;
    private final Table mainTable;
    private final SelectBox<String> sortSelectBox;
    private final TextButton backButton;

    private List<User> allUsers;
    private final User currentUser;

    public ScoreboardMenu() {
        Skin skin = TillDown.getSkin();
        this.stage = new Stage(new ScreenViewport(), App.getSharedBatch());

        // load users:
        loadUsers();
        this.currentUser = App.getCurrentUser();

        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.pad(30);

        sortSelectBox = new SelectBox<>(skin);
        sortSelectBox.setItems("Score", "Kill", "Time", "Username");
        sortSelectBox.setSelected("Score");


        backButton = new TextButton(App.getLanguage("button.back"), skin);

        updateList("Score");

        sortSelectBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateList(sortSelectBox.getSelected());
            }
        });
    }

    private void updateList(String sortBy) {
        Skin skin = TillDown.getSkin();
        mainTable.clear();


        mainTable.add(new Label(App.getLanguage("title.scoreboard"), skin, "title")).colspan(4).center().padBottom(40).row();

        mainTable.add(new Label(App.getLanguage("scoreboard.sortBy"), skin)).left().padRight(20);
        mainTable.add(sortSelectBox).left().width(150).padBottom(20).row();

        mainTable.add(new Label(App.getLanguage("scoreboard.rank"), skin)).width(50);
        mainTable.add(new Label(App.getLanguage("scoreboard.username"), skin)).width(200);
        mainTable.add(new Label(App.getLanguage("scoreboard.score"), skin)).width(100);
        mainTable.add(new Label(App.getLanguage("scoreboard.kill"), skin)).width(100);
        mainTable.add(new Label(App.getLanguage("scoreboard.time"), skin)).width(150).row();

        Comparator<User> comparator;
        switch (sortBy) {
            case "Kill":
                comparator = Comparator.comparingInt(User::getTotalKill).reversed();
                break;
            case "Time":
                comparator = Comparator.comparingDouble(User::getBestTimeLive).reversed();
                break;
            case "Username":
                comparator = Comparator.comparing(User::getUsername);
                break;
            case "Score":
            default:
                comparator = Comparator.comparingInt(User::getTotalScore).reversed();
                break;
        }

        List<User> sortedUsers = allUsers.stream()
            .sorted(comparator)
            .collect(Collectors.toList());

        List<User> top15 = sortedUsers.stream().limit(15).collect(Collectors.toList());

        if (!top15.contains(currentUser)) {
            top15.add(currentUser);
        }

        int rank = 1;
        for (User user : top15) {
            boolean isCurrentUser = user.getUsername().equals(currentUser.getUsername());

            Label rankLabel = new Label(String.valueOf(sortedUsers.indexOf(user) + 1), skin);
            Label usernameLabel = new Label(user.getUsername(), skin);
            Label scoreLabel = new Label(String.valueOf(user.getTotalScore()), skin);
            Label killLabel = new Label(String.valueOf(user.getTotalKill()), skin);
            Label bestTimeLabel = new Label(formatTime(user.getBestTimeLive()), skin);

            int userRank = sortedUsers.indexOf(user) + 1;
            if (userRank == 1) {
                rankLabel.setColor(Color.GOLD);
                usernameLabel.setColor(Color.GOLD);
                scoreLabel.setColor(Color.GOLD);
                killLabel.setColor(Color.GOLD);
                bestTimeLabel.setColor(Color.GOLD);
            } else if (userRank == 2) {
                rankLabel.setColor(Color.PURPLE);
                usernameLabel.setColor(Color.PURPLE);
                scoreLabel.setColor(Color.PURPLE);
                killLabel.setColor(Color.PURPLE);
                bestTimeLabel.setColor(Color.PURPLE);
            } else if (userRank == 3) {
                rankLabel.setColor(Color.BROWN);
                usernameLabel.setColor(Color.BROWN);
                scoreLabel.setColor(Color.BROWN);
                killLabel.setColor(Color.BROWN);
                bestTimeLabel.setColor(Color.BROWN);
            }

            if (isCurrentUser) {
                rankLabel.setColor(Color.GREEN);
                usernameLabel.setColor(Color.GREEN);
                scoreLabel.setColor(Color.GREEN);
                killLabel.setColor(Color.GREEN);
                bestTimeLabel.setColor(Color.GREEN);
            }

            mainTable.add(rankLabel).width(50).pad(5);
            mainTable.add(usernameLabel).width(200).pad(5);
            mainTable.add(scoreLabel).width(100).pad(5);
            mainTable.add(killLabel).width(100).pad(5);
            mainTable.add(bestTimeLabel).width(150).pad(5).row();

            rank++;
        }

        mainTable.add(backButton).width(150);

        stage.addActor(mainTable);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new MainMenu());
            }
        });

    }

    private String formatTime(float seconds) {
        int min = (int) seconds / 60;
        int sec = (int) seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private void loadUsers() {
        List<User> users = new ArrayList<>();

        File baseDir = new File("data/users");
        File[] userDirs = baseDir.listFiles(File::isDirectory);

        if (userDirs == null) return;

        for (File dir : userDirs) {
            // TODO: save game with game.json
            File[] jsonFiles = dir.listFiles((file, name) -> name.endsWith(".json") && !name.equals("Game.json"));
            if (jsonFiles == null) continue;

            for (File jsonFile : jsonFiles) {
                try (FileReader reader = new FileReader(jsonFile)) {
                    User user = new Gson().fromJson(reader, User.class);
                    if (user != null) {
                        users.add(user);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.allUsers = new ArrayList<>(users);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        App.getShader().setUniformi("u_grayscale", App.isGrayscale() ? 1 : 0);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
