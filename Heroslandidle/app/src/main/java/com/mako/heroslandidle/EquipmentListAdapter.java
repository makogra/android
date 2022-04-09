package com.mako.heroslandidle;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EquipmentListAdapter extends ListAdapter<Player, EquipmentAdapter.EquipmentViewHolder> {

    protected EquipmentListAdapter(@NonNull DiffUtil.ItemCallback<Player> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EquipmentAdapter.EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentAdapter.EquipmentViewHolder holder, int position) {

    }
}
