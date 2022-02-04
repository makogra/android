package com.mako.heroslandidle;

import com.mako.heroslandidle.Enums.Equipment;

import java.io.Serializable;

public class Player  implements Serializable {

    private int[] equipment;
    private long money = 0;

    Player(){
        equipment = new int[Equipment.values().length];
    }

    public int[] getEquipment() {
        return equipment;
    }

    public int getResource(int index){
        if (InvalidIndex(index))
            return -1;
        return equipment[index];
    }

    public boolean addMoney(long money){
        if (money <= 0)
            return false;
        if (Long.MAX_VALUE - this.money < money)
            this.money = Long.MAX_VALUE;
            // Give information about over flow
        else
            this.money += money;
        return true;
    }

    public boolean addResources(int index, int amount){
        if(InvalidIndex(index) || amount <= 0)
            return false;
        // OverFlow protection
        if (Integer.MAX_VALUE - amount < equipment[index])
            changeResources(index,Integer.MAX_VALUE-equipment[index]);
            // Give information about over flow
        else
            changeResources(index, amount);
        return true;
    }

    public boolean removeResources(int index, int amount){
        if(InvalidIndex(index) || amount <= 0 || amount > equipment[index])
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

    private boolean InvalidIndex(int index){
        return index < 0 || index >= equipment.length;
    }
}
