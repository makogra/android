package com.mako.heroslandidle.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mako.heroslandidle.CurrentPlayer;
import com.mako.heroslandidle.Player;
import com.mako.heroslandidle.R;
import com.mako.heroslandidle.activites.ExplorationActivity;
import com.mako.heroslandidle.database.PlayerRepository;

import java.util.Objects;

public class TownFragment extends Fragment {

    PlayerRepository mPlayerRepository;

    public TownFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_town, container, false);

        // Buttons
        Button goOnExpeditionButton = view.findViewById(R.id.GoToExpeditionButton);
        goOnExpeditionButton.setOnClickListener(this::goOnExpedition);

        Button saveButton = view.findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(v -> save());
        return view;
    }

    private void save() {

        SharedPreferences sp = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("playerId", CurrentPlayer.getPlayerId());
        editor.apply();
        System.out.println("saving");
        mPlayerRepository.insert(CurrentPlayer.getInstance());
        System.out.println("p = " + CurrentPlayer.toStringConvert());
        System.out.println("saved");
        mPlayerRepository.getPlayer(CurrentPlayer.getPlayerId()).observe(getViewLifecycleOwner(), player -> System.out.println("player = " + player));
        mPlayerRepository.countPlayers().observe(getViewLifecycleOwner(), integer -> System.out.println("player in DB count = " + integer));

    }

    private void load(){
        //TODO add new activity to select already existing player or create new one
        /*
          Challanges:
           * check if given name(id) already exitsts
           * popup window to write name
         */
    }

    private void goOnExpedition(View view) {
            Intent intent = new Intent(getActivity(), ExplorationActivity.class);
            //startForResult.launch(intent);
            startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerRepository = new PlayerRepository(requireActivity().getApplication());
    }
}