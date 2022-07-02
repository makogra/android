package com.mako.heroslandidle.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mako.heroslandidle.Player;
import com.mako.heroslandidle.R;
import com.mako.heroslandidle.activites.ExplorationActivity;
import com.mako.heroslandidle.activites.FullscreenActivity;
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
        System.out.println("saving");
        Player p = Player.getInstance();
        mPlayerRepository.insert(p);
        System.out.println("p = " + p);
        System.out.println("saved");
        mPlayerRepository.getPlayer(p.getId()).observe(getViewLifecycleOwner(), player -> System.out.println("player = " + player));

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