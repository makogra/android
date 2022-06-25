package com.mako.heroslandidle.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mako.heroslandidle.adapters.BuildingsAdapter;
import com.mako.heroslandidle.R;


public class BuildingsFragment extends Fragment {

    public BuildingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_buildings, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.buildings_recycler_view);
        BuildingsAdapter adapter = new BuildingsAdapter(getContext(), getResources());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}