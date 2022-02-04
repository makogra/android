package com.mako.heroslandidle;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

@RequiresApi(api = Build.VERSION_CODES.N)
public class FragmentAdapterTabs extends FragmentStateAdapter {

    /*

    new Comparator<Integer>() {
        final TypedArray typedArray = resources.obtainTypedArray(R.array.tabsArr);
        @Override
        public int compare(Integer o1, Integer o2) {
            return typedArray.getResourceId(o1,-1) - typedArray.getResourceId(o2,-1);
        }
    }

     */

    Resources resources = Resources.getSystem();
    ArrayList<Integer> availableTabs = new ArrayList<>();
    private int itemCount = 0;

    public FragmentAdapterTabs(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        availableTabs.add(R.string.town);
        availableTabs.add(R.string.equipment);
        availableTabs.add(R.string.buildings);
        updateItemCount();
        //sort();

    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int id = availableTabs.get(position);
        resources.finishPreloading();
        switch (id){
            case R.string.equipment:
                return new EquipmentFragment();
            case R.string.buildings:
                return new BuildingsFragment();
            case R.string.upgrades:
                return new UpgradesFragment();
            default:
                return new TownFragment();
        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public boolean addFragment(int id){
        if(!IdValidator(id))
            return false;
        if(availableTabs.add(id)){
            updateItemCount();
            //sort();
            return true;
        }
        return false;
    }

    private boolean IdValidator(int id) {
        boolean validator;
        TypedArray typedArray = resources.obtainTypedArray(R.array.tabsArr);
        validator = typedArray.hasValue(id);
        typedArray.recycle();
        return validator;
    }

    private void updateItemCount(){
        itemCount = availableTabs.size();
    }

    private void sort(){
        /*availableTabs.sort(new Comparator<Integer>() {
            // TODO find another way of sorting
            @SuppressLint("Recycle")
            final TypedArray typedArray = resources.obtainTypedArray(R.array.tabsArr);
            @Override
            public int compare(Integer o1, Integer o2) {
                return typedArray.getResourceId(o1,-1) - typedArray.getResourceId(o2,-1);
            }

        });
        Arrays.sort(availableTabs);
        */
        availableTabs.sort((o1,o2)->o1-o2);
    }
}
