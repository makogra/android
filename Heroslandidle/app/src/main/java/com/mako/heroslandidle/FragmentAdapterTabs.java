package com.mako.heroslandidle;

import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mako.heroslandidle.tabs.BuildingsFragment;
import com.mako.heroslandidle.tabs.EquipmentFragment;
import com.mako.heroslandidle.tabs.TownFragment;
import com.mako.heroslandidle.tabs.UpgradesFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FragmentAdapterTabs extends FragmentStateAdapter {


    private final Resources resources;
    private Player player;
    private List<String> availableTabs = new ArrayList<>();
    private final List<String> allTabs ;//= (ArrayList<String>) Arrays.asList(resources.getStringArray(R.array.land_types));


    public FragmentAdapterTabs(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Resources resources, Player player) {
        super(fragmentManager, lifecycle);
        this.resources = resources;
        this.player = player;
        allTabs = Arrays.asList(resources.getStringArray(R.array.tabs_arr));
        availableTabs.add(allTabs.get(0)); // Town
        availableTabs.add(allTabs.get(1)); // Equipment
        availableTabs.add(allTabs.get(2)); // Buildings
        //sort();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new EquipmentFragment(player);
            case 2:
                return new BuildingsFragment();
            case 3:
                return new UpgradesFragment();
            default:
                return new TownFragment();
        }
    }

    @Override
    public int getItemCount() {
        return availableTabs.size();
    }

    public boolean addFragment(int index){
        if(invalidIndex(index))
            return false;
        if(alreadyIn(index))
            return false;
        if (availableTabs.add(allTabs.get(index))) {
            //sort();
            return true;
        }
        return false;
    }

    private boolean alreadyIn(int index) {
        return availableTabs.contains(allTabs.get(index));
    }

    private boolean invalidIndex(int index) {
        String[] tabsArr = resources.getStringArray(R.array.tabs_arr);
        return index < 0 || index >= tabsArr.length;
    }

    private void sort(){
        //TODO fix problem with sort
        availableTabs.sort(Comparator.comparingInt(o -> allTabs.indexOf(o)));
    }
}
