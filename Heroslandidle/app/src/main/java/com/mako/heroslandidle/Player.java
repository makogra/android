package com.mako.heroslandidle;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;

@Entity(tableName = "player_table")
public class Player  implements Serializable {

    @NonNull
    @PrimaryKey
    private  String playerId;

    private  int[] equipment;
    private  int[] buildings;
    private  int money;

    public Player(){
        playerId = "new Badchess";
    }

    @NonNull
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(@NonNull String playerId) {
        this.playerId = playerId;
    }

    public int[] getEquipment() {
        return equipment;
    }

    public void setEquipment(int[] equipment) {
        this.equipment = equipment;
    }

    public int[] getBuildings() {
        return buildings;
    }

    public void setBuildings(int[] buildings) {
        this.buildings = buildings;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBuildingsAt(int buildingIndex) {
        return this.buildings[buildingIndex];
    }

    public int getEquipmentAt(int index) {
        return equipment[index];
    }

    public void setEquipmentAt(int index, int value) {
        equipment[index] = value;
    }

    public void setBuildingsAt(int index, int value) {
        buildings[index] = value;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", equipment=" + Arrays.toString(equipment) +
                ", buildings=" + Arrays.toString(buildings) +
                ", money=" + money +
                '}';
    }
}
