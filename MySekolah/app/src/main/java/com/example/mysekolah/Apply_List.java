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

public class Apply_List extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;
    DatabaseAccess databaseAccess;
    ListView apply_list;

    public static ArrayList<String> list_item;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        apply_list=findViewById(R.id.apply_list);

        list_item=new ArrayList<String>();
        String icParent=currentUser.getICNo();

        databaseAccess.getApplicationList(icParent);
//        databaseAccess.getApplicationList(currentUser.getICNo());

        viewApplication();

        apply_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String childName=apply_list.getItemAtPosition(position).toString();
                String status=databaseAccess.getStatus(childName);
                Intent i;

                switch (status){
                    case ("1"):
                        i= new Intent(Apply_List.this, Apply_pending.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("childname", childName);
                        startActivity(i);
                    case ("2"):
                        i= new Intent(Apply_List.this, Apply_success.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("childname", childName);
                        startActivity(i);
                    case ("3"):
                        i= new Intent(Apply_List.this, Apply_fail.class);
                        i.putExtra("user",currentUser);
                        i.putExtra("childname", childName);
                        startActivity(i);

                }

            }
        });







    }

    private void viewApplication() {
       // Cursor cursor=databaseAccess.getApplicationList(currentUser.getICNo());

//        if(cursor.getCount()==0)
//            Toast.makeText(this,"No application",Toast.LENGTH_SHORT).show();
//
//        else{
//            while(cursor.moveToFirst()){
//                list_item.add(cursor.getString(0));
//            }

//        if (cursor.moveToFirst()){
//            do{
//                list_item.add(cursor.getString(0));
//
//            }while (cursor.moveToNext());
//        }



            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_item);
            apply_list.setAdapter(adapter);
//        }
    }


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
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    lastfragment = R.id.nav_notif;
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
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
}