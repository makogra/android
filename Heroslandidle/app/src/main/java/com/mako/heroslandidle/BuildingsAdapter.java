package com.mako.heroslandidle;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private final Context context;
    private final Resources res;
    private Player player;
    private String[] buildingsTypes,
            buildingsDescriptions;
    private int[] buildingsMaxLvls;
    private int[][][] prices; //Building type, lvl, resources - all
    private View parent;

    public BuildingsAdapter(Context context, Resources res, Player player) {
        this.context = context;
        this.res = res;
        this.player = player;
        initialize();
    }

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
        System.out.println(Arrays.deepToString(prices));
    }

    @NonNull
    @Override
    public BuildingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(res.getLayout(R.layout.fragment_buildings_row), parent, false);
        this.parent = parent;
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
            holder.button.setOnClickListener(view -> {
                popup(holder, currentBuildingLvl);

                if (isMaxBuildingLvl(position, holder.button, currentBuildingLvl.get())) {
                    setMaxAndDisable(holder.button);
                }
            });
        }
    }

    private void build(@NonNull BuildingsViewHolder holder, AtomicInteger currentBuildingLvl) {
        currentBuildingLvl.getAndIncrement();
        TextView tv_lvl = holder.lvl;
        tv_lvl.setText(String.valueOf(currentBuildingLvl.get()));

    }

    private void popup(BuildingsViewHolder holder, AtomicInteger currentBuildingLvl) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup, null);

        Button closePopupBtn = customView.findViewById(R.id.popup_cancel_btn);
        Button acceptPopupBtn = customView.findViewById(R.id.popup_accept_btn);

        PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        closePopupBtn.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        acceptPopupBtn.setOnClickListener(view -> {
            build(holder, currentBuildingLvl);
            System.out.println("build succed");
            popupWindow.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return buildingsTypes.length;
    }

    private boolean isMaxBuildingLvl(int position, Button button, int lvl) {
        int maxBuildingLvl = buildingsMaxLvls[position];


        return (lvl == maxBuildingLvl && button.isEnabled());
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
