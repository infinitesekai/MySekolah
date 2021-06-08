package com.example.mysekolah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mysekolah.PersonalityCareerTest.PersonalityTestHome;
import com.example.mysekolah.SchoolEnrollment.SchoolEnroll;

import java.util.ArrayList;


public class SearchPage_Student extends Fragment implements SearchView.OnQueryTextListener{

    SearchView searchView;
    ListView listView;
    ListViewAdapter adapter;
    String [] functionList;
    ArrayList<FunctionName> arraylist= new ArrayList<FunctionName>();

    private User currentUser;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_page, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        searchView= (SearchView) v.findViewById(R.id.searchView);
        listView= (ListView) v.findViewById(R.id.listview);

        functionList= new String[] {"Exam Result", "Academic Qualification", "Personality Test", "Tertiary Info", "Attendance Check In", "Exam Timetable"};

        for(int i=0; i< functionList.length; i++){
            FunctionName functionName= new FunctionName(functionList[i]);
            arraylist.add(functionName);
        }


        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getContext(), arraylist);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(this);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FunctionName function= adapter.getItem(position);
                String functionName= function.getFunctions();

                Log.d("function", functionName);
                Intent i;


                switch (functionName) {
                    case ("Exam Result"):
                        i = new Intent(getActivity(), ExamResultForm.class);
                        i.putExtra("user", currentUser);
                        i.putExtra("ICNo", currentUser.getICNo());

                        startActivity(i);
                        break;

                    case ("Personality Test"):
                        i = new Intent(getActivity(), PersonalityTestHome.class);
                        i.putExtra("user", currentUser);
                        startActivity(i);
                        break;

                    case ("Academic Qualification"):
                        i = new Intent(getActivity(), Academic_Qualification.class);
                        i.putExtra("ICNo", currentUser.getICNo());
                        i.putExtra("user", currentUser);
                        startActivity(i);
                        break;

                    case ("Tertiary Info"):
                        i = new Intent(getActivity(), Tertiary_Info.class);
                        i.putExtra("user", currentUser);
                        startActivity(i);
                        break;

                    case ("Attendance Check In"):
                        i = new Intent(getActivity(), Check_in_scan.class);
                        i.putExtra("user", currentUser);
                        startActivity(i);
                        break;
                    case ("Exam Timetable"):
                        i = new Intent(getActivity(), ExamTimeTable.class);
                        i.putExtra("user", currentUser);
                        startActivity(i);
                        break;

                }

            }
        });

        return v;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}