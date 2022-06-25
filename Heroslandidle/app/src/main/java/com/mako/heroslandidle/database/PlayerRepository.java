package com.mako.heroslandidle.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mako.heroslandidle.Player;

import java.util.List;

public class PlayerRepository {

    private PlayerDao mPlayerDao;
    private LiveData<List<String>> mAllPlayersId;

    public PlayerRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
        mAllPlayersId = mPlayerDao.getAllIds();
    }

    LiveData<List<String>> getAllPlayersId(){
        return mAllPlayersId;
    }
    
    LiveData<Player> getPlayer(String id){
        return mPlayerDao.getPlayer(id);
    }

    public LiveData<int[]> getEquipment(String id){
        return mPlayerDao.getEquipment(id);
    }

    void insert(Player player){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPlayerDao.insert(player);
        });
    }
}
