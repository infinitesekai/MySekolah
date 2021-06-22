package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mysekolah.HomePage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Enroll_Check_IC extends AppCompatActivity  {

    private Button check;
    private EditText ic;
    String schoolLevel;
    private User currentUser;
    private int lastfragment;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_check_ic);

        ic= findViewById(R.id.editTextIC);
        check= findViewById(R.id.btnCheck);
        title=findViewById(R.id.title_check_ic);

        //get the value pass from the previous activity
        schoolLevel=getIntent().getStringExtra("SchoolLevel");
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        switch (schoolLevel){
            case("PreSchool"):
                title.setText("Application for Government Pre-School");
                break;
            case("Primary"):
                title.setText("Application for Government Primary School");
                break;
            case("Secondary"):
                title.setText("Application for Government Secondary School");
                break;

        }

        //when check button clicked
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Enroll_Check_IC.this, SchoolForm.class);
                i.putExtra("ICNo", ic.getText().toString()); //ic pass from EditText
                i.putExtra("SchoolLevel", schoolLevel);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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


}