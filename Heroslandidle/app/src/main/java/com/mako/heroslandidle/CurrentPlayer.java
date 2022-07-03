package com.mako.heroslandidle;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class CurrentPlayer {

    private static volatile Player currentPlayer;
    private static Resources resources;

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        CurrentPlayer.currentPlayer = currentPlayer;
    }

    public static Player getInstance(){
        if(currentPlayer == null){
            synchronized (Player.class){
                if (currentPlayer == null){
                    currentPlayer = new Player();
                    try{
                        initialize();
                    }catch (NullPointerException e){
                        System.out.println(CurrentPlayer.class + " " +e);
                    }
                }
            }
        }
        return currentPlayer;
    }

    public static void setResources(Resources resources){
        CurrentPlayer.resources = resources;
    }

    public static int[] getBuildings() {
        return currentPlayer.getBuildings();
    }

    public static void setsCurrentPlayerId(String id) {
        //TODO what to do with it
        currentPlayer.setPlayerId(id);
    }

    public static void setEquipment(int[] equipment) {
        currentPlayer.setEquipment(equipment);
    }

    public static void setBuildings(int[] buildings) {
        currentPlayer.setBuildings(buildings);
    }

    public static void setMoney(int money) {
        currentPlayer.setMoney(money);
    }

    public static void initialize(){
        if(currentPlayer == null)
            throw new NullPointerException("currentPlayer in null");
        if(resources == null)
            throw new NullPointerException("resources are null");
        currentPlayer.setEquipment(new int[resources.getStringArray(R.array.resources_arr).length]);
        currentPlayer.setBuildings(new int[resources.getStringArray(R.array.buildings_types).length]);
        currentPlayer.setBuildingsAt(0, 1);
    }

    public static int[] getEquipment() {
        return currentPlayer.getEquipment();
    }

    public static int getResource(int index){
        if (InvalidEquipmentIndex(index))
            return -1;
        return currentPlayer.getEquipmentAt(index);
    }

    public static boolean addMoney(int money){
        if (money <= 0)
            return false;
        if (Integer.MAX_VALUE - currentPlayer.getMoney() < money)
            currentPlayer.setMoney(Integer.MAX_VALUE);
            // Give information about over flow
        else
            currentPlayer.setMoney(currentPlayer.getMoney() + money);
        return true;
    }

    public static void addResources(int index, int amount){
        if(InvalidEquipmentIndex(index) || amount <= 0)
            return;
        // OverFlow protection
        if (Integer.MAX_VALUE - amount < currentPlayer.getEquipmentAt(index))
            changeResources(index,Integer.MAX_VALUE-currentPlayer.getEquipmentAt(index));
            // Give information about over flow
        else
            changeResources(index, amount);
    }

    public static boolean removeResources(int index, int amount){
        if(InvalidEquipmentIndex(index) || amount <= 0 || amount > currentPlayer.getEquipmentAt(index))
            return false;
        changeResources(index, -amount);
        return true;
    }

    private static void changeResources(int index, int amount){
        currentPlayer.setEquipmentAt(index, currentPlayer.getEquipmentAt(index) + amount);
    }

    /*
    void merge(Player secondPlayer){
        for (int i = 0; i < this.equipment.length; i++) {
            addResources(i,secondPlayer.equipment[i]);
        }
    }

     */

    private static boolean InvalidEquipmentIndex(int index){
        return index < 0 || index >= currentPlayer.getEquipment().length;
    }

    public static int getBuildingLvl(int buildingIndex) {
        if (buildingIndex < 0 || buildingIndex >= currentPlayer.getBuildings().length)
            throw new IndexOutOfBoundsException();
        return currentPlayer.getBuildingsAt(buildingIndex);
    }

    public static int getMoney() {
        return currentPlayer.getMoney();
    }

    public static boolean removeMoney(int cost) {
        if (cost > currentPlayer.getMoney())
            return false;
        currentPlayer.setMoney(currentPlayer.getMoney() - cost);
        return true;
    }

/*
    public static void setCurrentPlayerId(String id){
        sCurrentPlayerId = id;
    }

 */

    public static String getPlayerId() {
        return currentPlayer.getPlayerId();
    }

    /*
    public static void setPlayerId(@NonNull String id) {
        currentPlayer.playerId = id;
    }

     */

    public static String toStringConvert() {
        return "Player{" +
                "id='" + currentPlayer.getPlayerId() + '\'' +
                ", equipment=" + Arrays.toString(currentPlayer.getEquipment()) +
                ", buildings=" + Arrays.toString(currentPlayer.getBuildings()) +
                ", money=" + currentPlayer.getMoney() +
                '}';
    }
}
