package io.github.Graphic.Model.SaveData;

import io.github.Graphic.Model.enums.MonsterType;

public class MonsterData {
    private final float x;
    private final float y;
    private final MonsterType type;

    public MonsterData(float x, float y, MonsterType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public MonsterType getType() {
        return type;
    }
}
