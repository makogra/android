package com.mako.heroslandidle;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private Context context;
    private Resources res;
    private Player player;

    public BuildingsAdapter(Context context, Resources res, Player player) {
        this.context = context;
        this.res = res;
        this.player = player;
    }

    public BuildingsAdapter(Context context, Resources res) {
        this.context = context;
        this.res = res;
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
        holder.number.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class BuildingsViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.number = itemView.findViewById(R.id.buildings_row_number_txt);
        }
    }
}
