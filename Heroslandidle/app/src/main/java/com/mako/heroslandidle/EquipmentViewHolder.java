package com.mako.heroslandidle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentViewHolder extends RecyclerView.ViewHolder {

    private TextView resourceType, resourceAmount;
    private ImageView resourceIcon;

    private EquipmentViewHolder(@NonNull View itemView) {
        super(itemView);
        resourceType = itemView.findViewById(R.id.equipment_row_type_txt);
        resourceAmount = itemView.findViewById(R.id.equipment_row_amount_txt);
        //resourceIcon = itemView.findViewById(R.id.equipment_row_icon_img);
    }

    public void bind(int amount){
        resourceAmount.setText(String.valueOf(amount));
    }


    static EquipmentViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equipment_row, parent, false);
        return new EquipmentViewHolder(view);
    }

}
