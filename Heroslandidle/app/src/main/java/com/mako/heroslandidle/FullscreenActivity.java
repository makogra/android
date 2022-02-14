package com.mako.heroslandidle;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.mako.heroslandidle.databinding.ActivityFullscreenBinding;

import java.util.Arrays;

public class FullscreenActivity extends AppCompatActivity {

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

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Player player1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBars();

        com.mako.heroslandidle.databinding.ActivityFullscreenBinding binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tabLayout = findViewById(R.id.tabs);
        viewPager2 = findViewById(R.id.view_pager2);

        Resources resources = getResources();

        player1 = new Player(resources);

        createTabs();
        addTabs(resources);

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

        // Drawer layout
        DrawerLayout drawerLayout = findViewById(R.id.fullscreen_drawer_layout);
        ImageButton settingsButton = findViewById(R.id.fullscreen_nav_menu_btn);
        settingsButton.setOnClickListener(view -> {
            drawerLayout.openDrawer(Gravity.RIGHT);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        System.out.println("item selected: " + item + " id = " + id);
        if(id == R.id.nav_menu_account) {
            //TODO account method
            System.out.println("account");
        } else if (id == R.id.nav_menu_save){
            System.out.println("save");
        } else if (id == R.id.nav_menu_exit){
            System.out.println("exit");
            finishAndRemoveTask();
        }
        return super.onOptionsItemSelected(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentAdapterTabs fragmentAdapterTabs = new FragmentAdapterTabs(fragmentManager, getLifecycle(), getResources(), player1);

        viewPager2.setAdapter(fragmentAdapterTabs);
    }

    private void addTabs(Resources resources) {
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.town)));
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.equipment)));
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.buildings)));
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