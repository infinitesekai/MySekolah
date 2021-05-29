package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_Up extends AppCompatActivity {
    private EditText ic_number,password,repassword;
    private Button signup,signin;
    private DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        ic_number =findViewById(R.id.etx_signin_ic);
        password =findViewById(R.id.etx_signuppassword);
        repassword =findViewById(R.id.etx_signuprepassword);
        signup =findViewById(R.id.btn_signup);
        signin =findViewById(R.id.btn_signin);
        DB =new DatabaseHelper(this);

       // android:onClick="methodtoexecute"
        //remember to add this back to xml

        /*signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic = ic_number.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                if (ic.isEmpty()) return;
                if (pass.isEmpty()) return;
                if (repass.isEmpty()) return;
                if (ic.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(Sign_Up.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser =DB.checkuseric(ic);
                        if(!checkuser){
                            Boolean insert =DB.inserData(ic,pass);
                            if(insert){
                                Toast.makeText(Sign_Up.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Sign_Up.this, "Registration failed ", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(Sign_Up.this, "User already exist!Please sign in", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        Toast.makeText(Sign_Up.this,"Password not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/





        //signin function
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login_page.class);
                startActivity(intent);
            }
        });


    }
}