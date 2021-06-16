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
    public Button btn_student_home, btn_parent_home;
    private User currentUser;
    private int lastfragment;
    private BottomNavigationView bottomNav;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    if (currentUser.getRole() == 1) {
                        selectedFragment = new HomePage();
                    } else {
                        selectedFragment = new HomePage_Student();
                    }
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    lastfragment = R.id.nav_notif;

                    break;
                case R.id.nav_profile:
                    selectedFragment = (ProfilePage)new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    //selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
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
        currentUser = (User) getIntent().getSerializableExtra("user");
        //currentUser = MyApplication.currentUser;

        lastfragment = 0;
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNav.setSelectedItemId(bottomNav.getMenu().getItem(lastfragment).getItemId());
            }
        },100);
        /*
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
        if (currentUser.getRole() == 1) {
            Fragment fragment = new HomePage();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        } else {
            Fragment fragment = new HomePage_Student();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
        */

        /*
        btn_student_home = findViewById(R.id.student_home);
        btn_parent_home = findViewById(R.id.parent_home);

        btn_student_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomePage_Student();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        btn_parent_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomePage();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
         */
    }
}