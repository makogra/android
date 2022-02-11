package com.mako.heroslandidle;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private final Context context;
    private Player player;
    private final String[] buildingsTypes,
                    resourcesTypes,
                    descriptions;
    private final int[][] prices;
    private final int[] maxBuildingLvl;

    public BuildingsAdapter(Context context, Resources res, Player player) {
        this.context = context;
        this.player = player;
        buildingsTypes = res.getStringArray(R.array.buildings_types);
        resourcesTypes = res.getStringArray(R.array.resources_arr);
        prices = new int[buildingsTypes.length][resourcesTypes.length];
        descriptions = res.getStringArray(R.array.buildings_descriptions);
        maxBuildingLvl = res.getIntArray(R.array.max_buildings_lvl);
    }

    @NonNull
    @Override
    public BuildingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_buildings_row, parent, false);
        return new BuildingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingsViewHolder holder, int position) {
        holder.type.setText(buildingsTypes[position]);
        holder.description.setText(descriptions[position]);

        int buildingLvl;
        try {
            buildingLvl = player.getBuildingLvl(position);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            buildingLvl = 0;
        }
        if(buildingLvl == 0) {
            holder.button.setText(R.string.btn_build);
        } else if(buildingLvl == maxBuildingLvl[position]) {
            holder.button.setText(R.string.btn_build_max);
            holder.button.setEnabled(false);
        } else {
            holder.button.setText(R.string.btn_build_upgrade);
            holder.lvl.setText(String.valueOf(buildingLvl));
        }
    }

    @Override
    public int getItemCount() {
        return buildingsTypes.length;
    }

    public class BuildingsViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView type,
                description,
                lvl;
        Button button;


        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.buildings_row_icon_img);
            this.type = itemView.findViewById(R.id.buildings_row_type_txt);
            this.description = itemView.findViewById(R.id.buildings_row_description_txt);
            this.lvl = itemView.findViewById(R.id.buildings_row_lvl_txt);
            this.button = itemView.findViewById(R.id.buildings_row_build_btn);
        }
    }
}
