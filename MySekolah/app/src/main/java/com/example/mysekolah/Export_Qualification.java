package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//export qualification infotmation
public class Export_Qualification extends AppCompatActivity {
    private WebView webView;

    private String ic, name, preschool, pre_year, primary_school, primary_year, secondary_school, secondary_year, qualification, qualification_year;
    private User currentUser;
    private int lastfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_qualification);

        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //current user and lalst fragment
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;


        //web view
        webView=findViewById(R.id.webView);

        //initiate database access
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //get ic no of current user
        String current_IC = getIntent().getExtras().getString("ICNo");

        //call database method to get qualification record
        //store in Qualification object
        Qualification qualification_record=databaseAccess.DisplayQualification(current_IC);

        //assign qualification information to string variable
        ic=qualification_record.getICNo();
        name=qualification_record.getName();
        preschool=qualification_record.getPreSchool();
        pre_year=qualification_record.getPreYear();
        primary_school=qualification_record.getPrimarySchool();
        primary_year=qualification_record.getPrimaryYear();
        secondary_school=qualification_record.getSecondarySchool();
        secondary_year=qualification_record.getSecondaryYear();
        qualification=qualification_record.getqualification();
        qualification_year=qualification_record.getqualificationYear();

        //use webview for export
        //write simple html for table format
                String HTML="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "  width:30%\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Academic Qualification</h2>\n" +
                "\n" +
                "<table>\n" +
                " \n" +
                "  <tr>\n" +
                "    <td>No IC</td>\n" +
                "    <td >"+ic+ "</td>\n" +
                "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>Name</td>\n" +
                        "    <td >"+name+ "</td>\n" +
                        "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Pre-School</td>\n" +
                "    <td>"+preschool+"</td>\n" +
                "    <td>"+pre_year+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "      <td>Primary School</td>\n" +
                "    <td>"+primary_school+"</td>\n" +
                "    <td>"+primary_year+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "     <td>Secondary School</td>\n" +
                "    <td>"+secondary_school+"</td>\n" +
                "    <td>"+secondary_year+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "       <td>Highest Qualification</td>\n" +
                "    <td>"+qualification+"</td>\n" +
                "    <td>"+qualification_year+"</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";


        //Loads written HTML into this WebView, using null as the base URL
        //mimeType specifies format of data in text/html
        //encoding in utf-8
        //history entry null
        webView.loadDataWithBaseURL(null,HTML,"text/html","utf-8",null);

        databaseAccess.close();//close database access




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

    //download button on click
    //create PDF function
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void createPDF(View view){

        Context context=Export_Qualification.this;
        //print to pdf
        //accessing printing capabilities
        PrintManager printManager=(PrintManager)Export_Qualification.this.getSystemService(context.PRINT_SERVICE);

        //PrintDocumentAdapter provide content of Webview to be printed
        // the adapter converts the WebView contents to a PDF stream
       PrintDocumentAdapter adapter=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            adapter=webView.createPrintDocumentAdapter();//deprecated but still functional
        }
        String JobName=getString(R.string.app_name) +"Document";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            //print job started by calling print
            //bring up system print ui
            //user can select print option
            //download pdf
            PrintJob printJob=printManager.print(JobName,adapter,new PrintAttributes.Builder().build());
        }




    }
}