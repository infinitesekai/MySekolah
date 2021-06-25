package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

//application list
public class Apply_List extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    DatabaseAccess databaseAccess;

    //list view, list item and adapter to display list of application
    ListView apply_list;
    public static ArrayList<String> list_item;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //reference to list view by id
        apply_list=findViewById(R.id.apply_list);

        //array list to store list item
        list_item=new ArrayList<String>();

        String icParent=currentUser.getICNo();//get IC No of current user(parent）

        //call database method to get application list
        //child added into list_item
        //list_item is the list of application for children
        databaseAccess.getApplicationList(icParent);

        if(list_item.isEmpty()){
            Toast.makeText(Apply_List.this, "No Application", Toast.LENGTH_SHORT).show();

        }

        //array adapter for ListView display-insert item into ListView from list_item
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_item);

        apply_list.setAdapter(adapter);//conjoin array adapter with list view

        //click on list item
        apply_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected child name from list according to the position
                String childName=apply_list.getItemAtPosition(position).toString();

                //call database method to get application status of selected child
                String status=databaseAccess.getStatus(childName);
                Intent i;

                //start intent to navigate to respective page according to application status
                //application status=1, pending
                //application status=2, success
                //application status=3, fail
                switch (status){
                    case ("1"):
                        i= new Intent(Apply_List.this, Apply_pending.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("childname", childName);
                        startActivity(i);
                        break;
                    case ("2"):
                        i= new Intent(Apply_List.this, Apply_success.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("childname", childName);
                        startActivity(i);
                        break;
                    case ("3"):
                        i= new Intent(Apply_List.this, Apply_fail.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("childname", childName);
                        startActivity(i);
                        break;

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