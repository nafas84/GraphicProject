package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final float totalTime;
    private float timeRemaining; //

    private final Player player;
    private final List<Monster> monsters = new ArrayList<>();
    private final List<Seed> seeds = new ArrayList<>();

    public Game(Player player, int time) {
        this.totalTime = time * 60;
        this.timeRemaining = time * 60;
        this.player = player;
    }

    public void updateTime(float deltaTime) {
        timeRemaining -= deltaTime;
        if (timeRemaining < 0) timeRemaining = 0;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public void addTime(float seconds) {
        timeRemaining += seconds;
    }

    public void setTime(float newTime) {
        this.timeRemaining = newTime;
    }

    public Player getPlayer() {
        return player;
    }

    public float getPassedTime() {
        return totalTime - timeRemaining;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public String gameInfo() {
        int score = (int) (getPassedTime() * player.getKills());
        return App.getLanguage("gameInfo.battle") + "\n" +
            App.getLanguage("gameInfo.player") + "\n" + "         : " + player.getUsername() + "\n" +
            App.getLanguage("gameInfo.time") + "  : " + getPassedTime() + " seconds\n" +
            App.getLanguage("gameInfo.kills") + "  : " + player.getKills() + "\n" +
            App.getLanguage("gameInfo.score") + "  : " + score + "\n";
    }
}
