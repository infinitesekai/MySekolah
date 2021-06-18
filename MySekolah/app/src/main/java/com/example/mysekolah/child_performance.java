package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mysekolah.PersonalityCareerTest.TestCharResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class child_performance extends AppCompatActivity implements View.OnClickListener {

    public CardView exam, discipline, attendance, personalitytest;
    private User currentUser;
    private int lastfragment;
    DatabaseAccess dbAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_performance);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        attendance = findViewById(R.id.AttendanceCard);
        exam = findViewById(R.id.ExamResultCard);
        discipline = findViewById(R.id.DisciplineCard);
        personalitytest = findViewById(R.id.PersonalityCard);

        attendance.setOnClickListener(this);
        exam.setOnClickListener(this);
        discipline.setOnClickListener(this);
        personalitytest.setOnClickListener(this);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
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
        String examMessage = "exam";
        String attendanceMessage = "attendance";
        String disciplineMessage = "discipline";
        String personalityTestMessage = "personality";

        switch (v.getId()) {
            case R.id.AttendanceCard:
                i = new Intent(this, att_select_child.class);
                i.putExtra("user", currentUser);
                i.putExtra("message", attendanceMessage);
                startActivity(i);
                break;
            case R.id.ExamResultCard:
                i = new Intent(this, att_select_child.class);
                i.putExtra("user", currentUser);
                i.putExtra("message", examMessage);
                startActivity(i);
                break;

            case R.id.DisciplineCard:
                i = new Intent(this, att_select_child.class);
                i.putExtra("user", currentUser);
                i.putExtra("message", disciplineMessage);
                startActivity(i);
                break;
            case R.id.PersonalityCard:


                i = new Intent(this, att_select_child.class);
                i.putExtra("user", currentUser);
                i.putExtra("message", personalityTestMessage);
                startActivity(i);
                break;

        }
    }

}