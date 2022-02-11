package com.mako.heroslandidle;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private Context context;
    private Resources res;
    private Player player;
    private String[] buildingsTypes,
                    buildingsDescriptions;
    private int[] buildingsMaxLvls;

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

    private void initialize(){
        buildingsTypes = res.getStringArray(R.array.buildings_types);
        buildingsDescriptions = res.getStringArray(R.array.buildings_descriptions);
        buildingsMaxLvls = res.getIntArray(R.array.buildings_max_lvls);
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

        int currentBuildingLvl = Integer.parseInt(holder.lvl.getText().toString());
        if(isMaxBuildingLvl(position, holder.button, currentBuildingLvl)){
            setMaxAndDisable(holder.button);
        } else {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    build();
                    TextView tv_lvl = view.findViewById(R.id.buildings_row_lvl_txt_numeric);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return buildingsTypes.length;
    }

    private boolean isMaxBuildingLvl(int position ,Button button, int lvl){
        int maxBuildingLvl = buildingsMaxLvls[position];


        return (lvl == maxBuildingLvl && button.isEnabled());
    }

    private void setMaxAndDisable(Button button){
        button.setText(R.string.btn_build_max);
        button.setEnabled(false);
    }

    private void build(){
        System.out.println("build");
    }

    public class BuildingsViewHolder extends RecyclerView.ViewHolder {

        private TextView type,
                        description,
                        lvl;
        private Button button;

        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.type = itemView.findViewById(R.id.buildings_row_type_txt);
            this.description = itemView.findViewById(R.id.buildings_row_description_txt);
            this.lvl = itemView.findViewById(R.id.buildings_row_lvl_txt_numeric);
            this.button = itemView.findViewById(R.id.buildings_row_build_btn);
        }
    }
}
