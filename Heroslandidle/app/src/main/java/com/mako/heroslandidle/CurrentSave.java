package com.mako.heroslandidle;

import android.app.Application;

import com.mako.heroslandidle.database.PlayerRepository;

public class CurrentSave {

    private static String id;
    private static PlayerRepository playerRepository;
    private static boolean created = false;

    public static void init(Application application){
        playerRepository = new PlayerRepository(application);

        created = true;
    }


}
