package com.mako.heroslandidle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.mako.heroslandidle.databinding.ActivityExplorationBinding;

import java.util.Arrays;


public class ExplorationActivity extends AppCompatActivity {

    public static final String PLAYER_NAME = "playerFromExpedition";

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        hideSystemBars();

        com.mako.heroslandidle.databinding.ActivityExplorationBinding binding = ActivityExplorationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        player = new Player();
        System.out.println(Arrays.toString(getResources().getStringArray(R.array.land_types)));
        Land land1 = new Land(getResources());
        displayLand(binding.getRoot(), land1);
    }

    public void goBack(View v){
        Intent intent = new Intent();
        if(player == null)
            setResult(RESULT_CANCELED);
        else {
            intent.putExtra(PLAYER_NAME, player);
            setResult(RESULT_OK, intent);
        }
        finishAndRemoveTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(super.getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }

    private void displayLand(View view, Land land){
        TextView biom = view.findViewById(R.id.biom);
        TextView resType1 = view.findViewById(R.id.res_type1);
        TextView resAmount1 = view.findViewById(R.id.res_amount1);
        TextView resType2 = view.findViewById(R.id.res_type2);
        TextView resAmount2 = view.findViewById(R.id.res_amount2);

        biom.setText(land.getBiom());
        resType1.setText(land.getLeftResField().getResType());
        resAmount1.setText("" + land.getLeftResField().getResAmount());

        resType2.setText(land.getRightResField().getResType());
        resAmount2.setText("" + land.getRightResField().getResAmount());
    }
}