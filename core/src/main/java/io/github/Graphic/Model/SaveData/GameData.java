package io.github.Graphic.Model.SaveData;

import java.util.List;

public class GameData {
    private final float totalTime;
    private final float remainingTime;

    private final PlayerData playerData;

    private final List<SeedData> seedDataList;
    private final List<MonsterData> monsterDataList;


    public GameData(float totalTime, float remainingTime,
                    PlayerData playerData,
                    List<SeedData> seedDataList, List<MonsterData> monsterDataList) {
        this.totalTime = totalTime;
        this.remainingTime = remainingTime;
        this.playerData = playerData;
        this.seedDataList = seedDataList;
        this.monsterDataList = monsterDataList;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public List<SeedData> getSeedDataList() {
        return seedDataList;
    }

    public List<MonsterData> getMonsterDataList() {
        return monsterDataList;
    }
}
