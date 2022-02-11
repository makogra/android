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
    }

    @Override
    public int getItemCount() {
        return buildingsTypes.length;
    }

    public class BuildingsViewHolder extends RecyclerView.ViewHolder {

        private TextView type,
                        description;
        private Button button;

        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.type = itemView.findViewById(R.id.buildings_row_type_txt);
            this.description = itemView.findViewById(R.id.buildings_row_description_txt);
            this.button = itemView.findViewById(R.id.buildings_row_build_btn);
        }
    }
}
