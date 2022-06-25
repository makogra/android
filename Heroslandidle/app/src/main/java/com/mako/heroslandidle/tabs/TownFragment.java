package com.mako.heroslandidle.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mako.heroslandidle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TownFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TownFragment extends Fragment {



    public TownFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TownFragment newInstance() {
        return new TownFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_town, container, false);
    }
}