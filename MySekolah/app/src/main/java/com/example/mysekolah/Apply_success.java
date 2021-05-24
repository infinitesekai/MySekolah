package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Apply_success extends AppCompatActivity {
    ImageView successinfo;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_success);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        /*
        successinfo=findViewById(R.id.infoApproved);
        dialog=new Dialog(this);

        successinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.setContentView(R.layout.activity_apply_success);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        })


         */

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    break;
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
}