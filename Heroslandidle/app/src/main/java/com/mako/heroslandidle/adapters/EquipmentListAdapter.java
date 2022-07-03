package com.mako.heroslandidle.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.mako.heroslandidle.CurrentPlayer;
import com.mako.heroslandidle.EquipmentViewHolder;
import com.mako.heroslandidle.Player;

public class EquipmentListAdapter extends ListAdapter<Integer, EquipmentViewHolder> {

    protected EquipmentListAdapter(@NonNull DiffUtil.ItemCallback<Integer> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EquipmentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
         //= getItem(position);
        holder.bind(CurrentPlayer.getResource(position));
    }

    static class PlayerDiff extends DiffUtil.ItemCallback<Integer> {


        @Override
        public boolean areItemsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
            return oldItem.intValue() == newItem.intValue();
        }
    }
}
