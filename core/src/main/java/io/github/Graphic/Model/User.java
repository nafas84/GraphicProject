package io.github.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class User {
    // Menu setting
    private final int id;
    private String username;
    private String password;
    private final String question;
    private final String answer;
    private String avatarPath;

    private int totalScore = 0;
    private int totalKill = 0;
    private int bestTimeLive = 0;


    public User(String username, String password, String question, String answer, String avatarPath) {
        this.id = App.getNumberOfUsers() + 1;
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatarPath = avatarPath;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getQuestion() {
        return "\""+ question +"\"";
    }

    public String getAnswer() {
        return answer;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalKill() {
        return totalKill;
    }

    public int getBestTimeLive() {
        return bestTimeLive;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setTotalKill(int totalKill) {
        this.totalKill = totalKill;
    }

    public void setBestTimeLive(int bestTimeLive) {
        this.bestTimeLive = bestTimeLive;
    }

    public String getUserInfo() {
        return App.getLanguage("signUp.username") + "\t" + this.username + "\n\n" +
            App.getLanguage("signUp.password") + "\t" + this.password + "\n\n" +
            App.getLanguage("signUp.question") + "\t" + this.question + "\n\n" +
            App.getLanguage("signUp.answer") + "\t" + this.answer + "\n\n";
    }
}
