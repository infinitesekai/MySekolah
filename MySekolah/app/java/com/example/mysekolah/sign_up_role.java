package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class sign_up_role extends AppCompatActivity {

    private CardView parentView;
    private CardView studentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_role);
        parentView = findViewById(R.id.parentCard);
        studentView = findViewById(R.id.studentCard);

        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("1","i am parent");
                Intent intent = new Intent(sign_up_role.this,Sign_Up.class);
                intent.putExtra("role",1);
                startActivity(intent);
                finish();
            }
        });

        studentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("1","im a student");
                Intent intent = new Intent(sign_up_role.this,Sign_Up.class);
                intent.putExtra("role",2);//传值，用户类型
                startActivity(intent);
                finish();
            }
        });
    }
}