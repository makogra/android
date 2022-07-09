package com.mako.heroslandidle.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mako.heroslandidle.CurrentPlayer;
import com.mako.heroslandidle.R;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private static final String TAG = "BuildingsAdapter";
    private final Context context;
    private final Resources res;
    private String[] buildingsTypes,
            buildingsDescriptions;
    private int[] buildingsMaxLvls;
    private int[][][] prices; //Building type, lvl, resources - all


    public BuildingsAdapter(Context context, Resources res) {
        this.context = context;
        this.res = res;
        initialize();
    }

    private void initialize() {
        buildingsTypes = res.getStringArray(R.array.buildings_types);
        buildingsDescriptions = res.getStringArray(R.array.buildings_descriptions);
        buildingsMaxLvls = res.getIntArray(R.array.buildings_max_lvls);
        TypedArray typesArray = res.obtainTypedArray(R.array.buildings_costs_type);
        TypedArray lvlArray;
        int[] resourcesCosts;
        prices = new int[typesArray.length()][][];
        for (int i = 0; i < typesArray.length(); i++) {
            lvlArray = res.obtainTypedArray(typesArray.getResourceId(i,0));
            prices[i] = new int[lvlArray.length()][];
            for (int j = 0; j < lvlArray.length(); j++) {
                resourcesCosts = res.getIntArray(lvlArray.getResourceId(j, 0));
                prices[i][j] = resourcesCosts;
            }
            lvlArray.recycle();
        }
        typesArray.recycle();
        Log.d(TAG, "BuildingsAdapter.initialize: prices= " + Arrays.deepToString(prices));
    }

    @NonNull
    @Override
    public BuildingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(res.getLayout(R.layout.fragment_buildings_row), parent, false);
        return new BuildingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingsViewHolder holder, int position) {
        holder.type.setText(buildingsTypes[position]);
        holder.description.setText(buildingsDescriptions[position]);

        if (isMaxBuildingLvl(position) && holder.buildButton.isEnabled()) {
            setMaxAndDisable(holder.buildButton);
        } else {
            holder.lvl.setText("" + CurrentPlayer.getBuildingLvl(position));

            boolean canBuild = canBuild(position);

            holder.buildButton.setOnClickListener(view -> {
                popup(holder, canBuild, position);

                if (isMaxBuildingLvl(position)) {
                    setMaxAndDisable(holder.buildButton);
                }
            });
        }
    }

    private void build(@NonNull BuildingsViewHolder holder, int position) {
        //TODO async auto save after build
        int[] costs = prices[position][CurrentPlayer.getBuildingLvl(position)];

        CurrentPlayer.removeMoney(costs[1]);

        for (int i = 2; i < costs.length; i++) {
            CurrentPlayer.removeResources(i-2, costs[i]);
        }

        CurrentPlayer.upgradeBuilding(position);
        holder.lvl.setText(String.valueOf(CurrentPlayer.getBuildingLvl(position)));
    }

    private void popup(BuildingsViewHolder holder, boolean canBuild, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupMenu = layoutInflater.inflate(R.layout.popup, null);

        Button closePopupBtn = popupMenu.findViewById(R.id.popup_cancel_btn);
        Button acceptPopupBtn = popupMenu.findViewById(R.id.popup_accept_btn);
        TextView buildingTypeTextView = popupMenu.findViewById(R.id.popup_building_type_text_view);
        TextView lvlFromTextView = popupMenu.findViewById(R.id.popup_lvl_from_text_view);
        TextView lvlToTextView = popupMenu.findViewById(R.id.popup_lvl_to_text_view);
        TextView neededResourcesTextView = popupMenu.findViewById(R.id.popup_needed_resources_text_view);

        buildingTypeTextView.setText(buildingsTypes[position]);
        lvlFromTextView.setText("" + CurrentPlayer.getBuildingLvl(position));
        lvlToTextView.setText("" + (CurrentPlayer.getBuildingLvl(position) + 1));
        neededResourcesTextView.setText(Arrays.toString(prices[position][CurrentPlayer.getBuildingLvl(position)]));

        PopupWindow popupWindow = new PopupWindow(popupMenu, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);

        closePopupBtn.setOnClickListener(view -> popupWindow.dismiss());

        if(!canBuild) {
            acceptPopupBtn.setEnabled(false);
            return;
        }

        if(!acceptPopupBtn.isEnabled())
            acceptPopupBtn.setEnabled(true);

        acceptPopupBtn.setOnClickListener(view -> {
            displayPlayerEq();
            build(holder, position);
            displayPlayerEq();
            Log.d(TAG, "build succeed");
            popupWindow.dismiss();
        });
    }

    private void displayPlayerEq(){
        Log.d(TAG, "player.getMoney() = " + CurrentPlayer.getMoney());
        Log.d(TAG, "player equipment = " + Arrays.toString(CurrentPlayer.getEquipment()));
    }

    private boolean canBuild(int buildingTypeIndex){
        int[] costs = prices[buildingTypeIndex][CurrentPlayer.getBuildingLvl(buildingTypeIndex)];
        boolean canBuild = true;

        if(costs[0] > CurrentPlayer.getBuildingLvl(0))
            canBuild = false;
        if(costs[1] > CurrentPlayer.getMoney())
            canBuild = false;
        for (int i = 2; i < costs.length; i++) {
            if (CurrentPlayer.getResource(i-2) < costs[i])
                canBuild = false;
        }
        return canBuild;
    }

    @Override
    public int getItemCount() {
        return buildingsTypes.length;
    }

    private boolean isMaxBuildingLvl(int position) {
        return (CurrentPlayer.getBuildingLvl(position) == buildingsMaxLvls[position]);
    }

    private void setMaxAndDisable(Button button) {
        button.setText(R.string.btn_build_max);
        button.setEnabled(false);
    }


    public static class BuildingsViewHolder extends RecyclerView.ViewHolder {

        private final TextView type;
        private final TextView description;
        private final TextView lvl;
        private final Button buildButton;

        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.type = itemView.findViewById(R.id.buildings_row_type_txt);
            this.description = itemView.findViewById(R.id.buildings_row_description_txt);
            this.lvl = itemView.findViewById(R.id.buildings_row_lvl_txt_numeric);
            this.buildButton = itemView.findViewById(R.id.buildings_row_build_btn);
        }
    }
}
