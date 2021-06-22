package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mysekolah.PersonalityCareerTest.Past_Child_Test_Result;
import com.example.mysekolah.PersonalityCareerTest.Past_Test_Result;
import com.example.mysekolah.PersonalityCareerTest.PersonalityTestHome;
import com.example.mysekolah.PersonalityCareerTest.TestCharResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class att_select_child extends AppCompatActivity {


    DatabaseAccess databaseAccess;
    GridView gridView;
    private User currentUser;
    private Dependency dependency;
    private int lastfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_select_child);

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        gridView= findViewById(R.id.simpleGridView);

        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ArrayList<Dependency> dependencyArrayList=databaseAccess.getdependency(currentUser.getICNo());

        GridViewApdater gridViewApdater= new GridViewApdater(this, dependencyArrayList);
        gridView.setAdapter(gridViewApdater);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                dependency= gridViewApdater.getItem(position);
                String icChild= dependency.getChildIC();
                String childName= dependency.getChildName();

                switch (str){
                    case ("exam"):
                        i= new Intent(att_select_child.this, ExamResultFormPr.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("icChild", icChild);
                        i.putExtra("childName", childName);

                        startActivity(i);
                        break;
                    case ("attendance"):
                        i= new Intent(att_select_child.this, Attendance_Form.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("icChild", icChild);
                        i.putExtra("childName", childName);
                        startActivity(i);
                        break;
                    case ("discipline"):
                        i= new Intent(att_select_child.this, Discipline_Form.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("icChild", icChild);
                        i.putExtra("childName", childName);
                        startActivity(i);
                        break;
                    case ("personality"):
                        TestCharResult resultInfo;
                        resultInfo=databaseAccess.getPastResult(icChild);
                        if (resultInfo == null) {
                            Toast.makeText(
                                    att_select_child.this,
                                    "There is no result for your child yet.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }else {
                            i = new Intent(att_select_child.this, Past_Child_Test_Result.class);
                            i.putExtra("user", currentUser);
                            i.putExtra("icChild", icChild);
                            i.putExtra("childName", childName);
                            startActivity(i);
                            break;
                        }
                }
            }
        });


    }

    //function for bottom navigation bar
    //back to Parent Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
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