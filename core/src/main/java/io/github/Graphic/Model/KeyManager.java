package io.github.Graphic.Model;

import com.badlogic.gdx.Input;

public class KeyManager {
    private int moveUp = Input.Keys.W;
    private int moveDown = Input.Keys.S;
    private int moveLeft = Input.Keys.A;
    private int moveRight = Input.Keys.D;

    private int autoAim = Input.Keys.SPACE;
    private int reloadWeapon = Input.Keys.R;

    private int cheatTime = Input.Keys.T;
    private int cheatLevel = Input.Keys.L;
    private int cheatLife = Input.Keys.J;
    private int cheatHp = Input.Keys.H;

    private int pauseGame = Input.Keys.ESCAPE;

    public int getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(int moveUp) {
        this.moveUp = moveUp;
    }

    public int getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(int moveDown) {
        this.moveDown = moveDown;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public int getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(int moveRight) {
        this.moveRight = moveRight;
    }

    public int getAutoAim() {
        return autoAim;
    }

    public void setAutoAim(int autoAim) {
        this.autoAim = autoAim;
    }

    public int getReloadWeapon() {
        return reloadWeapon;
    }

    public void setReloadWeapon(int reloadWeapon) {
        this.reloadWeapon = reloadWeapon;
    }

    public int getCheatTime() {
        return cheatTime;
    }

    public void setCheatTime(int cheatTime) {
        this.cheatTime = cheatTime;
    }

    public int getCheatLevel() {
        return cheatLevel;
    }

    public void setCheatLevel(int cheatLevel) {
        this.cheatLevel = cheatLevel;
    }

    public int getCheatLife() {
        return cheatLife;
    }

    public void setCheatLife(int cheatLife) {
        this.cheatLife = cheatLife;
    }

    public int getCheatHp() {
        return cheatHp;
    }

    public void setCheatHp(int cheatHp) {
        this.cheatHp = cheatHp;
    }

    public int getPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(int pauseGame) {
        this.pauseGame = pauseGame;
    }
}

