package com.mako.heroslandidle.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mako.heroslandidle.adapters.EquipmentAdapter;
import com.mako.heroslandidle.R;


public class EquipmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private EquipmentAdapter adapter;
    private EquipmentViewModel mEquipmentViewModel;


    public EquipmentFragment() {
    }



    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_equipment);
        adapter = new EquipmentAdapter(getContext(), getResources());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEquipmentViewModel = new ViewModelProvider(requireActivity()).get(EquipmentViewModel.class);
        mEquipmentViewModel.getEquipment().observe(getViewLifecycleOwner(), equipments -> adapter.submitEquipment(equipments));
        return view;
    }
}