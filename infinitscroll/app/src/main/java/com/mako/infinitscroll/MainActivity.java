package com.mako.infinitscroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Seed seed;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seed = generateSeed();
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, seed);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        System.out.println("seed = " + Arrays.toString(seed.array) + " number: " + seed.numberString);
    }



    private static Seed generateSeed(){
        /*
        generate seed for colors as [RR,GG,BB]
         */
        String[] arr = new String[3];
        Random r = new Random();
        int seed = r.nextInt();
        r.setSeed(seed);
        String hex;
        StringBuilder numberString = new StringBuilder();
        for (int i = 0; i <= 2; i++) {
            hex = Integer.toHexString(r.nextInt(256));
            numberString.append(hex);
            arr[i] = String.format("%2s",hex).replace(" ", "0");
        }
        return new Seed(arr,numberString.toString(),seed);
    }
}