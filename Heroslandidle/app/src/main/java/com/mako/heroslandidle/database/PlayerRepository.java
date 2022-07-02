package com.mako.heroslandidle.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

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

    public LiveData<List<String>> getAllPlayersId(){
        return mAllPlayersId;
    }
    
    public LiveData<Player> getPlayer(String id){
        return mPlayerDao.getPlayer(id);
    }

    public LiveData<int[]> getEquipment(String id){
        return mPlayerDao.getEquipment(id);
    }

    public void insert(Player player){
        AppDatabase.databaseWriteExecutor.execute(() -> mPlayerDao.insert(player));
    }

    public LiveData<List<String>> getAllIds() {
        return mPlayerDao.getAllIds();
    }

    public void deleteById(String playerId) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPlayerDao.deleteById(playerId));
    }

    public LiveData<Integer> countPlayers() {
        return mPlayerDao.countPlayers();
    }
}
