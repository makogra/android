package com.mako.infinitscroll;

import android.graphics.Color;

import java.util.Random;

public class Seed {
    public String[] array;
    public String numberString;
    public int seed;

    public Seed(String[] array, String numberString, int seed){
        this.array = array;
        this.numberString = numberString;
        this.seed = seed;
    }

    public int getColor(){
        return getColor(0);
    }

    public int getColor(int position){
        Random r = new Random(seed+ position* 100100100L);
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = Math.abs(r.nextInt(256));
        }
        return Color.rgb(rgb[0],rgb[1],rgb[2]);
    }
}
