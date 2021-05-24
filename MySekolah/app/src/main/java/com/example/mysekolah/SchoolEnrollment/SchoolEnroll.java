package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mysekolah.HomePage;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SchoolEnroll extends AppCompatActivity implements View.OnClickListener {


    CardView preschool, primary, secondary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_enroll);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        preschool = findViewById(R.id.preSchoolCard);
        primary = findViewById(R.id.priSchoolCard);
        secondary = findViewById(R.id.secSchoolCard);

        preschool.setOnClickListener(this);
        primary.setOnClickListener(this);
        secondary.setOnClickListener(this);


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
            case R.id.preSchoolCard:
                i = new Intent(this, EnrollOrStatusPre.class);
                startActivity(i);
                break;

        }
    }
}