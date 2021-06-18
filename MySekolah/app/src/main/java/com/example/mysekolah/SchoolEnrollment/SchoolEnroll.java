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
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SchoolEnroll extends AppCompatActivity implements View.OnClickListener {


    private User currentUser;
    private int lastfragment;
    CardView preschool, primary, secondary;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_enroll);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Three different education level
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
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
                i = new Intent(this, EnrollOrStatus.class);
                i.putExtra("SchoolLevel", "PreSchool");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.priSchoolCard:
                i = new Intent(this, EnrollOrStatus.class);
                i.putExtra("SchoolLevel", "Primary");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.secSchoolCard:
                i = new Intent(this, EnrollOrStatus.class);
                i.putExtra("SchoolLevel", "Secondary");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;



        }
    }
}