package com.mako.heroslandidle.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mako.heroslandidle.Player;

import java.util.List;

@Dao
public interface PlayerDao {

    @Query("SELECT playerId FROM player_table")
    LiveData<List<String>> getAllIds();

    @Query("SELECT * FROM player_table WHERE playerId LIKE :playerId")
    LiveData<Player> getPlayer(String playerId);

    @Query("SELECT equipment FROM player_table WHERE playerId LIKE :playerId")
    LiveData<int[]> getEquipment(String playerId);

    @Query("DELETE FROM player_table WHERE playerId LIKE :playerId")
    void deleteById(String playerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player player);

    @Query("SELECT count(*) FROM player_table")
    LiveData<Integer> countPlayers();
}
