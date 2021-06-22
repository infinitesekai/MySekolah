package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mysekolah.util.MyApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public Button btn_student_home, btn_parent_home;//student home button,parent home button
    private User currentUser;//current user
    private int lastfragment;

    //navigation bar
    private BottomNavigationView bottomNav;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    if (currentUser.getRole() == 1) {
                        selectedFragment = new HomePage();
                    } else {
                        selectedFragment = new HomePage_Student();
                    }
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = (ProfilePage)new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:

                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    if (currentUser.getRole() == 1) {
                        selectedFragment = new SearchPage();
                    } else {
                        selectedFragment = new SearchPage_Student();
                    }
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent for current user
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //navigation bar
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNav.setSelectedItemId(bottomNav.getMenu().getItem(lastfragment).getItemId());
            }
        },100);

    }
}