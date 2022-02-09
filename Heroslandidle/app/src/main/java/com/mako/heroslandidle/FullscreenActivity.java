package com.mako.heroslandidle;

import android.annotation.SuppressLint;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mako.heroslandidle.Enums.Equipment;
import com.mako.heroslandidle.databinding.ActivityFullscreenBinding;

import java.util.Arrays;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 300;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 1000;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private View mControlsView;

    private boolean mVisible;
    //private final Runnable mHideRunnable = this::hide;
    private ActivityFullscreenBinding binding;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                if (data != null && data.getSerializableExtra(ExplorationActivity.PLAYER_NAME) != null){
                    player1.merge((Player) data.getSerializableExtra(ExplorationActivity.PLAYER_NAME));
                }
            }
        }
    });

    public static final int REQUEST_CODE = 1;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapterTabs fragmentAdapterTabs;
    private Player player1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBars();

        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mVisible = false;
        mControlsView = binding.getRoot();
        mContentView = binding.fullscreenContentControls;

        tabLayout = findViewById(R.id.tabs);
        viewPager2 = findViewById(R.id.view_pager2);

        player1 = new Player();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapterTabs = new FragmentAdapterTabs(fragmentManager, getLifecycle(), getResources(), player1);
        //Pass player to fragment adapter

        viewPager2.setAdapter(fragmentAdapterTabs);

        Resources resources = super.getResources();

        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.town)));
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.equipment)));
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.buildings)));

        System.out.println(Arrays.toString(resources.getStringArray(R.array.land_types)));
        System.out.println(R.array.land_types);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
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


    public void exitButtonClicked(View v){
        finishAndRemoveTask();
    }

    public void goOnExpedition(View v){
        Intent intent = new Intent(FullscreenActivity.this,ExplorationActivity.class);
        startForResult.launch(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}