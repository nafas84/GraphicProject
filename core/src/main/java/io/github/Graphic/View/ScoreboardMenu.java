package io.github.Graphic.View;

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
    private final Skin skin;
    private final SelectBox<String> sortSelectBox;
    private final Table listTable;
    private final TextButton backButton;

    private List<User> allUsers;
    private final User currentUser;

    public ScoreboardMenu() {
        this.skin = TillDown.getSkin();
        this.stage = new Stage(new ScreenViewport());

        loadUsers();
        this.currentUser = App.getCurrentUser();

        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.pad(30);

        // SelectBox برای انتخاب نوع سورت
        sortSelectBox = new SelectBox<>(skin);
        sortSelectBox.setItems("Score", "Kill", "Best Time Live");
        sortSelectBox.setSelected("Score");

        listTable = new Table(skin);
        listTable.top().left();

        backButton = new TextButton(App.getLanguage("button.back"), skin);

        setupUI();
        updateList("Score");

        // تغییر سورت
        sortSelectBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateList(sortSelectBox.getSelected());
            }
        });
    }

    private void setupUI() {
        mainTable.add(new Label("Scoreboard", skin, "title")).colspan(4).center().padBottom(40).row();

        mainTable.add(new Label("Sort by:", skin)).left().padRight(20);
        mainTable.add(sortSelectBox).left().width(150).padBottom(20).row();

        // جدول لیست یوزرها
        mainTable.add(new Label("Rank", skin)).width(50);
        mainTable.add(new Label("Username", skin)).width(200);
        mainTable.add(new Label("Score", skin)).width(100);
        mainTable.add(new Label("Kills", skin)).width(100);
        mainTable.add(new Label("Best Time", skin)).width(150).row();

        mainTable.add(listTable).colspan(5).expand().fill().top().left().row();

        mainTable.add(backButton).colspan(5).center().padTop(30).width(150).height(50);

        stage.addActor(mainTable);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TillDown.getGame().setScreen(new io.github.Graphic.View.Main.SettingMenu());
            }
        });
    }

    private void updateList(String sortBy) {
        listTable.clear();

        // سورت کردن بر اساس انتخاب
        Comparator<User> comparator;
        switch (sortBy) {
            case "Kill":
                comparator = Comparator.comparingInt(User::getTotalKill).reversed();
                break;
            case "Best Time Live":
                comparator = Comparator.comparingInt(User::getBestTimeLive).reversed();
                break;
            case "Score":
            default:
                comparator = Comparator.comparingInt(User::getTotalScore).reversed();
                break;
        }

        List<User> sortedUsers = allUsers.stream()
            .sorted(comparator)
            .collect(Collectors.toList());

        // گرفتن 15 نفر اول
        List<User> top15 = sortedUsers.stream().limit(15).collect(Collectors.toList());

        // اگر کاربر فعلی در لیست 15 نفر نبود، اضافه‌ش کن
        if (!top15.contains(currentUser)) {
            top15.add(currentUser);
        }

        // اضافه کردن ردیف‌ها به جدول
        int rank = 1;
        for (User user : top15) {
            boolean isCurrentUser = user.getUsername().equals(currentUser.getUsername());

            Label rankLabel = new Label(String.valueOf(sortedUsers.indexOf(user) + 1), skin);
            Label usernameLabel = new Label(user.getUsername(), skin);
            Label scoreLabel = new Label(String.valueOf(user.getTotalScore()), skin);
            Label killLabel = new Label(String.valueOf(user.getTotalKill()), skin);
            Label bestTimeLabel = new Label(String.valueOf(user.getBestTimeLive()), skin);

            // جلوه بصری 3 نفر اول
            int userRank = sortedUsers.indexOf(user) + 1;
            if (userRank == 1) {
                rankLabel.setColor(Color.GOLD);
                usernameLabel.setColor(Color.GOLD);
                scoreLabel.setColor(Color.GOLD);
                killLabel.setColor(Color.GOLD);
                bestTimeLabel.setColor(Color.GOLD);
            } else if (userRank == 2) {
                rankLabel.setColor(Color.BLUE);
                usernameLabel.setColor(Color.BLUE);
                scoreLabel.setColor(Color.BLUE);
                killLabel.setColor(Color.BLUE);
                bestTimeLabel.setColor(Color.BLUE);
            } else if (userRank == 3) {
                rankLabel.setColor(Color.BROWN);
                usernameLabel.setColor(Color.BROWN);
                scoreLabel.setColor(Color.BROWN);
                killLabel.setColor(Color.BROWN);
                bestTimeLabel.setColor(Color.BROWN);
            }

            // اگر بازیکن فعلی هست جلوه متمایز بده
            if (isCurrentUser) {
                rankLabel.setColor(Color.CYAN);
                usernameLabel.setColor(Color.CYAN);
                scoreLabel.setColor(Color.CYAN);
                killLabel.setColor(Color.CYAN);
                bestTimeLabel.setColor(Color.CYAN);
            }

            listTable.add(rankLabel).width(50).pad(5);
            listTable.add(usernameLabel).width(200).pad(5);
            listTable.add(scoreLabel).width(100).pad(5);
            listTable.add(killLabel).width(100).pad(5);
            listTable.add(bestTimeLabel).width(150).pad(5).row();

            rank++;
        }
    }

    private void loadUsers() {
        List<User> users = new ArrayList<>();

        File baseDir = new File("data/users");
        File[] userDirs = baseDir.listFiles(File::isDirectory);

        if (userDirs == null) return;

        for (File dir : userDirs) {
            // TODO: save game with game.json
            File[] jsonFiles = dir.listFiles((file, name) -> name.endsWith(".json") && !name.equals("game.json"));
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
