package io.github.Graphic.Model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final float totalTime;
    private float timeRemaining; //

    private final Player player;
    private final List<Monster> monsters = new ArrayList<>();


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
}
