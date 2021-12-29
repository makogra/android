package com.mako.infinitscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int[] generateSeed(){
        /*
        generate seed for colors as [R,R,R,G,G,G,B,B,B]
         */
        int[] seed = new int[9];
        Random r = new Random();
        char[] numbers;
        for (int i = 1; i <= 3; i++) {
            numbers = Integer.toString(r.nextInt(256)).toCharArray();
            for (int j = 3*(i-1), l = 3; j <= 3*i -1; j++, l--) {
                seed[j] = numbers.length >= l? numbers[numbers.length-l] - '0': 0;
            }
        }
        return seed;
    }
}