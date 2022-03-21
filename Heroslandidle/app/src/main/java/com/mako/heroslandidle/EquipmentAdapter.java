package com.mako.heroslandidle;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    private final Context context;
    private final Player player = Player.getInstance();
    private final String[] resources;
    private int money;


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
        holder.resourceAmount.setText(Integer.toString(player.getResource(position)));
    }

    @Override
    public int getItemCount() {
        return resources.length;
    }

    public class EquipmentViewHolder extends RecyclerView.ViewHolder {

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
