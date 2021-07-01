package com.example.mysekolah.PersonalityCareerTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage_Student;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage_Student;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PersonalityTestHome extends AppCompatActivity implements View.OnClickListener {

    //initialize the cardview
    CardView take_test, test_result;
    //initialize user
    private User currentUser;
    //initialize database access
    DatabaseAccess dbAccess;
    //request permission
//    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test_home);

//        //ask permission to access the external storage
//        int permission = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // request permission
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    EXTERNAL_STORAGE_REQ_CODE);
//        }

        //initiate database access
        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        //keep track of user
        currentUser = (User) getIntent().getSerializableExtra("user");

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
                    bundle.putSerializable("user", currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.takeTestCard:
                intent = new Intent(this, InstructionPage.class);
                intent.putExtra("user", currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                startActivity(intent);
                break;
            case R.id.checkResultCard:

                //checking if there is result exist or not
                TestCharResult resultInfo;
                resultInfo = dbAccess.getPastResult(currentUser.getICNo());
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