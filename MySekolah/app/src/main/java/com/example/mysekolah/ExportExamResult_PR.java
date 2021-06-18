package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ExportExamResult_PR extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    private GridView gridview;
    private TextView tvname, tvyear, tvtest;
    public static ArrayList<String> resultList;
    private ArrayAdapter<String> adapter;
    DatabaseAccess databaseAccess;

    String icChild;
    String childName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_exam_result_pr);
        gridview= findViewById(R.id.result_grid);
        //ArrayList
        resultList= new ArrayList<String>();
        adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,resultList );


        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        icChild= getIntent().getStringExtra("icChild");
        childName= getIntent().getStringExtra("childName");
        String school= getIntent().getExtras().getString("School");
        String year= getIntent().getExtras().getString("Year");
        String test= getIntent().getExtras().getString("Test");

        tvyear= findViewById(R.id.tvYear);
        tvname=findViewById(R.id.tvName);
        tvtest=findViewById(R.id.tvTest);

        tvname.setText(childName);
        tvyear.setText(year);
        tvtest.setText(test);


        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseAccess.ExportExamResult_PR(icChild,school,year,test);
        gridview.setAdapter(adapter);
        //databaseAccess.close();



        //First Check if the external storage is writable
        String state= Environment.getExternalStorageState();
        if(!Environment.MEDIA_MOUNTED.equals(state)){
            Toast.makeText(this, "Can access external library", Toast.LENGTH_LONG).show();
        }

        //Create a directory for your PDF
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "MyApp");
        if (!pdfDir.exists()){
            pdfDir.mkdir();
        }


        //Then take the screen shot
       /* Bitmap screen;
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        screen = Bitmap.createBitmap(v1.getDrawingCache());
        //Canvas canvas= new Canvas(screen);
        //v1.draw(canvas);
        v1.setDrawingCacheEnabled(false);*/

        Bitmap screen;
        View v1 = getWindow().getDecorView().getRootView();
        v1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v1.layout(0, 0, v1.getMeasuredWidth(), v1.getMeasuredHeight());
        v1.buildDrawingCache();
        screen = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);



        //Now create the name of your PDF file that you will generate
        File pdfFile = new File(pdfDir, "ExamResult.pdf");

        try {
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            addImage(document,byteArray);
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Uri uri = Uri.fromFile(new File(pdfDir,  "ExamResult.pdf"));
        Uri uri = FileProvider.getUriForFile(ExportExamResult_PR.this, BuildConfig.APPLICATION_ID+ ".provider", pdfDir);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
    private static void addImage(Document document,byte[] byteArray)
    {
        Image image = null;
        try
        {
            image = Image.getInstance(byteArray);
        }
        catch (BadElementException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try
        {
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}