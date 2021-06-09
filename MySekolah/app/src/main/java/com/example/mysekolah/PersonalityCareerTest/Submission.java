package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mysekolah.R;
import com.example.mysekolah.User;

import java.util.List;

public class Submission extends Activity implements View.OnClickListener{

    Button btn_cancel, btn_submit;
    int R_counter;
    int I_counter;
    int A_counter;
    int S_counter;
    int E_counter;
    int C_counter;

    private User currentUser;
    private int lastfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        R_counter=getIntent().getExtras().getInt("R_counter");
        I_counter=getIntent().getExtras().getInt("I_counter");
        A_counter=getIntent().getExtras().getInt("A_counter");
        S_counter=getIntent().getExtras().getInt("S_counter");
        E_counter=getIntent().getExtras().getInt("E_counter");
        C_counter=getIntent().getExtras().getInt("C_counter");

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;


        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        btn_cancel.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        final LoadingDialog loadingDialog = new LoadingDialog(Submission.this);
        int loading_time = 3000;

        switch (v.getId()){
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_submit:

                intent = new Intent(this, ResultPersonalityTest.class);

                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                        intent.putExtra("R_counter",R_counter);
                        intent.putExtra("I_counter",I_counter);
                        intent.putExtra("A_counter",A_counter);
                        intent.putExtra("S_counter",S_counter);
                        intent.putExtra("E_counter",E_counter);
                        intent.putExtra("C_counter",C_counter);
                        intent.putExtra("user",currentUser);
                        intent.putExtra("ICNo", currentUser.getICNo());
                        startActivity(intent);
                    }
                }, loading_time);
                break;
        }
    }
}