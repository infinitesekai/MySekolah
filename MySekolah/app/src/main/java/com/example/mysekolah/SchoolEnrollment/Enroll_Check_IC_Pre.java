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

import com.example.mysekolah.DatabaseHelper;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Enroll_Check_IC_Pre extends AppCompatActivity  {

    private Button check;
    private EditText ic;
    private DatabaseHelper mDBHelper;
    String schoolLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_check_ic);

        mDBHelper= new DatabaseHelper(this);
        ic= findViewById(R.id.editTextIC);
        check= findViewById(R.id.btnCheck);

        schoolLevel=getIntent().getStringExtra("SchoolLevel");



        //Check exists database
       /* File database= getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();
            //database.close();
            //Copy db
            if (copyDatabase(this)){
                Toast.makeText(this, "Copy databse sucess", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_LONG).show();
                return;
            }
        }*/


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i= new Intent(Enroll_Check_IC_Pre.this, PreSchoolForm.class);
                i.putExtra("ICNo", ic.getText().toString());
                i.putExtra("SchoolLevel", schoolLevel);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    /*private Boolean copyDatabase(Context context){
        try{
            InputStream inputStream= context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName= DatabaseHelper.DBLOCATION+ DatabaseHelper.DBNAME;
            OutputStream outputStream= new FileOutputStream(outFileName);
            byte[]buff= new byte[1024];
            int length=0;
            while ((length=inputStream.read(buff))>0){
                outputStream.write(buff,0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("Enroll_Check_IC_Pre", "DB copied");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    break;
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };


}