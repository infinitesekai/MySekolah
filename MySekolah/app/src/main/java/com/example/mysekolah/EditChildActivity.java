package com.example.mysekolah;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysekolah.adapter.ChildInfoAdapter;
import com.example.mysekolah.adapter.CustomDecoration;
import com.example.mysekolah.util.MyApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class EditChildActivity extends AppCompatActivity {
    private Button btnAddChild;//add child button
    private Button btnsave;//save button
    private User currentUser;//current user
    private RecyclerView rvChilds;//recycler view:children card
    private ChildInfoAdapter childInfoAdapter;//children information adapter
    private List<String> childs;//array list
    private AlertDialog.Builder mBuilder;//builder
    private View mView;//new view
    private EditText mICText;//edit text:ic
    private EditText mNameText;//edit text:name
    private int lastfragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);

        currentUser = (User) getIntent().getSerializableExtra("user");//get intent for current user
        rvChilds = findViewById(R.id.rv_child_info);
        btnAddChild = findViewById(R.id.btn_add_child);
        lastfragment=0;

        //navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();
        //get children array list
        ArrayList<User> users = DB.getPChilds(currentUser.getICNo());
        childs = new ArrayList<>();
        for (int i = 0; i < users.size();i++) {
            childs.add(users.get(i).getName());
        }
        childInfoAdapter = new ChildInfoAdapter(EditChildActivity.this,childs);
        childInfoAdapter.setOnDeleteChildListener(new ChildInfoAdapter.OnDeleteChildListener() {
            @Override
            public void onDelete(int position) {
                Boolean result = DB.deleteOneChild(currentUser.getICNo(), users.get(position).getICNo());
                if (result) {
                    childs.remove(position);
                    childInfoAdapter.notifyDataSetChanged();
                } else  {
                    Toast.makeText(EditChildActivity.this,"delete fail",Toast.LENGTH_SHORT).show();
                }

            }
        });
        rvChilds.setLayoutManager(new LinearLayoutManager(this));
        rvChilds.addItemDecoration(new CustomDecoration(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                20,
                getResources().getDisplayMetrics()
        )));
        rvChilds.setAdapter(childInfoAdapter);


        btnsave = findViewById(R.id.btn_save_child);
        //on click listener for save button
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //button save will link to profile page
                Fragment selectedFragment = null;
                selectedFragment = new ProfilePage();

                Bundle bundle = new Bundle();
                bundle.putSerializable("user",currentUser);//pass value current user
                selectedFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                DB.close();
            }
        });



        //on click listener for add child button
        btnAddChild.setOnClickListener(v -> {
            mBuilder = new AlertDialog.Builder(EditChildActivity.this);
            mView = View.inflate(getApplicationContext(), R.layout.child_add_view, null);
            //set layout
            mBuilder.setView(mView);
            //Get the builder and set view

            //get child's name and ic from imput
            mICText = (EditText) mView.findViewById(R.id.child_add_ic);
            mNameText = (EditText) mView.findViewById(R.id.child_add_name);

            //set view
            mBuilder.setTitle("Add Child").setIcon(android.R.drawable.ic_dialog_info)
                    .setView(mView)
                    //on click listener for cancel button
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            //confirm positive button to add child
            mBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String ic = mICText.getText().toString();
                    String name = mNameText.getText().toString();
                    User child = new User();
                    child.setICNo(ic);
                    child.setName(name);

                    //check ic and name of the child and show the status
                    if (DB.checkuseric(ic, name).size() > 0) {
                        if (DB.checkChid(currentUser.getICNo(), ic)) {
                            Boolean result = DB.addOneChild(currentUser.getICNo(), child);
                            if (result) {

                                Toast.makeText(EditChildActivity.this, "add child success！", Toast.LENGTH_SHORT).show();
                                childs.add(name);
                                childInfoAdapter.notifyDataSetChanged();
                            } else  {
                                Toast.makeText(getApplicationContext(), "add child fail", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "child exist!", Toast.LENGTH_SHORT).show();
                        }

                    } else  {
                        Toast.makeText(getApplicationContext(), "Resident not exist", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            mBuilder.show();
        });
    }


    //function for bottom navigation bar
    //back to Student Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:

                    if (currentUser.getRole() == 1) {
                        selectedFragment = new HomePage();
                    } else {
                        selectedFragment = new HomePage_Student();
                    }
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

                    if (currentUser.getRole() == 1) {
                        selectedFragment = new  SearchPage();
                    } else {
                        selectedFragment = new  SearchPage_Student();
                    }

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