package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * login pages
 * @author w
 *
 */
public class LoginActivity extends AppCompatActivity {

    private EditText ic_number,password;
    private Button btnlogin,btnSignUp;
    private DatabaseHelper DB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ic_number =findViewById(R.id.etx_ic_number);
        password =findViewById(R.id.etx_password);
        btnlogin =findViewById(R.id.btn_login);
        btnSignUp=findViewById(R.id.btn_signup);
        DB =new DatabaseHelper(this);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic= ic_number.getText().toString();
                String pass= password.getText().toString();
                if (ic.isEmpty()) return;
                if (pass.isEmpty()) return;
                if (ic.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkicpass =DB.checkusericpassword(ic,pass);
                    if(checkicpass==true){
                        Toast.makeText(LoginActivity.this,"Sign in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        //go to
                        Toast.makeText(LoginActivity.this,"invalid credetials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }
}