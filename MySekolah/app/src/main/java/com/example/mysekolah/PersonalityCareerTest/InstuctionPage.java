package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mysekolah.R;
import com.example.mysekolah.User;

public class InstuctionPage extends Activity {

    Button start_test;

    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instuction_page);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        start_test = findViewById(R.id.start_test);

        start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_test();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .75));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    private void start_test() {
        Intent intent = new Intent(InstuctionPage.this, PersonalTestQuestion.class);
        intent.putExtra("user", currentUser);
        intent.putExtra("ICNo", currentUser.getICNo());
        startActivity(intent);
    }
}