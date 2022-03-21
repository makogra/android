package com.mako.heroslandidle;

import android.content.res.Resources;

import java.io.Serializable;

public class Player  implements Serializable {

    private static volatile Player INSTANCE;
    private int[] equipment,
                    buildings;
    private int money = 0;
    private Resources resources;

    private Player(){ }

    static Player getInstance(){
        if(INSTANCE == null){
            synchronized (Player.class){
                if (INSTANCE == null){
                    INSTANCE = new Player();
                    try{
                        INSTANCE.initialize();
                    }catch (NullPointerException e){
                        System.out.println(Player.class + " " +e);
                    }
                }
            }
        }
        return INSTANCE;
    }

    void setResources(Resources resources){
        this.resources = resources;
    }

    void initialize(){
        if(INSTANCE == null)
            throw new NullPointerException("INSTANCE in null");
        if(resources == null)
            throw new NullPointerException("resources are null");
        equipment = new int[resources.getStringArray(R.array.resources_arr).length];
        buildings = new int[resources.getStringArray(R.array.buildings_types).length];
        buildings[0] = 1;
    }

    public int[] getEquipment() {
        return equipment;
    }

    public int getResource(int index){
        if (InvalidEquipmentIndex(index))
            return -1;
        return equipment[index];
    }

    public boolean addMoney(int money){
        if (money <= 0)
            return false;
        if (Integer.MAX_VALUE - this.money < money)
            this.money = Integer.MAX_VALUE;
            // Give information about over flow
        else
            this.money += money;
        return true;
    }

    public void addResources(int index, int amount){
        if(InvalidEquipmentIndex(index) || amount <= 0)
            return;
        // OverFlow protection
        if (Integer.MAX_VALUE - amount < equipment[index])
            changeResources(index,Integer.MAX_VALUE-equipment[index]);
            // Give information about over flow
        else
            changeResources(index, amount);
    }

    public boolean removeResources(int index, int amount){
        if(InvalidEquipmentIndex(index) || amount <= 0 || amount > equipment[index])
            return false;
        changeResources(index, -amount);
        return true;
    }

    private void changeResources(int index, int amount){
        equipment[index] += amount;
    }

    void merge(Player secondPlayer){
        for (int i = 0; i < this.equipment.length; i++) {
            addResources(i,secondPlayer.equipment[i]);
        }
    }

    private boolean InvalidEquipmentIndex(int index){
        return index < 0 || index >= equipment.length;
    }

    public int getBuildingLvl(int buildingIndex) {
        if (buildingIndex < 0 || buildingIndex >= buildings.length)
            throw new IndexOutOfBoundsException();
        return buildings[buildingIndex];
    }

    public int getMoney() {
        return money;
    }

    public boolean removeMoney(int cost) {
        if (cost > money)
            return false;
        money -= cost;
        return true;
    }
}
