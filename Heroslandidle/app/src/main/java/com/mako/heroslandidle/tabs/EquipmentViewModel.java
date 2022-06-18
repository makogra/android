package com.mako.heroslandidle.tabs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mako.heroslandidle.Player;
import com.mako.heroslandidle.database.PlayerRepository;

public class EquipmentViewModel extends AndroidViewModel {
    private PlayerRepository mRepository;

    private final LiveData<int[]> mEquipment;

    public EquipmentViewModel (@NonNull Application application){
        super(application);
        mRepository = new PlayerRepository(application);
        mEquipment = mRepository.getEquipment(Player.getCurrentPlayerId());
    }

    LiveData<int[]> getEquipment(){return mEquipment;}
}
