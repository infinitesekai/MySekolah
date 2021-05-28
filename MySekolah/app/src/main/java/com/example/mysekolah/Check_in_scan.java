package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class Check_in_scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CodeScanner codeScanner;
        CodeScannerView scanView;
        TextView resultData;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_scan);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        scanView=findViewById(R.id.scanner_view);
        codeScanner=new CodeScanner(this,scanView);
        resultData=findViewById(R.id.QRtxt);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull @NotNull Result result) {
                //decoded QR code ,store and display result
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //extract data and assign to text view
                        resultData.setText(result.getText());
                    }
                });

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
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