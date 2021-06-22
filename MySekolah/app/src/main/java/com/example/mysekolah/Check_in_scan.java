package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Scanner;

public class Check_in_scan extends AppCompatActivity {
    //use Code Scanner library for Android, based on ZXing (ZebraCrossing)
    CodeScanner codeScanner;
    CodeScannerView scanView;

    TextView resultData;//text view to display result of the scan

    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_scan);

        //current user and last fragment
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //bottom naviagation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //reference to view by id
        scanView=findViewById(R.id.scanner_view);
        resultData=findViewById(R.id.QRtxt);

        //instance of code scanner with CodeScannerview
        //two arguments, this context and the scanner view to be used
        codeScanner=new CodeScanner(this,scanView);



        //decode qr code and display
        //able to scan multiple qr code once opened
        //use threading for result display in text view
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull @NotNull Result result) {
                //decoded QR code ,store and display result
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //extract data from result variable and assign to text view
                        resultData.setText(result.getText());

                        codeScanner.startPreview();//continue for preview after decode

                    }
                });

            }
        });

        //camera preview when click on the screen/scan view
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //ask user permission at runtime
        requestforCamera();


    }

    //request camera permission
    //Dexter :Android library that simplifies the process of requesting permissions at runtime
    private void requestforCamera() {
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();//start camera preview with permission granted
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                //make toast to inform camera persmission deny
                Toast.makeText(Check_in_scan.this,"Camera Permission is Required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                //keep asking camera permission until granted
                //need to operate with camera permission
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    //function for bottom navigation bar
    //back to Student Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
}