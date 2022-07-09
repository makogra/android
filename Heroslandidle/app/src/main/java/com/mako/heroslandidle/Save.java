package com.mako.heroslandidle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mako.heroslandidle.database.PlayerRepository;

public class Save implements Runnable{

    private static final String TAG = "Save";
    private static PlayerRepository playerRepo;
    private static Application application;

    public static void init(Application application){
        Save.application = application;
        Save.playerRepo = new PlayerRepository(application);
    }

    @Override
    public void run() {
        save();
    }

    synchronized private void save(){

        SharedPreferences sp = application.getSharedPreferences("playerId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("playerId", CurrentPlayer.getPlayerId());
        editor.apply();
        Log.d(TAG, "saving");
        playerRepo.insert(CurrentPlayer.getInstance());
        Log.d(TAG, "p = " + CurrentPlayer.toStringConvert());
        Log.d(TAG, "saved");

    }
}
