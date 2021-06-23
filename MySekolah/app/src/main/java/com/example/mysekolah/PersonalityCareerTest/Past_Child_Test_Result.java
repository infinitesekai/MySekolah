package com.example.mysekolah.PersonalityCareerTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysekolah.BuildConfig;
import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.example.mysekolah.att_select_child;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Past_Child_Test_Result extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;
    Button result_quit;

    FrameLayout ll_scroll_child;
    private Bitmap bitmap;
    Button result_export_child;

    TextView H1;
    TextView H2;
    TextView H3;

    String icChild;
    String childName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_child_test_result);

        childName = getIntent().getStringExtra("childName");
        icChild = getIntent().getStringExtra("icChild");
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        H1 = findViewById(R.id.highest_result1_child);
        H2 = findViewById(R.id.highest_result2_child);
        H3 = findViewById(R.id.highest_result3_child);

        result1 = findViewById(R.id.result_1_child);
        result2 = findViewById(R.id.result_2_child);
        result3 = findViewById(R.id.result_3_child);

        desc1 = findViewById(R.id.description11_child);
        exp1 = findViewById(R.id.explanation_child);
        sug1 = findViewById(R.id.suggestedField_child);

        desc2 = findViewById(R.id.description22_child);
        exp2 = findViewById(R.id.explanation2_child);
        sug2 = findViewById(R.id.suggestedField2_child);

        desc3 = findViewById(R.id.description33_child);
        exp3 = findViewById(R.id.explanation3_child);
        sug3 = findViewById(R.id.suggestedField3_child);

        name = findViewById(R.id.tester_name_child);
        testeric = findViewById(R.id.tester_ic_child);


        expandable_view = findViewById(R.id.expandable_view_child);
        expandable_view2 = findViewById(R.id.expandable_view2_child);
        expandable_view3 = findViewById(R.id.expandable_view3_child);

        imageView = findViewById(R.id.arrow_expand_child);
        imageView2 = findViewById(R.id.arrow_expand2_child);
        imageView3 = findViewById(R.id.arrow_expand3_child);

        cardView = findViewById(R.id.result_card_child);
        cardView2 = findViewById(R.id.result_card2_child);
        cardView3 = findViewById(R.id.result_card3_child);

        result_quit = findViewById(R.id.result_quit_child);
        result_export_child = findViewById(R.id.result_export_child);
        ll_scroll_child = findViewById(R.id.ll_scroll_child);

        name.setText(childName);
        testeric.setText(icChild);


        resultInfo = dbAccess.getPastResult(icChild);

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
        desc1.setText(testInfo1.getDesc());
        exp1.setText(testInfo1.getExp());
        sug1.setText(testInfo1.getField());

        result2.setText(testInfo2.getAlpName());
        desc2.setText(testInfo2.getDesc());
        exp2.setText(testInfo2.getExp());
        sug2.setText(testInfo2.getField());

        result3.setText(testInfo3.getAlpName());
        desc3.setText(testInfo3.getDesc());
        exp3.setText(testInfo3.getExp());
        sug3.setText(testInfo3.getField());

        for (int i = 0; i < chars.size(); i++) {

            switch (chars.get(0)) {
                case "R":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    break;
                case "I":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    break;
                case "A":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    break;
                case "S":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    break;
                case "E":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    break;
                case "C":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    break;
            }
            switch (chars.get(1)) {
                case "R":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    break;
                case "I":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    break;
                case "A":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    break;
                case "S":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    break;
                case "E":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    break;
                case "C":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    break;
            }
            switch (chars.get(2)) {
                case "R":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    break;
                case "I":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    break;
                case "A":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    break;
                case "S":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    break;
                case "E":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    break;
                case "C":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    break;
            }
        }
        result_quit.setOnClickListener(this);
        result_export_child.setOnClickListener(this);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
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
                    selectedFragment = new SearchPage();
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
        String personalityTestMessage = "personality";

        switch (v.getId()) {
            case R.id.result_quit_child:
                intent = new Intent(this, att_select_child.class);
                intent.putExtra("user", currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                intent.putExtra("message", personalityTestMessage);
                startActivity(intent);
                break;
            case R.id.result_export_child:
                Log.d("size", " " + ll_scroll_child.getWidth() + "  " + ll_scroll_child.getWidth());
                bitmap = loadBitmapFromView(ll_scroll_child, ll_scroll_child.getWidth(), ll_scroll_child.getHeight());
                createPdf();
        }
    }

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

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        //start first page
        PdfDocument document = new PdfDocument();
        int page_number = 2;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, page_number).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        //Create a directory for your PDF
//        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOCUMENTS), "MyApp");
//        if (!pdfDir.exists()){
//            pdfDir.mkdir();
//        }
        // write the document content
        String targetPdf = "/sdcard/"+ childName+"_personality_test.pdf";

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
        File file = new File("/sdcard/"+ childName+"_personality_test.pdf");
//        File file = new File("/storage/emulated/0/Download/pdffromScroll.pdf");

        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri uri = Uri.fromFile(file);
//            Context context= Past_Test_Result.this;
//            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            Uri uri = FileProvider.getUriForFile(Past_Child_Test_Result.this, BuildConfig.APPLICATION_ID + ".provider", file);

            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Past_Child_Test_Result.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
}