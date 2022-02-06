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

    private Resources res;
    private Context context;
    private Player player;
    private String[] resources;
    private long money;


    public EquipmentAdapter(Context context, Resources res, Player player) {
        this.context = context;
        this.res = res;
        this.player = player;
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
        System.out.println("holder = " + holder);
        System.out.println("holder.resourceType = " + holder.resourceType);
        System.out.println("holder.resourceAmount = " + holder.resourceAmount);
        System.out.println("player = " + player.getResource(0));
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
