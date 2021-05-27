package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mysekolah.Apply_success;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.PersonalityCareerTest.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EnrollOrStatusPre extends AppCompatActivity implements View.OnClickListener {

    CardView application, checkStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolle_or_status_pre);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        application = findViewById(R.id.ApplicationFormCard);
        checkStatus = findViewById(R.id.CheckStatusCard);

        application.setOnClickListener(this);
        checkStatus.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.ApplicationFormCard:
                i = new Intent(this, Enroll_Check_IC_Pre.class);
                startActivity(i);
                break;

            case R.id.CheckStatusCard:
                i = new Intent(this, Apply_success.class);
                startActivity(i);
                break;

        }
    }
}