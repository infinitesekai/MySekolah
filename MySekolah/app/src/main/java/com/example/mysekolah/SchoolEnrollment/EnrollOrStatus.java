package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mysekolah.Apply_List;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EnrollOrStatus extends AppCompatActivity implements View.OnClickListener {

    CardView application, checkStatus;
    String schoolLevel;
    private User currentUser;
    private int lastfragment;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolle_or_status_pre);

        //get the value pass from the previous activity
        currentUser = (User) getIntent().getSerializableExtra("user");
        schoolLevel=getIntent().getStringExtra("SchoolLevel");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        application = findViewById(R.id.ApplicationFormCard);
        checkStatus = findViewById(R.id.CheckStatusCard);
        title=findViewById(R.id.title_enrol_status);

        //Title of the page change according to what education level user chosen before
        switch (schoolLevel){
            case("PreSchool"):
                title.setText("Pre-School Education");
                break;
            case("Primary"):
                title.setText("Primary School Education");
                break;
            case("Secondary"):
                title.setText("Secondary School Education");
                break;

        }

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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//?????????values????????????????????????
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//?????????values????????????????????????
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
            case R.id.ApplicationFormCard:
                i = new Intent(this, Enroll_Check_IC.class);
                i.putExtra("SchoolLevel", schoolLevel);
                i.putExtra("user", currentUser);
                startActivity(i);
                break;

            case R.id.CheckStatusCard:
                i = new Intent(this, Apply_List.class);
                i.putExtra("user",currentUser);
                startActivity(i);
                break;

        }
    }
}