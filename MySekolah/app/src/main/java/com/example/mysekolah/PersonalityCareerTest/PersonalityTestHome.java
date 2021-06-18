package com.example.mysekolah.PersonalityCareerTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage_Student;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.SearchPage_Student;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PersonalityTestHome extends AppCompatActivity implements View.OnClickListener {

    CardView take_test, test_result;
    private User currentUser;
    private int lastfragment;
    DatabaseAccess dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test_home);

        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        take_test = findViewById(R.id.takeTestCard);
        test_result = findViewById(R.id.checkResultCard);

        take_test.setOnClickListener(this);
        test_result.setOnClickListener(this);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            //the navigation
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
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
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.takeTestCard:
                intent = new Intent(this, InstuctionPage.class);
                intent.putExtra("user",currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                startActivity(intent);
                break;
            case R.id.checkResultCard:

                //checking if there is result exist or not
                TestCharResult resultInfo;
                resultInfo=dbAccess.getPastResult(currentUser.getICNo());
                if (resultInfo == null) {
                    Toast.makeText(
                            PersonalityTestHome.this,
                            "No result yet, please take the test.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    intent = new Intent(this, Past_Test_Result.class);
                    intent.putExtra("user", currentUser);
                    intent.putExtra("ICNo", currentUser.getICNo());
                    startActivity(intent);
                    break;
                }
        }
    }

}