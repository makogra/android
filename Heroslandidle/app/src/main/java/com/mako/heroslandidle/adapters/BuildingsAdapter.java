package com.mako.heroslandidle.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mako.heroslandidle.Player;
import com.mako.heroslandidle.R;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private final Context context;
    private final Resources res;
    private final Player player = Player.getInstance();
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
        System.out.println("BuildingsAdapter.initialize: prices= " + Arrays.deepToString(prices));
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

        AtomicInteger currentBuildingLvl = new AtomicInteger(Integer.parseInt(holder.lvl.getText().toString()));
        if (isMaxBuildingLvl(position, holder.button, currentBuildingLvl.get())) {
            setMaxAndDisable(holder.button);
        } else {

            boolean canBuild = canBuild(position, currentBuildingLvl.get());

            holder.button.setOnClickListener(view -> {
                popup(holder, currentBuildingLvl, canBuild, position);

                if (isMaxBuildingLvl(position, holder.button, currentBuildingLvl.get())) {
                    setMaxAndDisable(holder.button);
                }
            });
        }
    }

    private void build(@NonNull BuildingsViewHolder holder, AtomicInteger currentBuildingLvl, int position) {
        int[] costs = prices[position][currentBuildingLvl.get()];

        player.removeMoney(costs[1]);

        for (int i = 2; i < costs.length; i++) {
            player.removeResources(i-2, costs[i]);
        }

        currentBuildingLvl.incrementAndGet();
        TextView tv_lvl = holder.lvl;
        tv_lvl.setText(String.valueOf(currentBuildingLvl.get()));
    }

    private void popup(BuildingsViewHolder holder, AtomicInteger currentBuildingLvl, boolean canBuild, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup, null);

        Button closePopupBtn = customView.findViewById(R.id.popup_cancel_btn);
        Button acceptPopupBtn = customView.findViewById(R.id.popup_accept_btn);

        PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);

        closePopupBtn.setOnClickListener(view -> popupWindow.dismiss());

        if(canBuild) {
            if(!acceptPopupBtn.isEnabled())
                acceptPopupBtn.setEnabled(true);

            acceptPopupBtn.setOnClickListener(view -> {
                displayPlayerEq();
                build(holder, currentBuildingLvl, position);
                displayPlayerEq();
                System.out.println("build succeed");
                popupWindow.dismiss();
            });
        } else {
            acceptPopupBtn.setEnabled(false);
        }
    }

    private void displayPlayerEq(){
        System.out.println("player.getMoney() = " + player.getMoney());
        System.out.println("player equipment = " + Arrays.toString(player.getEquipment()));
    }

    private boolean canBuild(int buildingTypeIndex, int buildingLvl){
        int[] costs = prices[buildingTypeIndex][buildingLvl];
        boolean canBuild = true;

        if(costs[0] > player.getBuildingLvl(0))
            canBuild = false;
        if(costs[1] > player.getMoney())
            canBuild = false;
        for (int i = 2; i < costs.length; i++) {
            if (player.getResource(i) < costs[i])
                canBuild = false;
        }
        return canBuild;
    }

    @Override
    public int getItemCount() {
        return buildingsTypes.length;
    }

    private boolean isMaxBuildingLvl(int position, Button button, int lvl) {
        return (lvl == buildingsMaxLvls[position] && button.isEnabled());
    }

    private void setMaxAndDisable(Button button) {
        button.setText(R.string.btn_build_max);
        button.setEnabled(false);
    }


    public class BuildingsViewHolder extends RecyclerView.ViewHolder {

        private final TextView type;
        private final TextView description;
        private final TextView lvl;
        private final Button button;

        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.type = itemView.findViewById(R.id.buildings_row_type_txt);
            this.description = itemView.findViewById(R.id.buildings_row_description_txt);
            this.lvl = itemView.findViewById(R.id.buildings_row_lvl_txt_numeric);
            this.button = itemView.findViewById(R.id.buildings_row_build_btn);
        }
    }
}
