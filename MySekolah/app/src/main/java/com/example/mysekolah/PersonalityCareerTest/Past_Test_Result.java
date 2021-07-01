package com.example.mysekolah.PersonalityCareerTest;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.mysekolah.BuildConfig;
import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage_Student;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage_Student;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Past_Test_Result extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;
    Button result_quit;
    TextView H1;
    TextView H2;
    TextView H3;

    ScrollView ll_scroll;
    private Bitmap bitmap;
    Button result_export;

    private User currentUser;
    private int lastfragment;
    DatabaseAccess dbAccess;
    TestCharResult resultInfo;
    TestResultInfo testInfo1;
    TestResultInfo testInfo2;
    TestResultInfo testInfo3;

    TextView result1;
    TextView result2;
    TextView result3;
    TextView desc1;
    TextView desc2;
    TextView desc3;
    TextView exp1;
    TextView exp2;
    TextView exp3;
    TextView sug1;
    TextView sug2;
    TextView sug3;
    TextView name;
    TextView testeric;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_test_result);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        H1 = findViewById(R.id.highest_result1_past);
        H2 = findViewById(R.id.highest_result2_past);
        H3 = findViewById(R.id.highest_result3_past);

        result1 = findViewById(R.id.result_1_past);
        result2 = findViewById(R.id.result_2_past);
        result3 = findViewById(R.id.result_3_past);

        desc1 = findViewById(R.id.description11_past);
        exp1 = findViewById(R.id.explanation_past);
        sug1 = findViewById(R.id.suggestedField_past);

        desc2 = findViewById(R.id.description22_past);
        exp2 = findViewById(R.id.explanation2_past);
        sug2 = findViewById(R.id.suggestedField2_past);

        desc3 = findViewById(R.id.description33_past);
        exp3 = findViewById(R.id.explanation3_past);
        sug3 = findViewById(R.id.suggestedField3_past);

        name = findViewById(R.id.tester_name_past);
        testeric = findViewById(R.id.tester_ic_past);


        expandable_view = findViewById(R.id.expandable_view_past);
        expandable_view2 = findViewById(R.id.expandable_view2_past);
        expandable_view3 = findViewById(R.id.expandable_view3_past);

        imageView = findViewById(R.id.arrow_expand_past);
        imageView2 = findViewById(R.id.arrow_expand2_past);
        imageView3 = findViewById(R.id.arrow_expand3_past);

        cardView = findViewById(R.id.result_card_past);
        cardView2 = findViewById(R.id.result_card2_past);
        cardView3 = findViewById(R.id.result_card3_past);

        result_quit = findViewById(R.id.result_quit_past);
        result_export = findViewById(R.id.result_export);
        ll_scroll = findViewById(R.id.ll_scroll);

        name.setText(currentUser.getName());
        testeric.setText(currentUser.getICNo());


        resultInfo = dbAccess.getPastResult(currentUser.getICNo());

        String firstChar = resultInfo.getfirstChar();
        String secondChar = resultInfo.getsecondChar();
        String thirdChar = resultInfo.getthirdChar();
        List<String> chars = new ArrayList<>();
        chars.add(firstChar);
        chars.add(secondChar);
        chars.add(thirdChar);

        H1.setText(firstChar);
        H2.setText(secondChar);
        H3.setText(thirdChar);

        testInfo1 = dbAccess.getTestInfo(firstChar);

        testInfo2 = dbAccess.getTestInfo(secondChar);

        testInfo3 = dbAccess.getTestInfo(thirdChar);

        result1.setText(testInfo1.getAlpName());
        exp1.setText(testInfo1.getExp());
        sug1.setText(testInfo1.getField());

        result2.setText(testInfo2.getAlpName());
        exp2.setText(testInfo2.getExp());
        sug2.setText(testInfo2.getField());

        result3.setText(testInfo3.getAlpName());
        exp3.setText(testInfo3.getExp());
        sug3.setText(testInfo3.getField());

        for (int i = 0; i < chars.size(); i++) {

            switch (chars.get(0)) {
                case "R":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    desc1.setText(getString(R.string.people) + testInfo1.getDesc() + "\n" + getString(R.string.realistic));
                    break;
                case "I":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    desc1.setText(getString(R.string.people) + testInfo1.getDesc() + "\n" + getString(R.string.investigative));
                    break;
                case "A":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    desc1.setText(getString(R.string.people) + testInfo1.getDesc() + "\n" + getString(R.string.artistic));
                    break;
                case "S":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    desc1.setText(getString(R.string.people) + testInfo1.getDesc() + "\n" + getString(R.string.social));
                    break;
                case "E":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    desc1.setText(getString(R.string.people) + testInfo1.getDesc() + "\n" + getString(R.string.enterprising));
                    break;
                case "C":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    desc1.setText(getString(R.string.people) + testInfo1.getDesc() + getString(R.string.conventional));
                    break;
            }
            switch (chars.get(1)) {
                case "R":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    desc2.setText(getString(R.string.people) + testInfo2.getDesc() + "\n" + getString(R.string.realistic));
                    break;
                case "I":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    desc2.setText(getString(R.string.people) + testInfo2.getDesc() + "\n" + getString(R.string.investigative));
                    break;
                case "A":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    desc2.setText(getString(R.string.people) + testInfo2.getDesc() + "\n" + getString(R.string.artistic));
                    break;
                case "S":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    desc2.setText(getString(R.string.people) + testInfo2.getDesc() + "\n" + getString(R.string.social));
                    break;
                case "E":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    desc2.setText(getString(R.string.people) + testInfo2.getDesc() + "\n" + getString(R.string.enterprising));
                    break;
                case "C":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    desc2.setText(getString(R.string.people) + testInfo2.getDesc() + getString(R.string.conventional));
                    break;
            }
            switch (chars.get(2)) {
                case "R":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    desc3.setText(getString(R.string.people) + testInfo3.getDesc() + "\n" + getString(R.string.realistic));
                    break;
                case "I":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    desc3.setText(getString(R.string.people) + testInfo3.getDesc() + "\n" + getString(R.string.investigative));
                    break;
                case "A":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    desc3.setText(getString(R.string.people) + testInfo3.getDesc() + "\n" + getString(R.string.artistic));
                    break;
                case "S":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    desc3.setText(getString(R.string.people) + testInfo3.getDesc() + "\n" + getString(R.string.social));
                    break;
                case "E":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    desc3.setText(getString(R.string.people) + testInfo3.getDesc() + "\n" + getString(R.string.enterprising));
                    break;
                case "C":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    desc3.setText(getString(R.string.people) + testInfo3.getDesc() + getString(R.string.conventional));
                    break;
            }
        }



        result_quit.setOnClickListener(this);
        result_export.setOnClickListener(this);


    }




    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//the value that we need to pass
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//the value that we need to pass
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//the value that we need to pass
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };

    //expand and show less
    public void showmore(View view) {
        if (expandable_view.getVisibility() == View.GONE) {
            imageView.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandable_view.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandable_view.setVisibility(View.GONE);
        }
    }

    public void showmore2(View view) {
        if (expandable_view2.getVisibility() == View.GONE) {
            imageView2.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            expandable_view2.setVisibility(View.VISIBLE);
        } else {
            imageView2.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            expandable_view2.setVisibility(View.GONE);
        }
    }

    public void showmore3(View view) {
        if (expandable_view3.getVisibility() == View.GONE) {
            imageView3.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView3, new AutoTransition());
            expandable_view3.setVisibility(View.VISIBLE);
        } else {
            imageView3.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView3, new AutoTransition());
            expandable_view3.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.result_quit_past:
                intent = new Intent(this, PersonalityTestHome.class);
                intent.putExtra("user", currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                startActivity(intent);
                break;

            case R.id.result_export:
                //ask permission to access the external storage
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // request permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            EXTERNAL_STORAGE_REQ_CODE);
                }

                Log.d("size", " " + ll_scroll.getWidth() + "  " + ll_scroll.getWidth());
                bitmap = loadBitmapFromView(ll_scroll, ll_scroll.getWidth(), ll_scroll.getHeight());
                createPdf();
                break;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }


    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHight = (int) hight, convertWidth = (int) width;

        //start first page
        PdfDocument document = new PdfDocument();

        int page_number = 1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHight, page_number).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);


        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHight, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/" + currentUser.getName() + "_past_personality_test.pdf";

//        String targetPdf = "/storage/emulated/0/Download/pdffromScroll.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF() {
        File file = new File("/sdcard/" + currentUser.getName() + "_past_personality_test.pdf");

        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);


            Uri uri = FileProvider.getUriForFile(Past_Test_Result.this, BuildConfig.APPLICATION_ID + ".provider", file);

            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Past_Test_Result.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }


}