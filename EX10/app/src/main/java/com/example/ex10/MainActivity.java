package com.example.ex10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ex10.ui.dashboard.DashboardFragment;
import com.example.ex10.ui.home.HomeFragment;
import com.example.ex10.ui.notification.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            navView.setOnItemSelectedListener(navListener);

            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container,
                        new HomeFragment()).commit();
            }
        }
        private BottomNavigationView.OnItemSelectedListener navListener = new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_dashboard:
                        selectedFragment = new DashboardFragment();
                        break;
                    case R.id.navigation_notifications:
                        selectedFragment = new NotificationFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        };
}