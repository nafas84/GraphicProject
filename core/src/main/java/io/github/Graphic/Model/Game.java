package io.github.Graphic.Model;

public class Game {
    private final float initialTime;
    private float timeRemaining; //
    private final Player player;


    public Game(Player player, int time) {
        this.initialTime = time * 60;
        this.timeRemaining = time * 60;
        this.player = player;
    }

    public void updateTime(float deltaTime) {
        timeRemaining -= deltaTime;
        if (timeRemaining < 0) timeRemaining = 0;
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
}
