package com.mako.heroslandidle.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mako.heroslandidle.CurrentPlayer;
import com.mako.heroslandidle.Player;
import com.mako.heroslandidle.R;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    private final Context context;
    private final String[] resources;


    public EquipmentAdapter(Context context, Resources res) {
        this.context = context;
        this.resources = res.getStringArray(R.array.resources_arr);
    }

    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_equipment_row, parent, false);

        return new EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        holder.resourceType.setText(resources[position]);
        holder.resourceAmount.setText("" + CurrentPlayer.getResource(position));
    }

    @Override
    public int getItemCount() {
        return resources.length;
    }

    public void submitEquipment(int[] equipments) {

    }

    public static class EquipmentViewHolder extends RecyclerView.ViewHolder {

        TextView resourceType, resourceAmount;
        ImageView resourceIcon;

        public EquipmentViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceType = itemView.findViewById(R.id.equipment_row_type_txt);
            resourceAmount = itemView.findViewById(R.id.equipment_row_amount_txt);
            //resourceIcon = itemView.findViewById(R.id.equipment_row_icon_img);
        }
    }
}
