package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Sign_Up extends AppCompatActivity {
    private EditText ic_number,password,repassword,name,address,phoneno;//input text
    private Button signup,signin;//signup button,signin button

    //private DatabaseHelper DB;
    private int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //reference to view by id
        ic_number =findViewById(R.id.etx_signin_ic);
        name =findViewById(R.id.etx_signin_name);
        password =findViewById(R.id.etx_signup_password);
        repassword =findViewById(R.id.etx_signup_repassword);
        address =findViewById(R.id.etx_signup_address);
        phoneno =findViewById(R.id.etx_signup_phoneno);
        signup =findViewById(R.id.btn_signup);
        signin =findViewById(R.id.btn_signin);
        //get user type
        role = getIntent().getIntExtra("role", 0);

        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();

        // on click listener for export button
        //save all the information and create an account
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic = ic_number.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String nameStr = name.getText().toString().toUpperCase();
                String phonetr = phoneno.getText().toString();
                String addressStr = address.getText().toString();
                if (ic.isEmpty()) return;
                if (pass.isEmpty()) return;
                if (repass.isEmpty()) return;
                if (ic.equals("")||pass.equals("")||repass.equals("")) {
                    Toast.makeText(Sign_Up.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                } else{
                    if(pass.equals(repass)){
                        // Add a user if the user exists in residents
                        ArrayList<User> userList = DB.checkuseric(ic, nameStr);
                        Boolean checkuser = true;
                        User currentUser = null;
                        if (userList.size() == 0) {
                            checkuser = true;
                        } else  {
                            checkuser = false;
                            currentUser = userList.get(0);
                        }
                        // If cannot query:true, can query: false
                        if(!checkuser){
                            //insert data into user table
                            Boolean insert = DB.inserData(ic,pass, role, nameStr, phonetr, addressStr);
                            if(insert){
                                if(ContextCompat.checkSelfPermission(Sign_Up.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(Sign_Up.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
                                }
                                Toast.makeText(Sign_Up.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(),Login_page.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Sign_Up.this, "Registration failed ", Toast.LENGTH_SHORT).show();
                            }

                        } else{
                            Toast.makeText(Sign_Up.this, "Resident not exist,please use another ic number", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Sign_Up.this,"Password not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // on click listener for export button
        //user click here go back to login page
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login_page.class);
                startActivity(intent);
            }
        });


    }
}