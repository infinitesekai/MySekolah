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
    private Button btnAddChild;
    private Button btnsave;
    private User currentUser;
    private RecyclerView rvChilds;
    private ChildInfoAdapter childInfoAdapter;
    private List<String> childs;
    private AlertDialog.Builder mBuilder;
    private View mView;
    private EditText mICText;
    private EditText mNameText;
    private int lastfragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        //currentUser = MyApplication.currentUser;
        currentUser = (User) getIntent().getSerializableExtra("user");
        rvChilds = findViewById(R.id.rv_child_info);
        btnAddChild = findViewById(R.id.btn_add_child);
        lastfragment=0;


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();
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

        //添加

        btnsave = findViewById(R.id.btn_save_child);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = null;
                selectedFragment = new ProfilePage();
                //currentUser = MyApplication.currentUser;
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                selectedFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                DB.close();
            }
        });




        btnAddChild.setOnClickListener(v -> {
            mBuilder = new AlertDialog.Builder(EditChildActivity.this);
//            mBuilder = new AlertDialog.Builder(EditChildActivity.this);
            mView = View.inflate(getApplicationContext(), R.layout.child_add_view, null);
            //设置自定义的布局
            mBuilder.setView(mView);
            //拿到控件,设置数据
            mICText = (EditText) mView.findViewById(R.id.child_add_ic);
            mNameText = (EditText) mView.findViewById(R.id.child_add_name);



//            final EditText inputIc = new EditText(EditChildActivity.this);
//            final EditText inputName = new EditText(EditChildActivity.this);
//            inputIc.setText(dbStr);


            mBuilder.setTitle("Add Child").setIcon(android.R.drawable.ic_dialog_info)
                    .setView(mView)
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            mBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String ic = mICText.getText().toString();
                    String name = mNameText.getText().toString().toUpperCase();
                    User child = new User();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    child.setICNo(ic);
                    child.setName(name);
                    if (DB.checkuseric(ic, name).size() > 0) {
                        if (DB.checkChid(currentUser.getICNo(), ic)) {
                            Boolean result = DB.addOneChild(currentUser.getICNo(), child);
                            if (result) {
                                //MyApplication.currentUser.setICNo(ic);
                               // MyApplication.currentUser.setName(name);
                                //currentUser.setICNo(ic);
                               // currentUser.setName(name);
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