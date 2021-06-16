package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysekolah.util.MyApplication;

public class Login_page extends AppCompatActivity {
    private EditText ic_number,password;
    private Button btnlogin,btnSignUp;
   // private DatabaseHelper DB;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        ic_number =findViewById(R.id.etx_ic_number);
        password =findViewById(R.id.etx_password);
        btnlogin =findViewById(R.id.btn_login);
        btnSignUp=findViewById(R.id.btn_sign_up);
       // DB = new DatabaseHelper(this);
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();


        //temp link to main page
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(Login_page.this,MainActivity.class);
               startActivity(i);
            }
        });

       btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic= ic_number.getText().toString();
                String pass= password.getText().toString();
                if (ic.isEmpty()) return;
                if (pass.isEmpty()) return;
                if (ic.equals("")||pass.equals(""))
                    Toast.makeText(Login_page.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    User user =DB.checkusericpassword(ic,pass);
                    if(user.getICNo() != null){
                        Toast.makeText(Login_page.this,"Sign in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        //MyApplication.currentUser = user;
                        currentUser = user;
                        intent.putExtra("user", currentUser);
                        startActivity(intent);
                        finish();

                    }else{
                        //go to
                        Toast.makeText(Login_page.this,"invalid credetials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Login_page.this, sign_up_role.class));
                    }
                });
    }
}