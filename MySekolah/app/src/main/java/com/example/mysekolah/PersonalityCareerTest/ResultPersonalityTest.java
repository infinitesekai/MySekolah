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

import com.example.mysekolah.BuildConfig;
import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage_Student;
import com.example.mysekolah.MainActivity;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage_Student;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResultPersonalityTest extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;

    FrameLayout ll_scroll_now;
    private Bitmap bitmap;
    Button result_export_now;

    Button result_quit;
    TextView Rvalue;
    TextView Ivalue;
    TextView Avalue;
    TextView Svalue;
    TextView Evalue;
    TextView Cvalue;
    TextView total;
    TextView H1;
    TextView H2;
    TextView H3;
    ImageButton image_btn;
    private User currentUser;
    private int lastfragment;
    DatabaseAccess dbAccess;
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
        setContentView(R.layout.activity_result_personality_test);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        int R_counter=getIntent().getExtras().getInt("R_counter");
        int I_counter=getIntent().getExtras().getInt("I_counter");
        int A_counter=getIntent().getExtras().getInt("A_counter");
        int S_counter=getIntent().getExtras().getInt("S_counter");
        int E_counter=getIntent().getExtras().getInt("E_counter");
        int C_counter=getIntent().getExtras().getInt("C_counter");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Rvalue=findViewById(R.id.first_total);
        Rvalue.setText(String.valueOf(R_counter));

        Ivalue=findViewById(R.id.second_total);
        Ivalue.setText(String.valueOf(I_counter));

        Avalue=findViewById(R.id.third_total);
        Avalue.setText(String.valueOf(A_counter));

        Svalue=findViewById(R.id.forth_total);
        Svalue.setText(String.valueOf(S_counter));

        Evalue=findViewById(R.id.fifth_total);
        Evalue.setText(String.valueOf(E_counter));

        Cvalue=findViewById(R.id.sixth_total);
        Cvalue.setText(String.valueOf(C_counter));

        total=findViewById(R.id.result_total);
        H1=findViewById(R.id.highest_result1);
        H2=findViewById(R.id.highest_result2);
        H3=findViewById(R.id.highest_result3);

        result1=findViewById(R.id.result_1);
        result2=findViewById(R.id.result_2);
        result3=findViewById(R.id.result_3);

        desc1=findViewById(R.id.description_1);
        exp1=findViewById(R.id.explanation);
        sug1=findViewById(R.id.suggestedField);

        desc2=findViewById(R.id.description_1_2);
        exp2=findViewById(R.id.explanation2);
        sug2=findViewById(R.id.suggestedField2);

        desc3=findViewById(R.id.description_1_3);
        exp3=findViewById(R.id.explanation3);
        sug3=findViewById(R.id.suggestedField3);

        name=findViewById(R.id.tester_name);
        testeric=findViewById(R.id.tester_ic);


        expandable_view = findViewById(R.id.expandable_view);
        expandable_view2 = findViewById(R.id.expandable_view2);
        expandable_view3 = findViewById(R.id.expandable_view3);

        imageView = findViewById(R.id.arrow_expand);
        imageView2 = findViewById(R.id.arrow_expand2);
        imageView3 = findViewById(R.id.arrow_expand3);

        cardView = findViewById(R.id.result_card);
        cardView2 = findViewById(R.id.result_card2);
        cardView3 = findViewById(R.id.result_card3);

        result_quit = findViewById(R.id.result_quit);
        result_export_now = findViewById(R.id.result_export_now);
        ll_scroll_now = findViewById(R.id.ll_scroll_now);


        image_btn = findViewById(R.id.imageButton);

        List<Integer> result=new ArrayList<Integer>();

        result.add(R_counter);
        result.add(I_counter);
        result.add(A_counter);
        result.add(S_counter);
        result.add(E_counter);
        result.add(C_counter);

        String R="R";
        String I="I";
        String A="A";
        String S="S";
        String E="E";
        String C="C";



        int totalscore=0;
        for(int i=0;i<result.size();i++){
//            int firstMax= Collections.max(result);
            totalscore+=result.get(i);
        }

        name.setText(currentUser.getName());
        testeric.setText(currentUser.getICNo());
        total.setText(String.valueOf(totalscore));

        //using hashmap to map the characters and counters
        HashMap<String,Integer> Character=new HashMap<String, Integer>();

        Character.put(R,R_counter);
        Character.put(I,I_counter);
        Character.put(A,A_counter);
        Character.put(S,S_counter);
        Character.put(E,E_counter);
        Character.put(C,C_counter);

        Map<String,Integer> sortedCharacter=sortByValue(Character);

        System.out.println(sortedCharacter);



        List<String> character =new ArrayList<String>();

        for(Map.Entry<String,Integer>entry:sortedCharacter.entrySet()){
            character.add(entry.getKey());
        }

        String firstChar=character.get(0);
        String secondChar=character.get(1);
        String thirdChar=character.get(2);

        for (int i = 0; i < 3; i++) {

            switch (character.get(0)) {
                case "R":
                    cardView.setCardBackgroundColor(Color.parseColor("#995a3f"));
                    break;
                case "I":
                    cardView.setCardBackgroundColor(Color.parseColor("#26768F"));
                    break;
                case "A":
                    cardView.setCardBackgroundColor(Color.parseColor("#4F4461"));
                    break;
                case "S":
                    cardView.setCardBackgroundColor(Color.parseColor("#2E8266"));
                    break;
                case "E":
                    cardView.setCardBackgroundColor(Color.parseColor("#525CDD"));
                    break;
                case "C":
                    cardView.setCardBackgroundColor(Color.parseColor("#B8685E"));
                    break;
            }
            switch (character.get(1)) {
                case "R":
                    cardView2.setCardBackgroundColor(Color.parseColor("#995a3f"));
                    break;
                case "I":
                    cardView2.setCardBackgroundColor(Color.parseColor("#26768F"));
                    break;
                case "A":
                    cardView2.setCardBackgroundColor(Color.parseColor("#4F4461"));
                    break;
                case "S":
                    cardView2.setCardBackgroundColor(Color.parseColor("#2E8266"));
                    break;
                case "E":
                    cardView2.setCardBackgroundColor(Color.parseColor("#525CDD"));
                    break;
                case "C":
                    cardView2.setCardBackgroundColor(Color.parseColor("#B8685E"));
                    break;
            }
            switch (character.get(2)) {
                case "R":
                    cardView3.setCardBackgroundColor(Color.parseColor("#995a3f"));
                    break;
                case "I":
                    cardView3.setCardBackgroundColor(Color.parseColor("#26768F"));
                    break;
                case "A":
                    cardView3.setCardBackgroundColor(Color.parseColor("#4F4461"));
                    break;
                case "S":
                    cardView3.setCardBackgroundColor(Color.parseColor("#2E8266"));
                    break;
                case "E":
                    cardView3.setCardBackgroundColor(Color.parseColor("#525CDD"));
                    break;
                case "C":
                    cardView3.setCardBackgroundColor(Color.parseColor("#B8685E"));
                    break;
            }
        }
        H1.setText(firstChar);
        H2.setText(secondChar);
        H3.setText(thirdChar);

        testInfo1=dbAccess.getTestInfo(firstChar);

        testInfo2=dbAccess.getTestInfo(secondChar);

        testInfo3=dbAccess.getTestInfo(thirdChar);

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



        boolean insert;

        insert = dbAccess.insertPersonalityResult(currentUser.getICNo(),firstChar,secondChar,thirdChar);

        if (insert) {
            Toast.makeText(ResultPersonalityTest.this, "Result stored", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(ResultPersonalityTest.this, "Result failed", Toast.LENGTH_SHORT).show();
        }




        result_quit.setOnClickListener(this);
        image_btn.setOnClickListener(this);
        result_export_now.setOnClickListener(this);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };

    //expand and show less 
    public void showmore(View view){
        if (expandable_view.getVisibility() == View.GONE){
            imageView.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandable_view.setVisibility(View.VISIBLE);
        }else {
            imageView.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandable_view.setVisibility(View.GONE);
        }
    }
    public void showmore2(View view){
        if (expandable_view2.getVisibility() == View.GONE){
            imageView2.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            expandable_view2.setVisibility(View.VISIBLE);
        }else {
            imageView2.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            expandable_view2.setVisibility(View.GONE);
        }
    }

    public void showmore3(View view){
        if (expandable_view3.getVisibility() == View.GONE){
            imageView3.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView3, new AutoTransition());
            expandable_view3.setVisibility(View.VISIBLE);
        }else {
            imageView3.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView3, new AutoTransition());
            expandable_view3.setVisibility(View.GONE);
        }
    }

    //sorting the value
    public static HashMap<String,Integer>sortByValue(HashMap<String,Integer> Character){
        //create a list from HashMap elements
        List<Map.Entry<String,Integer>>list= new LinkedList<Map.Entry<String, Integer>>(Character.entrySet());

        //start the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        //put data from sorted list to hashmap
        HashMap<String,Integer>temp=new LinkedHashMap<String, Integer>();
        for(Map.Entry<String,Integer>aa:list){
            temp.put(aa.getKey(),aa.getValue());
        }
        return temp;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.result_quit:
                intent = new Intent(this, PersonalityTestHome.class);
                intent.putExtra("user",currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                startActivity(intent);
                break;
            case R.id.result_export_now:
                Log.d("size"," "+ll_scroll_now.getWidth() +"  "+ll_scroll_now.getWidth());
                bitmap = loadBitmapFromView(ll_scroll_now, ll_scroll_now.getWidth(), ll_scroll_now.getHeight());
                createPdf();

            case  R.id.imageButton:
                intent = new Intent(this, ResultInfo.class);
                startActivity(intent);
                break;
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        //start first page
        PdfDocument document = new PdfDocument();
        int page_number=1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, page_number).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        //Create a directory for your PDF
//        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOCUMENTS), "MyApp");
//        if (!pdfDir.exists()){
//            pdfDir.mkdir();
//        }
        // write the document content
        String targetPdf = "/sdcard/"+ currentUser.getName() +".pdf";

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



    private void openGeneratedPDF(){
        File file = new File("/sdcard/"+ currentUser.getName() +".pdf");
//        File file = new File("/storage/emulated/0/Download/pdffromScroll.pdf");

        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
//            Uri uri = Uri.fromFile(file);
//            Context context= Past_Test_Result.this;
//            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            Uri uri = FileProvider.getUriForFile(ResultPersonalityTest.this, BuildConfig.APPLICATION_ID + ".provider",file);

            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(ResultPersonalityTest.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

}