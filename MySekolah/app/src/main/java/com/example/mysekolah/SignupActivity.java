package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText ic_number,password,repassword;
    private Button signup,signin;
    private DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        ic_number =findViewById(R.id.etx_ic_number);
        password =findViewById(R.id.etx_signuppassword);
        repassword =findViewById(R.id.etx_signuprepassword);
        signup =findViewById(R.id.btn_signup);
        signin =findViewById(R.id.btn_signin);
        DB =new DatabaseHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic = ic_number.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                if (ic.isEmpty()) return;
                if (pass.isEmpty()) return;
                if (repass.isEmpty()) return;
                if (ic.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(SignupActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser =DB.checkuseric(ic);
                        if(!checkuser){
                            Boolean insert =DB.inserData(ic,pass);
                            if(insert){
                                Toast.makeText(SignupActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(),HomePage.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignupActivity.this, "Registration failed ", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(SignupActivity.this, "User already exist!Please sign in", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        Toast.makeText(SignupActivity.this,"Password not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //signin function
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }
}